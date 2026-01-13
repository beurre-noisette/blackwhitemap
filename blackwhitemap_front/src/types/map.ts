import { ChefDetail, ChefType } from "@/types/chef.ts";

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
 * 주소 기반 셰프 그룹 (Level 2 이하에서 사용)
 * - 같은 주소에 있는 셰프들을 하나의 그룹으로 묶음
 */
export interface ChefGroup {
  address: string;
  chefs: ChefDetail[];
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

/**
 * 지도 뷰 상태
 * - cluster: 줌 아웃 상태 (클러스터 마크 표시)
 * - individual: 줌 인 상태 (개별 셰프 마커 표시)
 */
export type MapViewState = "cluster" | "individual";
