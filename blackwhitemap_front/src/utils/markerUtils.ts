import { ChefDetail } from "@/types/chef";
import { DisplayLevel } from "@/types/map";

/**
 * 줌 레벨을 DisplayLevel로 변환
 * - 11 이상: cluster (시/도별 클러스터)
 * - 5~10: level5to10 (개별 아이콘 마커)
 * - 4 이하: level4below (알약 형태 마커)
 */
export const getDisplayLevel = (zoomLevel: number): DisplayLevel => {
  if (zoomLevel >= 11) return "cluster";
  if (zoomLevel >= 5) return "level5to10";
  return "level4below";
};

/**
 * 셰프의 표시 이름을 반환
 * - BLACK 타입: nickname 사용
 * - WHITE 타입: name 사용
 */
export const getChefDisplayName = (chef: ChefDetail): string => {
  return chef.type === "BLACK" ? chef.nickname : chef.name;
};
