import { ButtonHTMLAttributes, forwardRef, ReactNode } from "react";
import { cva, type VariantProps } from "class-variance-authority";
import { cn } from "../utils/cn";

/**
 * Button 컴포넌트의 스타일 variants 정의
 */

const buttonVariants = cva(
  // base: 모든 버튼에 공통으로 적용되는 기본 스타일
  [
    "inline-flex items-center justify-center",
    "w-[301px] h-8", // width: 301px, height: 32px (h-8 = 32px)
    "rounded-[41px]", // border-radius: 41px
    "px-4 py-1", // padding: 4px 16px
    "gap-2", // gap: 8px (gap-2 = 8px)
    "font-semibold text-sm", // font-weight: 600, font-size: 14px
    "leading-none", // line-height: 100%
    "tracking-tight", // letter-spacing: -2% (tracking-tight = -0.025em, 가장 가까운 값)
    "transition-colors", // 부드러운 색상 전환
    "focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2", // 접근성
  ],
  {
    variants: {
      // variant: 버튼의 상태
      variant: {
        // primary: 기본 활성 상태
        primary: [
          "bg-black text-white", // 배경: Black, 텍스트: White
          "hover:bg-gray-800", // 호버 시 약간 밝게
          "active:bg-gray-900", // 클릭 시 더 어둡게
          "focus-visible:ring-gray-700", // 포커스 링 색상
        ],
        // disabled: 비활성 상태
        disabled: [
          "bg-gray-400 text-white", // 배경: Gray 400 (#B7B6B6), 텍스트: White
          "cursor-not-allowed", // 비활성 커서
        ],
      },
    },
    defaultVariants: {
      variant: "primary",
    },
  },
);

export interface ButtonProps extends Omit<
  ButtonHTMLAttributes<HTMLButtonElement>,
  "disabled"
> {
  /**
   * 버튼 variant (primary | disabled)
   */
  variant?: VariantProps<typeof buttonVariants>["variant"];

  /**
   * 버튼 비활성화 여부
   * true일 경우 disabled variant 적용
   */
  disabled?: boolean;

  /**
   * 버튼 좌측에 표시될 아이콘
   */
  icon?: ReactNode;

  /**
   * 버튼 텍스트 (children 대신 사용 가능)
   */
  label?: string;
}

export const Button = forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant, disabled, icon, label, children, ...props }, ref) => {
    // disabled가 true이면 variant를 'disabled'로 강제 설정
    const finalVariant = disabled ? "disabled" : variant;

    return (
      <button
        ref={ref}
        className={cn(buttonVariants({ variant: finalVariant }), className)}
        disabled={disabled}
        {...props}
      >
        {/* 아이콘이 있으면 먼저 렌더링 */}
        {icon && (
          <span className="flex-shrink-0 w-5 h-5 rounded overflow-hidden">
            {/* width: 20px(w-5), height: 20px(h-5), border-radius: 4px(rounded) */}
            {icon}
          </span>
        )}

        {/* 텍스트 렌더링 */}
        <span>{label || children}</span>
      </button>
    );
  },
);

Button.displayName = "Button";
