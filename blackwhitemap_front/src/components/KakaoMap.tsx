import { useState, useMemo, useEffect, useRef } from "react";
import { CustomOverlayMap, Map, MapMarker } from "react-kakao-maps-sdk";
import { ChefCluster, DisplayLevel } from "@/types/map";
import { ChefDetail } from "@/types/chef";
import { ClusterMarker } from "@/components/ClusterMarker.tsx";
import { PillMarker } from "@/components/markers/PillMarker";
import { SelectedMarker } from "@/components/markers/SelectedMarker";
import { ICON_MAP } from "@/components/Icon.tsx";
import { getDisplayLevel } from "@/utils/markerUtils";

/**
 * KakaoMap 컴포넌트의 Props
 *
 * @param clusters - 시/도별 클러스터 데이터 (줌 아웃 상태에서 표시)
 * @param chefs - 전체 셰프 데이터 (줌 인 상태에서 개별 마커 표시)
 * @param onChefClick - 개별 셰프 마커 클릭 시 콜백
 * @param selectedChef - 외부에서 관리하는 선택된 셰프 (optional)
 */
interface KakaoMapProps {
  clusters: ChefCluster[];
  chefs: ChefDetail[];
  onChefClick: (chef: ChefDetail) => void;
  selectedChef?: ChefDetail | null;
  onMapInteract?: () => void;
}

/**
 * 선택 상태 타입
 * - none: 선택 없음
 * - single: 단일 셰프 선택
 */
type MarkerSelection = { type: "none" } | { type: "single"; chef: ChefDetail };

/**
 * 카카오맵 컴포넌트
 *
 * 세 가지 뷰 상태를 관리합니다:
 * 1. cluster 모드 (level 11+): 시/도별 클러스터 마커 표시
 * 2. level5to10 모드 (level 5~10): 개별 아이콘 마커
 * 3. level4below 모드 (level 4 이하): 알약 마커
 */
export const KakaoMap = ({
  clusters,
  chefs,
  onChefClick,
  selectedChef,
  onMapInteract,
}: KakaoMapProps) => {
  const [center, setCenter] = useState({ lat: 34.7, lng: 127.75 });
  const [level, setLevel] = useState(13); // 13: 남한 전체 보임

  // Display Level 상태
  const [displayLevel, setDisplayLevel] = useState<DisplayLevel>("cluster");

  // 내부 선택 상태 (외부 selectedChef와 연동)
  const [selection, setSelection] = useState<MarkerSelection>({ type: "none" });

  const skipNextZoom = useRef(false);
  const skipSelectionReset = useRef(false);

  // 외부 selectedChef 변경 시 내부 상태 동기화
  useEffect(() => {
    if (selectedChef) {
      setSelection({ type: "single", chef: selectedChef });
    } else {
      setSelection({ type: "none" });
    }
  }, [selectedChef]);

  // displayLevel 변경 시 선택 상태 초기화
  // (단, 마커 클릭으로 인한 변경은 제외)
  useEffect(() => {
    if (skipSelectionReset.current) {
      skipSelectionReset.current = false;
      return;
    }
    setSelection({ type: "none" });
  }, [displayLevel]);

  const mappableChefs = useMemo(
    () =>
      chefs.filter(
        (chef) =>
          Number.isFinite(chef.latitude) && Number.isFinite(chef.longitude),
      ),
    [chefs],
  );

  /**
   * 클러스터 마커 클릭 핸들러
   * - 해당 지역으로 줌인 (level 8 정도)
   */
  const handleClusterClick = (cluster: ChefCluster) => {
    setCenter({ lat: cluster.latitude, lng: cluster.longitude });
    setLevel(8);
    setDisplayLevel("level5to10");
  };

  /**
   * 개별 마커 클릭 핸들러
   */
  const handleMarkerClick = (chef: ChefDetail) => {
    if (selection.type === "single" && selection.chef.id === chef.id) {
      return;
    }
    if (displayLevel !== "level4below") {
      skipNextZoom.current = true;
      skipSelectionReset.current = true;
      setCenter({ lat: chef.latitude, lng: chef.longitude });
      setLevel(2);
      setDisplayLevel(getDisplayLevel(2));
    }
    setSelection({ type: "single", chef });
    onChefClick(chef);
  };

  /**
   * 선택된 마커인지 확인
   */
  const isSelected = (chef: ChefDetail) => {
    return selection.type === "single" && selection.chef.id === chef.id;
  };

  return (
    <Map
      center={{ lat: center.lat, lng: center.lng }}
      level={level}
      style={{ width: "100%", height: "100%" }}
      onZoomChanged={(map) => {
        const newLevel = map.getLevel();
        setLevel(newLevel);
        setDisplayLevel(getDisplayLevel(newLevel));

        if (skipNextZoom.current) {
          skipNextZoom.current = false;
          return;
        }
        onMapInteract?.();
      }}
      onDragStart={() => {
        onMapInteract?.();
      }}
      onClick={() => {
        setSelection({ type: "none" });
        onMapInteract?.();
      }}
    >
      {/* 클러스터 모드: 시/도별 클러스터 마커 표시 */}
      {displayLevel === "cluster" &&
        clusters.map((cluster) => (
          <CustomOverlayMap
            key={cluster.region}
            position={{ lat: cluster.latitude, lng: cluster.longitude }}
            clickable={true}
          >
            <ClusterMarker
              cluster={cluster}
              onClick={() => handleClusterClick(cluster)}
            />
          </CustomOverlayMap>
        ))}

      {/* Level 5~10 모드: 개별 아이콘 마커 */}
      {displayLevel === "level5to10" &&
        mappableChefs.map((chef) => {
          const selected = isSelected(chef);

          if (selected) {
            return (
              <CustomOverlayMap
                key={chef.id}
                position={{ lat: chef.latitude, lng: chef.longitude }}
                clickable={true}
                zIndex={10}
              >
                <SelectedMarker
                  chef={chef}
                  onClick={() => handleMarkerClick(chef)}
                />
              </CustomOverlayMap>
            );
          }

          return (
            <MapMarker
              key={chef.id}
              position={{ lat: chef.latitude, lng: chef.longitude }}
              image={{
                src: ICON_MAP[
                  chef.type === "BLACK" ? "chef-black-seg" : "chef-white-seg"
                ],
                size: { width: 24, height: 24 },
              }}
              onClick={() => handleMarkerClick(chef)}
            />
          );
        })}

      {/* Level 4 이하 모드: 알약 마커 */}
      {displayLevel === "level4below" &&
        mappableChefs.map((chef) => {
          const selected = isSelected(chef);

          return (
            <CustomOverlayMap
              key={chef.id}
              position={{ lat: chef.latitude, lng: chef.longitude }}
              clickable={true}
              zIndex={selected ? 10 : 1}
            >
              {selected ? (
                <SelectedMarker
                  chef={chef}
                  onClick={() => handleMarkerClick(chef)}
                />
              ) : (
                <PillMarker
                  chef={chef}
                  onClick={() => handleMarkerClick(chef)}
                />
              )}
            </CustomOverlayMap>
          );
        })}
    </Map>
  );
};
