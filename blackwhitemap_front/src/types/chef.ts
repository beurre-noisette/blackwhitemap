export type ChefType = "BLACK" | "WHITE";

/**
 * 기본 셰프 정보 (공통 필드)
 */
export interface ChefBase {
  id: number;
  name: string;
  nickname: string;
  type: ChefType;
  restaurantName: string;
  address: string;
  smallAddress: string;
  category: string;
  imageUrls: string[];
  finalPlacement: string | null; // 최종성적 (예: "우승", "준우승", "8강")
  viewCount: number;
}

/**
 * 셰프 정보 (GET /performer/chefs 응답)
 * - 지도에 표시할 전체 셰프 목록
 */
export interface ChefInfo extends ChefBase {
  latitude: number; // 지도 마커용
  longitude: number; // 지도 마커용
  naverReservationUrl: string | null;
  catchTableUrl: string | null;
  instagramUrl: string | null;
  region: string;
}

/**
 * 셰프 상세 정보
 * - ChefDetailContent에서 사용
 */
export interface ChefDetail extends ChefInfo {
  closedDays: string[]; // ["일", "월"]
}

/**
 * 이번주 Best Chef (GET /ranking/weekly-best 응답)
 * - BestChefContent에서만 사용
 * - latitude, longitude, instagramUrl 없음
 */
export interface BestChef extends ChefBase {
  naverReservationUrl: string | null;
  catchTableUrl: string | null;
  closedDays: string[];
  rank: number;
}

/**
 * 일일 인기 셰프 Top5 (GET /ranking/daily-best 응답)
 * - Top5Content에서 사용
 */
export interface DailyBestChef {
  id: number;
  name: string;
  nickname: string;
  type: ChefType;
  restaurantName: string;
  smallAddress: string;
  category: string;
  naverReservationUrl: string | null;
  catchTableUrl: string | null;
  finalPlacement: string | null; // 최종성적 (예: "우승", "준우승", "8강")
  rank: number;
}
