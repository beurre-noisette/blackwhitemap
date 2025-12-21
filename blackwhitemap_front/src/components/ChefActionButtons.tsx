import { ChefDetail } from "@/types/chef.ts";
import { Icon } from "@/components/Icon.tsx";
import { ShareButton } from "@/components/ShareButton.tsx";
import { Button } from "@/components/Button.tsx";

export interface ChefActionButtonsProps {
  chef: ChefDetail;

  /**
   * 예약하기 버튼 클릭 콜백 (선택적 요소)
   * - 지정하면 새 창 열기 대신 콜백 실행
   * - 미지정 시 예약 URL로 새 창 열기
   */
  onReservationClick?: () => void;

  /**
   * 공유하기 버튼 표시 여부
   * - true: 공유하기 버튼 + 예약하기 버튼 (flex-1)
   * - false: 예약하기 버튼만 (w-full)
   */
  showShareButton?: boolean;

  className?: string;
}

/**
 * ChefActionButtons 컴포넌트
 *
 * 셰프 상세 페이지의 액션 버튼 영역
 * - 공유하기 버튼 (선택적)
 * - 예약하기 버튼
 *
 * 예약하기 버튼 규칙:
 * 1. catchTableUrl과 naverReservationUrl 둘 다 있으면 → 캐치테이블만 표시
 * 2. 둘 중 하나만 있으면 → 해당 플랫폼 버튼 표시
 * 3. 둘 다 없으면 → disabled 버튼 표시
 * 4. 클릭 시 새 창에서 예약 페이지 열기
 */
export const ChefActionButtons = ({
  chef,
  onReservationClick,
  showShareButton = false,
  className,
}: ChefActionButtonsProps) => {
  // 예약 URL 결정 (캐치테이블 우선)
  const reservationUrl: string | null =
    chef.catchTableUrl || chef.naverReservationUrl;

  // URL 존재 여부
  const isReservationAvailable: boolean = !!reservationUrl;

  /**
   * 예약하기 버튼 클릭 핸들러
   * - onReservationClick이 있으면 콜백 실행
   * - 없으면 예약 URL로 새 창 열기
   */
  const handleReservation = () => {
    if (onReservationClick) {
      onReservationClick();
    } else if (reservationUrl) {
      window.open(reservationUrl, "_blank");
    }
  };

  // 예약하기 버튼 아이콘 결정
  const getReservationIcon = () => {
    if (chef.catchTableUrl) {
      return <Icon name="catchtable" />;
    }

    if (chef.naverReservationUrl) {
      return <Icon name="naver" />;
    }

    return undefined;
  };

  return (
    <div className={className ?? "flex gap-6"}>
      {/* 공유하기 버튼 (showShareButton이 true일 때만 표시) */}
      {showShareButton && (
        <ShareButton
          title={chef.restaurantName}
          text={chef.nickname ?? chef.name}
        />
      )}

      {/* 예약하기 버튼 */}
      <Button
        onClick={handleReservation}
        icon={getReservationIcon()}
        disabled={!isReservationAvailable}
        className={showShareButton ? "flex-1" : "w-full"}
      >
        {isReservationAvailable
          ? "예약하기"
          : "예약을 지원하지 않는 가게입니다"}
      </Button>
    </div>
  );
};

ChefActionButtons.displayName = "ChefActionButtons";
