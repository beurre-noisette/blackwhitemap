import { ChefDetail } from "@/types/chef";
import { cn } from "@/utils/cn";
import { Icon } from "@/components/Icon";
import { getChefDisplayName } from "@/utils/markerUtils";

interface PillMarkerProps {
  chef: ChefDetail;
  onClick: () => void;
}

/**
 * 알약 형태 마커 컴포넌트 (Level 2 이하)
 * - 아이콘 + restaurantName + nickname/name
 * - 둥근 알약 형태 (border-radius: 47px)
 */
export const PillMarker = ({ chef, onClick }: PillMarkerProps) => {
  const iconName = chef.type === "BLACK" ? "chef-black-seg" : "chef-white-seg";
  const displayName = getChefDisplayName(chef);

  return (
    <div
      onClick={onClick}
      className="relative cursor-pointer"
      role="button"
      aria-label={`${chef.restaurantName} 마커`}
    >
      <div
        className={cn(
          "inline-flex items-center gap-2",
          "bg-white border border-gray-300 rounded-[47px]",
          "py-[6px] pl-3 pr-4",
          "max-w-[160px] w-max",
          "overflow-hidden",
        )}
      >
        {/* 아이콘 */}
        <Icon name={iconName} size="large" />

        {/* 텍스트 영역 */}
        <div className="flex flex-1 flex-col gap-1 min-w-0 max-w-[100px] overflow-hidden">
          <span className="text-sm font-semibold text-black truncate leading-[14px] tracking-[-0.02em]">
            {chef.restaurantName}
          </span>
          <span className="text-xs text-gray-500 truncate leading-3 tracking-[-0.02em]">
            {displayName}
          </span>
        </div>
      </div>
    </div>
  );
};
