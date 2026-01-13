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
        "flex items-center gap-2",
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
            "text-sm font-semibold truncate leading-[14px] tracking-[-0.02em]",
            "text-black",
          )}
        >
          {chef.restaurantName}
        </span>
        <span
          className={cn(
            "text-xs truncate leading-3 tracking-[-0.02em]",
            "text-gray-500",
          )}
        >
          {displayName}
        </span>
      </div>
    </div>
  );
};
