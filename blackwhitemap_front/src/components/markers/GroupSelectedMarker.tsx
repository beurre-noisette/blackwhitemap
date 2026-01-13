import { ChefDetail } from "@/types/chef";
import { cn } from "@/utils/cn";
import { Icon } from "@/components/Icon";
import { getChefDisplayName } from "@/utils/markerUtils";

interface GroupSelectedMarkerProps {
  chefs: ChefDetail[];
  onChefClick: (chef: ChefDetail) => void;
}

/**
 * 그룹 선택된 마커 컴포넌트 (여러 가게)
 * - 카드 형태로 여러 가게 나열
 * - 가게 간 구분선
 */
export const GroupSelectedMarker = ({
  chefs,
  onChefClick,
}: GroupSelectedMarkerProps) => {
  return (
    <div
      className={cn(
        "flex flex-col",
        "w-[185px]",
        "bg-white border border-black rounded-md",
        "p-3",
      )}
      style={{
        filter: "drop-shadow(0px 0px 24px rgba(0, 0, 0, 0.2))",
      }}
    >
      {chefs.map((chef, index) => (
        <div key={chef.id}>
          <ChefItem chef={chef} onClick={() => onChefClick(chef)} />
          {index < chefs.length - 1 && (
            <div className="border-t border-gray-200 my-2" />
          )}
        </div>
      ))}
    </div>
  );
};

interface ChefItemProps {
  chef: ChefDetail;
  onClick: () => void;
}

/**
 * 그룹 내 개별 셰프 항목
 */
const ChefItem = ({ chef, onClick }: ChefItemProps) => {
  const iconName = chef.type === "BLACK" ? "chef-black-seg" : "chef-white-seg";
  const displayName = getChefDisplayName(chef);

  return (
    <div
      onClick={onClick}
      className="flex items-center gap-2 cursor-pointer hover:bg-gray-50 rounded p-1 -m-1"
      role="button"
      aria-label={`${chef.restaurantName} 선택`}
    >
      {/* 아이콘 */}
      <Icon name={iconName} size="large" />

      {/* 텍스트 영역 */}
      <div className="flex flex-col min-w-0">
        <span className="text-sm font-semibold text-black truncate leading-[14px] tracking-[-0.02em]">
          {chef.restaurantName}
        </span>
        <span className="text-xs text-gray-500 truncate leading-3 tracking-[-0.02em]">
          {displayName}
        </span>
      </div>
    </div>
  );
};
