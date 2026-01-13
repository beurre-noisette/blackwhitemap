import { ChefDetail } from "@/types/chef";
import { DisplayLevel } from "@/types/map";

/**
 * 줌 레벨을 DisplayLevel로 변환
 * - 11 이상: cluster (시/도별 클러스터)
 * - 3~10: level3to10 (개별 아이콘 마커)
 * - 2 이하: level2below (알약 형태 마커)
 */
export const getDisplayLevel = (zoomLevel: number): DisplayLevel => {
  if (zoomLevel >= 11) return "cluster";
  if (zoomLevel >= 3) return "level3to10";
  return "level2below";
};

/**
 * 셰프의 표시 이름을 반환
 * - BLACK 타입: nickname 사용
 * - WHITE 타입: name 사용
 */
export const getChefDisplayName = (chef: ChefDetail): string => {
  return chef.type === "BLACK" ? chef.nickname : chef.name;
};
