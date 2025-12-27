import { ChefDetail } from "@/types/chef";
import { ChefCard } from "./ChefCard";
import { ChefActionButtons } from "./ChefActionButtons";

export interface ChefDetailContentProps {
  chef: ChefDetail;
  variant: "minimized" | "default";
  onReservationClick?: () => void;
}

/**
 * ChefDetailContent 컴포넌트
 *
 * 셰프 상세 정보를 표시
 * - minimized: 가게명 + 닉네임 + 버튼만 표시
 * - default: ChefCard variant="chefDetail" 재사용
 */
export const ChefDetailContent = ({
  chef,
  variant,
  onReservationClick,
}: ChefDetailContentProps) => {
  if (variant === "minimized") {
    return (
      <div className="flex flex-col pt-[13px]">
        {/* 가게명 + 이름(혹은 닉네임) */}
        <div className="flex flex-col gap-2 mb-6">
          <h2 className="text-xl font-bold leading-none tracking-tight text-black">
            {chef.restaurantName}
          </h2>
          <p className="text-sm font-semibold leading-none tracking-tight text-black">
            {chef.nickname ?? chef.name}
          </p>
        </div>

        {/* 공유하기 + 예약하기 버튼 */}
        <ChefActionButtons
          chef={chef}
          onReservationClick={onReservationClick}
          showShareButton={true}
        />
      </div>
    );
  }

  // default 상태: ChefCard 컴포넌트 재사용
  return (
    <div className="pt-[13px]">
      {/* ChefCard와 버튼 사이 24px 간격 */}
      <div className="mb-6">
        <ChefCard
          chef={chef}
          variant="chefDetail"
          onReservationClick={onReservationClick}
        />
      </div>
    </div>
  );
};

ChefDetailContent.displayName = "ChefDetailContent";
