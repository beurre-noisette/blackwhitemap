import { ButtonHTMLAttributes } from "react";
import { cn } from "@/utils/cn";
import { Icon } from "@/components/Icon";

/**
 * RankingButton 컴포넌트의 Props
 */
export interface RankingButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  isActive?: boolean;
}

/**
 * 랭킹 버튼 컴포넌트
 *
 * @example
 * <RankingButton onClick={() => setSheetState("bestChef-default")} />
 */
export const RankingButton = ({
  isActive = false,
  className,
  ...props
}: RankingButtonProps) => {
  return (
    <button
      type="button"
      className={cn(
        "flex items-center justify-center",
        "w-12 h-12 min-w-12 min-h-12 max-w-12 max-h-12",
        "bg-white",
        isActive && "border border-black",
        "shadow-[0px_4px_20px_rgba(0,0,0,0.2)]",
        "rounded-[24px]",
        "cursor-pointer",
        "transition-all duration-200",
        "hover:shadow-[0px_4px_24px_rgba(0,0,0,0.25)]",
        "active:scale-95",
        className,
      )}
      {...props}
    >
      <Icon name="ranking" size="large" alt="랭킹 보기" />
    </button>
  );
};

RankingButton.displayName = "RankingButton";
