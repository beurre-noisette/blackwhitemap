import { get } from "./client";
import type { ApiResponse, WeeklyBestChefResponse } from "./types";
import type { BestChef, ChefType } from "@/types/chef";

/**
 * closedDays 변환 헬퍼
 */
function parseClosedDays(closedDays: string | null | undefined): string[] {
  if (!closedDays || closedDays.trim() === "") {
    return [];
  }
  return closedDays.split(",").map((day) => day.trim());
}

/**
 * WeeklyBestChefResponse를 BestChef로 변환
 */
function mapToBestChef(response: WeeklyBestChefResponse): BestChef {
  return {
    id: response.id,
    name: response.name,
    nickname: response.nickname,
    type: response.type as ChefType,
    restaurantName: response.restaurantName,
    address: response.address,
    category: response.category,
    imageUrls: response.imageUrls,
    viewCount: response.viewCount,
    naverReservationUrl: response.naverReservationUrl,
    catchTableUrl: response.catchTableUrl,
    closedDays: parseClosedDays(response.closedDays),
    rank: response.rank,
    // score는 프론트엔드에서 사용하지 않으므로 제외
  };
}

/**
 * GET /ranking/weekly-best
 * 이번주 Best Chef 조회
 *
 * @param limit - 조회할 랭킹 개수 (기본값 5)
 */
export async function fetchWeeklyBestChefs(
  limit: number = 5,
): Promise<BestChef[]> {
  const response = await get<ApiResponse<WeeklyBestChefResponse[]>>(
    `/ranking/weekly-best?limit=${limit}`,
  );

  return response.data.map(mapToBestChef);
}
