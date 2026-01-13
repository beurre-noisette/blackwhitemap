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
        "py-[7px] px-4",
        "rounded-[47px]",
        "cursor-pointer",
        chef.type === "WHITE"
          ? "bg-white border border-gray-300"
          : "bg-black border border-gray-700",
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
            chef.type === "WHITE" ? "text-black" : "text-white",
          )}
        >
          {chef.restaurantName}
        </span>
        <span
          className={cn(
            "text-xs truncate leading-3 tracking-[-0.02em]",
            chef.type === "WHITE" ? "text-gray-500" : "text-gray-400",
          )}
        >
          {displayName}
        </span>
      </div>
    </div>
  );
};
