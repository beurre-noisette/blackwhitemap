import { ReactNode } from "react";
import { Icon, IconName } from "./Icon";
import { cn } from "@/utils/cn.ts";

export interface ChefInfoRowProps {
  iconName: IconName;
  label: string;
  /**
   * 표시할 값 (문자열 또는 커스텀 ReactNode)
   * - 단순 텍스트: string;
   * - 복잡한 UI: ReactNode;
   */
  children: ReactNode;
  className?: string; // 추가 CSS 클래스
}

export const ChefInfoRow = ({
  iconName,
  label,
  children,
  className,
}: ChefInfoRowProps) => {
  return (
    <div className={cn("flex items-center gap-1 h-3", className)}>
      {/* 아이콘 */}
      <Icon
        name={iconName}
        size="extraSmall"
        className="text-gray-500 flex-shrink-0"
      />

      {/* 라벨 */}
      <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
        {label}
      </span>

      {/* 구분자 */}
      <span className="text-xs font-normal leading-none tracking-tight text-gray-400">
        |
      </span>

      {/* 값 */}
      {typeof children === "string" ? (
        <span className="text-xs font-normal leading-none tracking-tight text-gray-500 truncate">
          {children}
        </span>
      ) : (
        children
      )}
    </div>
  );
};

ChefInfoRow.displayName = "ChefInfoRow";
