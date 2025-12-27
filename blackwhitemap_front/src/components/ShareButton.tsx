import { ButtonHTMLAttributes } from "react";
import { cn } from "@/utils/cn.ts";
import { Icon } from "@/components/Icon.tsx";

export interface ShareButtonProps extends Omit<
  ButtonHTMLAttributes<HTMLButtonElement>,
  "onClick"
> {
  url?: string; // 공유할 URL (기본값: 현재 페이지)
  title?: string;
  text?: string;
  onShareSuccess?: () => void; // 공유 성공 시 콜백
  onShareError?: () => void; // 공유 실패 시 콜백
}

/**
 * ShareButton 컴포넌트
 *
 * 공유 버튼 (32x32 아이콘)
 * - 모바일: Web Share API (네이티브 공유 sheet)
 * - PC: 클립보드 복사 + 알림
 *
 * @example
 * ```tsx
 * <ShareButton
 *   url={window.location.href}
 *   title={chef.nickname}
 *   text={chef.description}
 * />
 * ```
 */
export const ShareButton = ({
  url = window.location.href,
  title,
  text,
  onShareSuccess,
  onShareError,
  className,
  ...props
}: ShareButtonProps) => {
  const handleShare = async () => {
    try {
      // Web Share API 지원 여부 확인
      if (navigator.share) {
        // 모바일: 네이티브 공유 sheet
        await navigator.share({
          url,
          title,
          text,
        });

        onShareSuccess?.();
      } else {
        // PC: 클립보드 복사 fallback
        await navigator.clipboard.writeText(url);

        // TODO: 토스트 알림 컴포넌트로 교체
        alert("링크가 클립보드에 복사되었습니다!");

        onShareSuccess?.();
      }
    } catch (error) {
      // 사용자가 공유 취소한 경우는 에러로 처리하지 않음
      if (error instanceof Error && error.name !== "AbortError") {
        console.error("공유 실패:", error);
        onShareError?.();
      }
    }
  };

  return (
    <button
      type="button"
      onClick={handleShare}
      className={cn(
        // 박스 스타일 제거, 아이콘만 표시
        "flex items-center justify-center",
        "w-8 h-8", // 32x32
        "hover:opacity-70", // 호버 시 살짝 투명
        "active:opacity-50", // 클릭 시 더 투명
        "transition-opacity",
        className,
      )}
      aria-label="공유하기"
      {...props}
    >
      <Icon name="share" size="medium" className="w-8 h-8" />
      {/* medium은 20x20이므로 강제로 32x32로 설정 */}
    </button>
  );
};

ShareButton.displayName = "ShareButton";
