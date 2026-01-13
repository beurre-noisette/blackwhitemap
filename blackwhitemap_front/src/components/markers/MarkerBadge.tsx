import { ChefType } from "@/types/chef";
import { cn } from "@/utils/cn";

interface MarkerBadgeProps {
  count: number;
  type: ChefType;
}

/**
 * 마커 배지 컴포넌트
 * - 같은 주소에 추가 가게가 있을 때 표시
 * - 마커의 1시 방향에 위치
 * - WHITE: 흰색 배경 + 검정 테두리
 * - BLACK: 검정 배경 + 흰색 테두리
 */
export const MarkerBadge = ({ count, type }: MarkerBadgeProps) => {
  if (count <= 0) return null;

  return (
    <div
      className={cn(
        "absolute -top-1 -right-2",
        "flex items-center justify-center",
        "w-5 h-[14px] rounded-[26px]",
        "text-[10px] font-medium",
        type === "WHITE"
          ? "bg-white border border-black text-black"
          : "bg-black border border-white text-white",
      )}
    >
      +{count}
    </div>
  );
};
