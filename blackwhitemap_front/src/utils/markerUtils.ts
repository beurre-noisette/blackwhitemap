import { ChefDetail } from "@/types/chef";
import { ChefGroup, DisplayLevel } from "@/types/map";

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
 * 셰프 목록을 주소+타입 기준으로 그룹화 (Level 3~10용)
 * - 같은 주소 + 같은 타입의 셰프들을 그룹화
 * - key: "address|type" 형태
 * @returns Map<string, ChefDetail[]>
 */
export const groupChefsByAddressAndType = (
  chefs: ChefDetail[],
): Map<string, ChefDetail[]> => {
  const grouped = new Map<string, ChefDetail[]>();

  chefs.forEach((chef) => {
    const key = `${chef.address}|${chef.type}`;
    const existing = grouped.get(key) || [];
    grouped.set(key, [...existing, chef]);
  });

  return grouped;
};

/**
 * 셰프 목록을 주소 기준으로 그룹화 (Level 2 이하용)
 * - 같은 주소의 셰프들을 타입 무관하게 그룹화
 * @returns ChefGroup[]
 */
export const groupChefsByAddress = (chefs: ChefDetail[]): ChefGroup[] => {
  const grouped = new Map<string, ChefDetail[]>();

  chefs.forEach((chef) => {
    const existing = grouped.get(chef.address) || [];
    grouped.set(chef.address, [...existing, chef]);
  });

  return Array.from(grouped.entries())
    .filter(([, groupChefs]) => groupChefs.length > 0)
    .map(([address, groupChefs]) => ({
      address,
      chefs: groupChefs,
      latitude: groupChefs[0]!.latitude,
      longitude: groupChefs[0]!.longitude,
    }));
};

/**
 * 셰프의 표시 이름을 반환
 * - BLACK 타입: nickname 사용
 * - WHITE 타입: name 사용
 */
export const getChefDisplayName = (chef: ChefDetail): string => {
  return chef.type === "BLACK" ? chef.nickname : chef.name;
};
