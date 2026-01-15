import { ChefDetail } from "@/types/chef";
import { cn } from "@/utils/cn";
import { Icon } from "@/components/Icon";
import { getChefDisplayName } from "@/utils/markerUtils";

interface SelectedMarkerProps {
  chef: ChefDetail;
  onClick?: () => void;
}

/**
 * 선택된 마커 컴포넌트 (단일 가게)
 * - 말풍선 형태 + 드롭 섀도우
 * - 아이콘 + restaurantName + nickname/name
 */
export const SelectedMarker = ({ chef, onClick }: SelectedMarkerProps) => {
  const iconName = chef.type === "BLACK" ? "chef-black-seg" : "chef-white-seg";
  const displayName = getChefDisplayName(chef);

  return (
    <div
      onClick={onClick}
      className={cn(
        "relative flex items-center gap-2",
        "py-[6px] px-3",
        "rounded-[47px]",
        "cursor-pointer",
        "bg-white border border-black",
      )}
      style={{
        filter: "drop-shadow(0px 0px 24px rgba(0, 0, 0, 0.2))",
      }}
      role="button"
      aria-label={`${chef.restaurantName} 선택됨`}
    >
      {/* 아이콘 */}
      <Icon name={iconName} size="large" />

      {/* 텍스트 영역 */}
      <div className="flex flex-col min-w-0">
        <span
          className={cn(
            "text-sm font-semibold leading-[14px] tracking-[-0.02em]",
            "text-black",
            "whitespace-nowrap",
          )}
        >
          {chef.restaurantName}
        </span>
        <span
          className={cn(
            "text-xs leading-3 tracking-[-0.02em]",
            "text-gray-500",
            "whitespace-nowrap",
          )}
        >
          {displayName}
        </span>
      </div>

      {/* 말풍선 꼬리 */}
      <div
        className={cn(
          "absolute left-1/2 -bottom-[6px] -translate-x-1/2",
          "w-0 h-0",
          "border-l-[6px] border-r-[6px] border-t-[6px]",
          "border-l-transparent border-r-transparent border-t-white",
        )}
      />
      <div
        className={cn(
          "absolute left-1/2 -bottom-[7px] -translate-x-1/2",
          "w-0 h-0",
          "border-l-[7px] border-r-[7px] border-t-[7px]",
          "border-l-transparent border-r-transparent border-t-black",
          "-z-10",
        )}
      />
    </div>
  );
};
