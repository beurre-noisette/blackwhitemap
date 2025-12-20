import { ChefDetail } from "@/types/chef";
import { Icon } from "./Icon";
import { Button } from "./Button";
import { ShareButton } from "./ShareButton";

export interface ChefDetailContentProps {
  chef: ChefDetail;
  variant: "minimized" | "default";
  onReservationClick?: () => void; // 예약하기 버튼 클릭 콜백
}

/**
 * ChefDetailContent 컴포넌트
 *
 * 셰프 상세 정보를 표시
 * - minimized: 가게명 + 닉네임 + 버튼
 * - default: 사진 + 상세정보 + 버튼
 */

export const ChefDetailContent = ({
  chef,
  variant,
  onReservationClick,
}: ChefDetailContentProps) => {
  const handleReservation = () => {
    if (onReservationClick) {
      onReservationClick();
    } else {
      // FIXME 둘 다 없거나 둘 중 하나만 있으면?
      const url = chef.catchTableUrl || chef.naverReservationUrl;

      if (url) {
        window.open(url, "_blank");
      }
    }
  };

  if (variant === "minimized") {
    return (
      <div className="flex flex-col pt-[13px]">
        {/* 가게명 + 이름(혹은 닉네임) */}
        <div className="flex flex-col gap-2 mb-6">
          <h2 className="text-xl font-bold leading-none tracking-tight text-black">
            {chef.restaurantName}
          </h2>
          <p className="text-sm font-semibold leading-none tracking-tight text-blakc">
            {chef.nickname ?? chef.name}
          </p>
        </div>

        {/* 공유하기 + 예약하기 버튼 */}
        <div className="flex gap-6">
          <ShareButton
            title={chef.restaurantName}
            text={chef.nickname ?? chef.name}
          />

          <Button
            onClick={handleReservation}
            icon={
              chef.catchTableUrl ? (
                <Icon name="catchtable" />
              ) : chef.naverReservationUrl ? (
                <Icon name="naver" />
              ) : undefined
            }
            className="flex-1"
          >
            예약하기
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="flex flex-col pt-[13px]">
      {/* 사진 + 정보 */}
      <div className="flex gap-4 mb-6">
        {/* 사진 */}
        <img
          src={chef.imageUrls[0]}
          alt={chef.nickname ?? chef.name}
          className="w-[100px] h-[140px] rounded-[16px] object-cover bg-white"
        />

        {/* 정보 */}
        <div className="flex flex-col gap-3 flex-1">
          {/* 가게명 + 이름(혹은 닉네임) */}
          <div className="flex flex-col gap-2">
            <h2 className="text-xl font-bold leading-none tracking-tight text-black">
              {chef.restaurantName}
            </h2>
            <p className="text-sm font-semibold leading-none tracking-tight text-black">
              {chef.nickname ?? chef.name}
            </p>
          </div>

          {/* 카테고리 / 위치 / 휴무일 / 인스타 */}
          <div className="flex flex-col gap-2">
            {/* 카테고리 | xxx */}
            <div className="flex items-center gap-1 h-3">
              {/* TODO 카테고리 아이콘으로 수정해야 함 */}
              <Icon name="watch" size="extraSmall" className="text-gray-500" />
              <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
                카테고리
              </span>
              <span className="text-xs font-normal leading-none tracking-tight text-gray-400">
                |
              </span>
              <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
                {chef.category}
              </span>
            </div>

            {/* 위치 | xxx */}
            <div className="flex items-center gap-1 h-3">
              {/* TODO 위치 아이콘으로 수정해야 함 */}
              <Icon name="watch" size="extraSmall" className="text-gray-500" />
              <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
                위치
              </span>
              <span className="text-xs font-normal leading-none tracking-tight text-gray-400">
                |
              </span>
              <span className="text-xs font-normal leading-none tracking-tight text-gray-500">
                {chef.address.split(" ")[2] || chef.address.split(" ")[1]}
              </span>
            </div>

            {/* 휴무일 | x · x */}
            <div className="flex items-center gap-1 h-3">
              {/* TODO 휴무일 아이콘으로 수정해야 함 */}
              <Icon name="watch" size="extraSmall" className="text-gray-500" />
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

            {/* 공유하기 | 인스타그램 */}
            {chef.instagramUrl && (
              <div className="flex items-center gap-1 h-3">
                {/* TODO 인스타그램 아이콘으로 수정해야 함 */}
                <Icon
                  name="share"
                  size="extraSmall"
                  className="text-gray-500"
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
                  className="text-xs font-normal leading-none tracking-tight text-blue-600 underline"
                >
                  인스타그램
                </a>
              </div>
            )}
          </div>
        </div>
      </div>

      {/* 공유하기 + 예약하기 버튼 */}
      <div className="flex gap-6">
        <ShareButton
          title={chef.restaurantName}
          text={chef.nickname ?? chef.name}
        />

        <Button
          onClick={handleReservation}
          icon={
            chef.catchTableUrl ? (
              <Icon name="catchtable" />
            ) : chef.naverReservationUrl ? (
              <Icon name="naver" />
            ) : undefined
          }
          className="flex-1"
        >
          예약하기
        </Button>
      </div>
    </div>
  );
};

ChefDetailContent.displayName = "ChefDetailContent";
