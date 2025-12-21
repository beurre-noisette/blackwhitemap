import { ChefDetail } from "@/types/chef";
import { cn } from "@/utils/cn";
import { ChefInfoRow } from "./ChefInfoRow";
import { ChefActionButtons } from "./ChefActionButtons";

export interface ChefCardProps {
  chef: ChefDetail;
  variant?: "bestChef" | "chefDetail";
  onReservationClick?: () => void;
  className?: string;
}

/**
 * ChefCard 컴포넌트
 *
 * variant:
 * - bestChef: 인스타그램 제거, 공유 버튼 제거
 * - chefDetail: 모든 정보 표시, 공유 버튼 표시
 */
export const ChefCard = ({
  chef,
  variant = "bestChef",
  onReservationClick,
  className,
}: ChefCardProps) => {
  return (
    <div
      className={cn(
        "w-[335px]",
        "rounded-[24px]",
        "border border-gray-200",
        "p-4",
        "flex flex-col gap-4",
        "bg-white",
        className,
      )}
    >
      {/* 사진 + 가게 정보 */}
      <div className="flex gap-4">
        {/* 사진 */}
        <img
          src={chef.imageUrls[0]}
          alt={chef.name}
          className="w-[100px] h-[120px] rounded-[16px] object-cover bg-black flex-shrink-0"
        />

        {/* 가게 정보 */}
        <div className="flex flex-col gap-3 flex-1 min-w-0">
          {/* 가게명 + 이름 */}
          <div className="flex flex-col gap-2">
            <h3 className="text-xl font-bold leading-none tracking-tight text-black truncate">
              {chef.restaurantName}
            </h3>
            <p className="text-sm font-semibold leading-none tracking-tight text-black truncate">
              {chef.nickname ?? chef.name}
            </p>
          </div>

          {/* 카테고리 */}
          <ChefInfoRow iconName="category" label="카테고리">
            {chef.category}
          </ChefInfoRow>

          {/* 위치 */}
          <ChefInfoRow iconName="location" label="위치">
            {chef.address.split(" ")[2] || chef.address.split(" ")[1]}
          </ChefInfoRow>

          {/* 휴무일 */}
          <ChefInfoRow iconName="watch" label="휴무일">
            {chef.closedDays.map((day, index) => (
              <div key={day} className="flex items-center gap-1">
                {index > 0 && (
                  <div className="w-1 h-1 rounded-full bg-gray-200" />
                )}
                <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
                  {day}
                </span>
              </div>
            ))}
          </ChefInfoRow>

          {/* 인스타그램 (chefDetail에서만 표시) */}
          {variant === "chefDetail" && chef.instagramUrl && (
            <ChefInfoRow iconName="usdCircle" label="공유하기">
              <a
                href={chef.instagramUrl}
                target="_blank"
                rel="noopener noreferrer"
                className="text-xs font-normal leading-none tracking-tight text-blue-600 underline
  truncate"
              >
                인스타그램
              </a>
            </ChefInfoRow>
          )}
        </div>
      </div>

      {/* 예약하기 버튼 (bestChef는 버튼만, chefDetail은 공유+버튼) */}
      <ChefActionButtons
        chef={chef}
        onReservationClick={onReservationClick}
        showShareButton={variant === "chefDetail"}
      />
    </div>
  );
};

ChefCard.displayName = "ChefCard";
