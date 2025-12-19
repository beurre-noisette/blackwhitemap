import { Icon, IconName } from "@/components/Icon.tsx";
import { cva } from "class-variance-authority";
import { HTMLAttributes } from "react";
import { cn } from "@/utils/cn.ts";

/**
 * SegmentedControl 컴포넌트의 세그먼트 값 타입
 * - ALL: 모든 셰프
 * - BLACK: 흑요리사
 * - WHITE: 백요리사
 */
export type SegmentValue = "ALL" | "BLACK" | "WHITE";

/**
 * 세그먼트 항목 정의
 */
interface Segment {
  value: SegmentValue;
  label: string;
  icon?: IconName;
}

/**
 * 세그먼트 버튼의 스타일 variants 정의
 */
const segmentVariants = cva(
  // base: 모든 세그먼트에 공통으로 적용되는 기본 스타일
  [
    "flex items-center justify-center", // flex 레이아웃
    "w-[101px] h-8", // width: 101px, height: 32px
    "rounded-[100px]", // border-radius: 100px
    "px-2.5 py-1.5", // padding: 6px 10px (px-2.5 = 10px, py-1.5 = 6px)
    "gap-1", // gap: 4px
    "font-semibold text-sm", // font-weight: 600, font-size: 14px
    "leading-[140%]", // line-height: 140%
    "tracking-tight", // letter-spacing: -2% (tracing-tight = -0.025em, 가장 가까운 값)
    "transition-all duration-200", // 부드러운 전환 효과
    "cursor-pointer",
  ],
  {
    variants: {
      selected: {
        true: [
          "bg-black text-white",
          "shadow-[0px_0px_24px_0px_rgba(0,0,0,0.16)]", // box-shadow: 0px 0px 24px 0px #00000029
        ],
        false: [
          "bg-transparent text-black", // 배경은 투명하게 텍스트는 검정
          "hover:bg-gray-100", // 호버 시 약간의 배경색
        ],
      },
    },
    defaultVariants: {
      selected: false,
    },
  },
);

export interface SegmentedControlProps extends Omit<
  HTMLAttributes<HTMLDivElement>,
  "onChange"
> {
  // 현재 선택된 세그먼트 값
  value: SegmentValue;

  /**
   * 세그먼트 선택 변경 시 호출되는 콜백
   * @param value - 새로 선택된 세그먼트 값
   */
  onChange: (value: SegmentValue) => void;
}

/**
 * 세그먼트 목록 정의
 * - All: 텍스트만
 * - 흑/백: chef-black/chef-white 아이콘 + 텍스트
 */
const SEGMENTS: Segment[] = [
  { value: "ALL", label: "All" },
  { value: "BLACK", label: "흑", icon: "chef-black" },
  { value: "WHITE", label: "백", icon: "chef-white" },
];

/**
 * 셰프 필터링을 위한 세그먼트 컨트롤
 * - All: 모든 셰프 표시
 * - 흑: 흑 셰프만 표시
 * - 백: 백 셰프만 표시
 */

export const SegmentedControl = ({
  className,
  value,
  onChange,
  ...props
}: SegmentedControlProps) => {
  return (
    <div
      className={cn(
        // 컨테이너 스타일
        "inline-flex items-center", // flex 레이아웃
        "w-[335px] h-12", // width: 335px, height: 48px
        "rounded-[100px]", // border-radius: 100px
        "p-2", // padding: 8px
        "gap-2", // gap: 8px
        "bg-white",
        "shadow-[0px_0px_24px_0px_rgba(0,0,0,0.2)]", // box-shadow: 0px 0px 24px 0px #00000033
        className,
      )}
      {...props}
    >
      {/* 내부 세그먼트 영역 */}
      <div className="flex items-center w-[304px] h-8 gap-2">
        {/* gap: 8px */}
        {SEGMENTS.map((segment: Segment) => {
          const isSelected: boolean = value === segment.value;

          const iconName: IconName | undefined = segment.icon;

          return (
            <button
              key={segment.value}
              type="button"
              onClick={() => onChange(segment.value)}
              className={cn(segmentVariants({ selected: isSelected }))}
            >
              {/* 아이콘이 있으면 렌더링 */}
              {iconName && <Icon name={iconName} size="medium" />}

              {/* 텍스트 */}
              <span>{segment.label}</span>
            </button>
          );
        })}
      </div>
    </div>
  );
};

SegmentedControl.displayName = "SegmentedControl";
