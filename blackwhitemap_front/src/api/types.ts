/**
 * 백엔드 API 응답 타입 정의
 */

/**
 * ApiResponse<T> - 백엔드 공통 응답 구조
 */
export interface ApiResponse<T> {
  data: T;
  // 필요시 추가 필드
  // success?: boolean;
  // message?: string;
}

/**
 * 백엔드 PerformerResponse.ChefInfo
 */
export interface ChefInfoResponse {
  id: number;
  name: string;
  nickname: string;
  type: string;
  restaurantName: string;
  address: string;
  latitude: number;
  longitude: number;
  closedDays: string; // "일,월" 형태
  category: string;
  naverReservationUrl: string | null;
  catchTableUrl: string | null;
  instagramUrl: string | null;
  imageUrls: string[];
  viewCount: number;
}

/**
 * 백엔드 PerformerResponse.ChefClusterInfo
 */
export interface ChefClusterInfoResponse {
  region: string;
  blackCount: number;
  whiteCount: number;
  latitude: number;
  longitude: number;
}

/**
 * 백엔드 RankingResponse.WeeklyBestChef
 */
export interface WeeklyBestChefResponse {
  id: number;
  name: string;
  nickname: string;
  type: string;
  restaurantName: string;
  address: string;
  closedDays: string; // "일,월" 형태
  category: string;
  naverReservationUrl: string | null;
  catchTableUrl: string | null;
  imageUrls: string[];
  viewCount: number;
  rank: number;
  score: number;
}
