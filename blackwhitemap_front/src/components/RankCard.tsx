import { DailyBestChef } from "@/types/chef";
import { cn } from "@/utils/cn";
import { formatCategoryLabel } from "@/utils/regionUtils";
import { Icon } from "./Icon";

export interface RankCardProps {
  chef: DailyBestChef;
  className?: string;
}

/**
 * RankCard 컴포넌트
 *
 * 일일 인기 셰프 Top5 랭킹 카드
 * - chef.type에 따라 블랙/화이트 테마 적용
 * - BLACK: 검정 배경, 흰색 텍스트
 * - WHITE: 흰색 배경, 검정 테두리
 */
export const RankCard = ({ chef, className }: RankCardProps) => {
  // chef.type에 따라 테마 결정
  const isBlack = chef.type === "BLACK";

  // 예약 URL 결정 (캐치테이블 우선)
  const reservationUrl = chef.catchTableUrl || chef.naverReservationUrl;
  const isReservationAvailable = !!reservationUrl;

  /**
   * 예약 버튼 클릭 핸들러
   */
  const handleReservationClick = () => {
    if (reservationUrl) {
      window.open(reservationUrl, "_blank");
    }
  };

  return (
    <div
      className={cn(
        // 기본 스타일
        "w-full h-[74px]",
        "rounded-[20px]",
        "p-4",
        "flex items-center gap-4",
        // 테마별 스타일
        isBlack
          ? "bg-[#0E0D0D]" // 블랙 테마: 검정 배경
          : "bg-white border border-[#E4E3E3]", // 화이트 테마: 흰색 배경 + 테두리
        className,
      )}
    >
      {/* 왼쪽 영역: 순위 + 아이콘 + 셰프명 */}
      <div className="flex flex-col gap-1 w-[80px] h-[42px] shrink-0">
        {/* 순위 + 흑/백 아이콘 */}
        <div className="flex items-center gap-1">
          <span
            className={cn(
              "text-lg font-bold leading-none tracking-tight",
              isBlack ? "text-white" : "text-[#0E0D0D]",
            )}
          >
            {chef.rank}위
          </span>
          {/* 흑/백 아이콘 (chef-black-seg / chef-white-seg 사용) */}
          <Icon
            name={isBlack ? "chef-black-seg" : "chef-white-seg"}
            size="small"
          />
        </div>

        {/* 셰프명 */}
        <span
          className={cn(
            "text-lg font-bold leading-none tracking-tight truncate",
            isBlack ? "text-white" : "text-[#0E0D0D]",
          )}
        >
          {chef.nickname ?? chef.name}
        </span>
      </div>

      {/* 구분선 */}
      <div className="w-px h-[42px] bg-[#D3D2D2] shrink-0" />

      {/* 중앙 영역: 업장명 + 위치|카테고리 */}
      <div className="flex-1 flex flex-col gap-2 min-w-0">
        {/* 업장명 */}
        <span
          className={cn(
            "text-base font-bold leading-none tracking-tight truncate",
            isBlack ? "text-white" : "text-[#0E0D0D]",
          )}
        >
          {chef.restaurantName}
        </span>

        {/* 위치 | 카테고리 */}
        <div className="flex items-center gap-[7px]">
          <span
            className={cn(
              "text-sm font-semibold leading-none tracking-tight truncate",
              isBlack ? "text-[#B7B6B6]" : "text-[#868686]",
            )}
          >
            {chef.smallAddress}
          </span>
          <div className="w-px h-[10px] bg-[#D3D2D2] shrink-0 rounded-full" />
          <span
            className={cn(
              "text-sm font-semibold leading-none tracking-tight truncate",
              isBlack ? "text-[#B7B6B6]" : "text-[#868686]",
            )}
          >
            {formatCategoryLabel(chef.category)}
          </span>
        </div>
      </div>

      {/* 예약 버튼 (아이콘만) */}
      <button
        onClick={handleReservationClick}
        disabled={!isReservationAvailable}
        className={cn(
          "w-8 h-8 rounded-full flex items-center justify-center shrink-0",
          "transition-colors",
          isBlack
            ? "bg-white hover:bg-gray-100" // 블랙 테마: 흰색 배경
            : "bg-[#0E0D0D] hover:bg-gray-800", // 화이트 테마: 검정 배경
          !isReservationAvailable && "opacity-50 cursor-not-allowed",
        )}
        aria-label="예약하기"
      >
        <Icon name="catchtable" size="medium" />
      </button>
    </div>
  );
};

RankCard.displayName = "RankCard";
