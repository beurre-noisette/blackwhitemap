import { ChefCluster } from "@/types/map.ts";

/**
 * Mock 데이터: 시/도별 셰프 클러스터 정보
 * - 초기 지도 화면(줌 아웃)에 표시될 클러스터 마커 데이터
 * - 각 지역의 중심 좌표는 시청/도청 기준으로 설정
 */
export const mockChefClusters: ChefCluster[] = [
  {
    region: "서울특별시",
    blackCount: 25,
    whiteCount: 15,
    latitude: 37.5665,
    longitude: 126.978,
  },
  {
    region: "부산광역시",
    blackCount: 1,
    whiteCount: 1,
    latitude: 35.1796,
    longitude: 129.0756,
  },
  {
    region: "경기도",
    blackCount: 8,
    whiteCount: 12,
    latitude: 37.4138,
    longitude: 127.5183,
  },
  {
    region: "인천광역시",
    blackCount: 3,
    whiteCount: 2,
    latitude: 37.4563,
    longitude: 126.7052,
  },
  {
    region: "제주특별자치도",
    blackCount: 1,
    whiteCount: 1,
    latitude: 33.4996,
    longitude: 126.5312,
  },
];

/**
 * 지도 초기 설정값
 * - 남한 전체가 보이도록 설정
 */
export const INITIAL_MAP_CENTER = {
  latitude: 36.5,
  longitude: 127.5,
  level: 13,
};
