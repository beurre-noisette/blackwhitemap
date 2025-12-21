import { ChefDetail } from "@/types/chef";
import { ChefInfoRow } from "./ChefInfoRow";
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
 * - minimized: 가게명 + 닉네임 + 버튼
 * - default: 사진 + 상세정보 + 버튼
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

            {/* 공유하기 | 인스타그램 */}
            {chef.instagramUrl && (
              <ChefInfoRow iconName="usdCircle" label="공유하기">
                <a
                  href={chef.instagramUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-xs font-normal leading-none tracking-tight text-blue-600 underline"
                >
                  인스타그램
                </a>
              </ChefInfoRow>
            )}
          </div>
        </div>
      </div>

      {/* 공유하기 + 예약하기 버튼 */}
      <ChefActionButtons
        chef={chef}
        onReservationClick={onReservationClick}
        showShareButton={true}
      />
    </div>
  );
};

ChefDetailContent.displayName = "ChefDetailContent";
