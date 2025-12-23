export type ChefType = "BLACK" | "WHITE";

/**
 * 셰프 정보 인터페이스
 * - PerformerResponse의 ChefInfo DTO와 동일한 구조
 */
export interface ChefInfo {
  id: number;
  name: string;
  nickname: string;
  type: ChefType;
  restaurantName: string;
  address: string;
  latitude: number;
  longitude: number;
  category: string;
  naverReservationUrl: string | null;
  catchTableUrl: string | null;
  instagramUrl: string | null;
  imageUrls: string[];
  viewCount: number;
}

/**
 * FIXME 도메인 변경 가능성 있음
 * 셰프 상세 정보
 * - closedDays: 휴무일 목록
 */
export interface ChefDetail extends ChefInfo {
  closedDays: string[]; // ["일", "월"]
}

/**
 * 이번주 Best Chef 정보
 * - ChefDetail의 모든 정보 포함
 * - rank: 주간 랭킹 순위 (1 ~ 5)
 */
export interface BestChef extends ChefDetail {
  rank: number;
}
