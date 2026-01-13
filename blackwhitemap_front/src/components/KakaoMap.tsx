import { useState, useMemo } from "react";
import { CustomOverlayMap, Map } from "react-kakao-maps-sdk";
import { ChefCluster, ChefGroup, DisplayLevel } from "@/types/map";
import { ChefDetail } from "@/types/chef";
import { ClusterMarker } from "@/components/ClusterMarker.tsx";
import { DefaultMarker } from "@/components/markers/DefaultMarker";
import { PillMarker } from "@/components/markers/PillMarker";
import { SelectedMarker } from "@/components/markers/SelectedMarker";
import { GroupSelectedMarker } from "@/components/markers/GroupSelectedMarker";
import {
  getDisplayLevel,
  groupChefsByAddressAndType,
  groupChefsByAddress,
} from "@/utils/markerUtils";

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
}

/**
 * 선택 상태 타입
 * - none: 선택 없음
 * - single: 단일 셰프 선택
 * - group: 그룹 선택 (Level 2 이하에서 같은 주소의 여러 가게)
 */
type MarkerSelection =
  | { type: "none" }
  | { type: "single"; chef: ChefDetail; groupKey?: string }
  | { type: "group"; group: ChefGroup };

/**
 * 카카오맵 컴포넌트
 *
 * 세 가지 뷰 상태를 관리합니다:
 * 1. cluster 모드 (level 11+): 시/도별 클러스터 마커 표시
 * 2. level3to10 모드 (level 3~10): 개별 아이콘 마커, 같은 주소+타입만 배지 표시
 * 3. level2below 모드 (level 2 이하): 알약 마커, 같은 주소 그룹화
 */
export const KakaoMap = ({
  clusters,
  chefs,
  onChefClick,
  selectedChef,
}: KakaoMapProps) => {
  const [center, setCenter] = useState({ lat: 34.7, lng: 127.75 });
  const [level, setLevel] = useState(13); // 13: 남한 전체 보임

  // Display Level 상태
  const [displayLevel, setDisplayLevel] = useState<DisplayLevel>("cluster");

  // 내부 선택 상태 (외부 selectedChef와 연동)
  const [selection, setSelection] = useState<MarkerSelection>({ type: "none" });

  // 외부 selectedChef 변경 시 내부 상태 동기화
  useMemo(() => {
    if (selectedChef) {
      setSelection({ type: "single", chef: selectedChef });
    }
  }, [selectedChef]);

  /**
   * Level 3~10용: 주소+타입 기준 그룹화
   * - key: "address|type"
   * - value: ChefDetail[]
   */
  const chefsByAddressAndType = useMemo(
    () => groupChefsByAddressAndType(chefs),
    [chefs],
  );

  /**
   * Level 2 이하용: 주소 기준 그룹화
   */
  const chefGroups = useMemo(() => groupChefsByAddress(chefs), [chefs]);

  /**
   * 클러스터 마커 클릭 핸들러
   * - 해당 지역으로 줌인 (level 8 정도)
   */
  const handleClusterClick = (cluster: ChefCluster) => {
    setCenter({ lat: cluster.latitude, lng: cluster.longitude });
    setLevel(8);
    setDisplayLevel("level3to10");
  };

  /**
   * Level 3~10에서 마커 클릭 핸들러
   */
  const handleLevel3to10MarkerClick = (chef: ChefDetail, groupKey: string) => {
    const group = chefsByAddressAndType.get(groupKey) || [];

    if (group.length === 1) {
      // 단일 가게: 선택 상태 변경 + 외부 콜백
      setSelection({ type: "single", chef, groupKey });
      onChefClick(chef);
    } else {
      // 여러 가게: 첫 번째 가게 선택 (추후 그룹 선택 UI로 확장 가능)
      setSelection({ type: "single", chef, groupKey });
      onChefClick(chef);
    }
  };

  /**
   * Level 2 이하에서 마커 클릭 핸들러
   */
  const handleLevel2BelowMarkerClick = (group: ChefGroup) => {
    const firstChef = group.chefs[0];
    if (!firstChef) return;

    if (group.chefs.length === 1) {
      // 단일 가게
      setSelection({ type: "single", chef: firstChef });
      onChefClick(firstChef);
    } else {
      // 여러 가게: 그룹 선택 상태로 변경
      setSelection({ type: "group", group });
    }
  };

  /**
   * 그룹 내 개별 셰프 클릭 핸들러
   */
  const handleGroupChefClick = (chef: ChefDetail) => {
    setSelection({ type: "single", chef });
    onChefClick(chef);
  };

  /**
   * 선택된 마커인지 확인 (Level 3~10)
   */
  const isSelectedInLevel3to10 = (chef: ChefDetail, groupKey: string) => {
    if (selection.type === "single") {
      return selection.groupKey === groupKey && selection.chef.id === chef.id;
    }
    return false;
  };

  /**
   * 선택된 그룹인지 확인 (Level 2 이하)
   */
  const isSelectedGroup = (group: ChefGroup) => {
    if (selection.type === "group") {
      return selection.group.address === group.address;
    }
    if (selection.type === "single") {
      return group.chefs.some((c) => c.id === selection.chef.id);
    }
    return false;
  };

  return (
    <Map
      center={{ lat: center.lat, lng: center.lng }}
      level={level}
      style={{ width: "100%", height: "100%" }}
      onZoomChanged={(map) => {
        const newLevel = map.getLevel();
        setDisplayLevel(getDisplayLevel(newLevel));
      }}
      onClick={() => {
        // 지도 클릭 시 선택 해제
        setSelection({ type: "none" });
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

      {/* Level 3~10 모드: 개별 아이콘 마커 */}
      {displayLevel === "level3to10" &&
        Array.from(chefsByAddressAndType.entries())
          .filter(([, groupChefs]) => groupChefs.length > 0)
          .map(([key, groupChefs]) => {
            const primaryChef = groupChefs[0]!;
            const extraCount = groupChefs.length - 1;
            const isSelected = isSelectedInLevel3to10(primaryChef, key);

            return (
              <CustomOverlayMap
                key={key}
                position={{
                  lat: primaryChef.latitude,
                  lng: primaryChef.longitude,
                }}
                clickable={true}
                zIndex={isSelected ? 10 : 1}
              >
                {isSelected ? (
                  <SelectedMarker
                    chef={primaryChef}
                    onClick={() => handleLevel3to10MarkerClick(primaryChef, key)}
                  />
                ) : (
                  <DefaultMarker
                    chef={primaryChef}
                    extraCount={extraCount}
                    onClick={() => handleLevel3to10MarkerClick(primaryChef, key)}
                  />
                )}
              </CustomOverlayMap>
            );
          })}

      {/* Level 2 이하 모드: 알약 마커 */}
      {displayLevel === "level2below" &&
        chefGroups
          .filter((group) => group.chefs.length > 0)
          .map((group) => {
            const primaryChef = group.chefs[0]!;
            const extraCount = group.chefs.length - 1;
            const isSelected = isSelectedGroup(group);

            return (
              <CustomOverlayMap
                key={group.address}
                position={{ lat: group.latitude, lng: group.longitude }}
                clickable={true}
                zIndex={isSelected ? 10 : 1}
              >
                {isSelected ? (
                  selection.type === "group" &&
                  selection.group.address === group.address ? (
                    <GroupSelectedMarker
                      chefs={group.chefs}
                      onChefClick={handleGroupChefClick}
                    />
                  ) : (
                    <SelectedMarker
                      chef={
                        selection.type === "single"
                          ? selection.chef
                          : primaryChef
                      }
                      onClick={() => handleLevel2BelowMarkerClick(group)}
                    />
                  )
                ) : (
                  <PillMarker
                    chef={primaryChef}
                    extraCount={extraCount}
                    onClick={() => handleLevel2BelowMarkerClick(group)}
                  />
                )}
              </CustomOverlayMap>
            );
          })}
    </Map>
  );
};
