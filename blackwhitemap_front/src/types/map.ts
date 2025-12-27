import { ChefType } from "@/types/chef.ts";

/**
 * 지도에 표시할 클러스터 정보
 * - region: Region ENUM name (e.g. "SEOUL", "BUSAN", "GYEONGGI")
 * - blackCount: 해당 지역의 흑요리사 수
 * - whiteCount: 해당 지역의 백요리사 수
 * - latitude: 지역 중심 위도 (클러스터 마커 표시 위치)
 * - longitude: 지역 중심 경도 (클러스터 마커 표시 위치)
 */
export interface ChefCluster {
  region: string;
  blackCount: number;
  whiteCount: number;
  latitude: number;
  longitude: number;
}

/**
 * 개별 셰프 마커 정보
 * - 지도에 표시할 정보
 */
export interface ChefMarker {
  id: number;
  latitude: number;
  longitude: number;
  type: ChefType;
}

/**
 * 지도 뷰 상태
 * - cluster: 줌 아웃 상태 (클러스터 마크 표시)
 * - individual: 줌 인 상태 (개별 셰프 마커 표시)
 */
export type MapViewState = "cluster" | "individual";
