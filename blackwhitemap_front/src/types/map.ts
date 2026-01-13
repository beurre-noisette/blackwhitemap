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
 * 지도 표시 레벨
 * - cluster: 줌 레벨 11 이상 (시/도별 클러스터)
 * - level3to10: 줌 레벨 3~10 (개별 아이콘 마커)
 * - level2below: 줌 레벨 2 이하 (알약 형태 마커)
 */
export type DisplayLevel = "cluster" | "level3to10" | "level2below";

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
