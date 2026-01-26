import { get } from "./client";
import type {
  ApiResponse,
  ChefClusterInfoResponse,
  ChefInfoResponse,
} from "./types";
import type { ChefDetail, ChefType } from "@/types/chef";
import type { ChefCluster } from "@/types/map";

/**
 * closedDays 변환 헬퍼
 * "일,월" → ["일", "월"]
 * null/undefined/empty → []
 */
function parseClosedDays(closedDays: string | null | undefined): string[] {
  if (!closedDays || closedDays.trim() === "") {
    return [];
  }
  return closedDays.split(",").map((day) => day.trim());
}

/**
 * ChefInfoResponse를 ChefDetail로 변환
 */
function mapToChefDetail(response: ChefInfoResponse): ChefDetail {
  return {
    id: response.id,
    name: response.name,
    nickname: response.nickname,
    type: response.type as ChefType,
    restaurantName: response.restaurantName,
    address: response.address,
    smallAddress: response.smallAddress,
    latitude: response.latitude,
    longitude: response.longitude,
    category: response.category,
    imageUrls: response.imageUrls,
    finalPlacement: response.finalPlacement,
    viewCount: response.viewCount,
    naverReservationUrl: response.naverReservationUrl,
    catchTableUrl: response.catchTableUrl,
    instagramUrl: response.instagramUrl,
    closedDays: parseClosedDays(response.closedDays),
  };
}

/**
 * GET /performer/chefs
 * 전체 셰프 목록 조회 (필터링 가능)
 *
 * @param type - "ALL" | "BLACK" | "WHITE" (선택적)
 */
export async function fetchChefs(
  type?: ChefType | "ALL",
): Promise<ChefDetail[]> {
  const endpoint =
    type && type !== "ALL"
      ? `/performer/chefs?type=${type}`
      : "/performer/chefs";

  const response = await get<ApiResponse<ChefInfoResponse[]>>(endpoint);

  return response.data.map(mapToChefDetail);
}

/**
 * GET /performer/chefs/cluster
 * 시/도별 클러스터 데이터 조회
 */
export async function fetchChefClusters(): Promise<ChefCluster[]> {
  const response = await get<ApiResponse<ChefClusterInfoResponse[]>>(
    "/performer/chefs/cluster",
  );

  return response.data;
}
