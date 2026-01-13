import { ChefDetail } from "@/types/chef";
import { Icon } from "@/components/Icon";

interface DefaultMarkerProps {
  chef: ChefDetail;
  onClick: () => void;
}

/**
 * 기본 마커 컴포넌트 (Level 3~10)
 * - 24x24 아이콘 마커
 * - 같은 주소+타입의 추가 가게가 있으면 배지 표시
 */
export const DefaultMarker = ({
  chef,
  onClick,
}: DefaultMarkerProps) => {
  const iconName = chef.type === "BLACK" ? "chef-black-seg" : "chef-white-seg";

  return (
    <div
      onClick={onClick}
      className="relative cursor-pointer"
      role="button"
      aria-label={`${chef.restaurantName} 마커`}
    >
      <Icon name={iconName} size="large" />
    </div>
  );
};
