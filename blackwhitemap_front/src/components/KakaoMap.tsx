import { useState } from "react";
import { CustomOverlayMap, Map, MapMarker } from "react-kakao-maps-sdk";
import { ChefCluster, ChefMarker } from "@/types/map";
import { ChefDetail } from "@/types/chef";
import { ICON_MAP } from "@/components/Icon.tsx";
import { ClusterMarker } from "@/components/ClusterMarker.tsx";

/**
 * KakaoMap 컴포넌트의 Props
 *
 * @param clusters - 시/도별 클러스터 데이터 (줌 아웃 상태에서 표시)
 * @param chefs - 전체 셰프 데이터 (줌 인 상태에서 개별 마커 표시)
 * @param onChefClick - 개별 셰프 마커 클릭 시 콜백
 */
interface KakaoMapProps {
  clusters: ChefCluster[];
  chefs: ChefDetail[];
  onChefClick: (chef: ChefDetail) => void;
}

/**
 * 카카오맵 컴포넌트
 *
 * 두 가지 뷰 상태를 관리합니다:
 * 1. cluster 모드: 줌 아웃 상태, 시/도별 클러스터 마커 표시
 * 2. individual 모드: 줌 인 상태, 개별 셰프 마커 표시
 *
 * 클러스터 마커 클릭 시 해당 지역으로 줌인하여 개별 마커를 표시합니다.
 */
export const KakaoMap = ({ clusters, chefs, onChefClick }: KakaoMapProps) => {
  // 지도 중심 좌표 및 줌 레벨
  // 36.5 127.5
  const [center, setCenter] = useState({ lat: 36.5, lng: 127.5 });
  const [level, setLevel] = useState(13); // 13: 남한 전체 보임

  // 뷰 상태: cluster(줌 아웃) vs individual(줌 인)
  const [viewState, setViewState] = useState<"cluster" | "individual">(
    "cluster",
  );

  /**
   * 클러스터 마커 클릭 핸들러
   * - 해당 지역으로 줌인 (level 8 정도)
   * - 뷰 상태를 individual로 변경
   */
  const handleClusterClick = (cluster: ChefCluster) => {
    setCenter({ lat: cluster.latitude, lng: cluster.longitude });
    setLevel(8); // 줌인 레벨 (추후 조정 가능)
    setViewState("individual");
  };

  /**
   * 개별 마커를 ChefInfo에서 ChefMarker 형태로 변환
   */
  const markers: ChefMarker[] = chefs.map((chef) => ({
    id: chef.id,
    latitude: chef.latitude,
    longitude: chef.longitude,
    type: chef.type,
  }));

  return (
    <Map
      center={{ lat: center.lat, lng: center.lng }}
      level={level}
      style={{ width: "100%", height: "100%" }}
      onZoomChanged={(map) => {
        const newLevel = map.getLevel();
        // 줌 레벨에 따라 뷰 상태 자동 전환
        // 레벨 11 이상: 클러스터 모드, 미만: 개별 마커 모드
        setViewState(newLevel >= 11 ? "cluster" : "individual");
      }}
    >
      {/* 클러스터 모드: 시/도별 클러스터 마커 표시 */}
      {viewState === "cluster" &&
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

      {/* 개별 모드: 셰프별 마커 표시 */}
      {viewState === "individual" &&
        markers.map((marker) => (
          <MapMarker
            key={marker.id}
            position={{ lat: marker.latitude, lng: marker.longitude }}
            image={{
              src: ICON_MAP[
                marker.type === "BLACK" ? "chef-black" : "chef-white"
              ],
              size: { width: 24, height: 24 },
            }}
            onClick={() => {
              const chef = chefs.find((c) => c.id === marker.id);
              if (chef) onChefClick(chef);
            }}
          />
        ))}
    </Map>
  );
};
