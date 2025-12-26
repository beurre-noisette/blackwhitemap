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
  category: string;
  imageUrls: string[];
  viewCount: number;
}

/**
 * 셰프 정보 (GET /performer/chefs 응답)
 * - 지도에 표시할 전체 셰프 목록
 */
export interface ChefInfo extends ChefBase {
  latitude: number;           // 지도 마커용
  longitude: number;          // 지도 마커용
  naverReservationUrl: string | null;
  catchTableUrl: string | null;
  instagramUrl: string | null;
}

/**
 * 셰프 상세 정보
 * - ChefDetailContent에서 사용
 */
export interface ChefDetail extends ChefInfo {
  closedDays: string[];      // ["일", "월"]
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