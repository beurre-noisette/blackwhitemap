import { HTMLAttributes, ReactNode } from "react";
import { cn } from "@/utils/cn";


export interface MapControlBarProps extends HTMLAttributes<HTMLDivElement> {
  children: ReactNode;
}

/**
 * 지도 컨트롤 바 컴포넌트
 *
 * 지도 상단에 표시되는 컨트롤 요소들을 감싸는 레이아웃 컨테이너.
 * RankingButton(좌측)과 SegmentedControl(우측)을 포함.
 *
 * @example
 * <MapControlBar>
 *   <RankingButton onClick={handleRankingClick} />
 *   <SegmentedControl value={filter} onChange={setFilter} />
 * </MapControlBar>
 */
export const MapControlBar = ({
  children,
  className,
  ...props
}: MapControlBarProps) => {
  return (
    <div
      className={cn(
        "flex items-center",
        "w-full max-w-[375px]",
        "px-5 pt-[18px] pb-4",
        "gap-[9px]",
        className,
      )}
      {...props}
    >
      {children}
    </div>
  );
};

MapControlBar.displayName = "MapControlBar";