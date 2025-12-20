import { ChefDetail } from "@/types/chef";
import { cn } from "@/utils/cn";
import { Icon } from "./Icon";
import { Button } from "./Button";
import { ShareButton } from "@/components/ShareButton.tsx";

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
 * - bestChef: 인스타그램, 공유 버튼 제거
 * - chefDetail: 모든 정보 표시
 */
export const ChefCard = ({
  chef,
  variant = "bestChef",
  onReservationClick,
  className,
}: ChefCardProps) => {
  const handleReservation = () => {
    if (onReservationClick) {
      onReservationClick();
    } else {
      const url = chef.catchTableUrl || chef.naverReservationUrl;
      if (url) {
        window.open(url, "_blank");
      }
    }
  };

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

          {/* 카테고리 | 바베큐 */}
          <div className="flex items-center gap-1 h-3">
            <Icon
              name="watch"
              size="extraSmall"
              className="text-gray-500 flex-shrink-0"
            />
            <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
              카테고리
            </span>
            <span className="text-xs font-normal leading-none tracking-tight text-gray-400">
              |
            </span>
            <span className="text-xs font-normal leading-none tracking-tight text-gray-500 truncate">
              {chef.category}
            </span>
          </div>

          {/* 위치 | x */}
          <div className="flex items-center gap-1 h-3">
            <Icon
              name="watch"
              size="extraSmall"
              className="text-gray-500 flex-shrink-0"
            />
            <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
              위치
            </span>
            <span className="text-xs font-normal leading-none tracking-tight text-gray-400">
              |
            </span>
            <span className="text-xs font-normal leading-none tracking-tight text-gray-500 truncate">
              {chef.address.split(" ")[2] || chef.address.split(" ")[1]}
            </span>
          </div>

          {/* 휴무일 | 일 · 월 */}
          <div className="flex items-center gap-1 h-3">
            <Icon
              name="watch"
              size="extraSmall"
              className="text-gray-500 flex-shrink-0"
            />
            <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
              휴무일
            </span>
            <span className="text-xs font-normal leading-none tracking-tight text-gray-400">
              |
            </span>
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
          </div>

          {/* 인스타그램 (chefDetail에서만 표시) */}
          {variant === "chefDetail" && chef.instagramUrl && (
            <div className="flex items-center gap-1 h-3">
              <Icon
                name="share"
                size="extraSmall"
                className="text-gray-500 flex-shrink-0"
              />
              <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
                공유하기
              </span>
              <span className="text-xs font-normal leading-none tracking-tight text-gray-400">
                |
              </span>
              <a
                href={chef.instagramUrl}
                target="_blank"
                rel="noopener noreferrer"
                className="text-xs font-normal leading-none tracking-tight text-blue-600 underline
  truncate"
              >
                인스타그램
              </a>
            </div>
          )}
        </div>
      </div>

      {/* 예약하기 버튼 (bestChef는 버튼만, chefDetail은 공유+버튼) */}
      <div className="flex gap-6">
        {variant === "chefDetail" && (
          <ShareButton
            title={chef.restaurantName}
            text={chef.nickname ?? chef.name}
          />
        )}

        <Button
          onClick={handleReservation}
          icon={
            chef.catchTableUrl ? (
              <Icon name="catchtable" />
            ) : chef.naverReservationUrl ? (
              <Icon name="naver" />
            ) : undefined
          }
          className={variant === "bestChef" ? "w-full" : "flex-1"}
        >
          예약하기
        </Button>
      </div>
    </div>
  );
};

ChefCard.displayName = "ChefCard";
