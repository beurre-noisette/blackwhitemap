import { ReactNode } from "react";
import { motion, PanInfo } from "framer-motion";
import { cn } from "@/utils/cn";
import {
  BOTTOM_SHEET_SPECS,
  BottomSheetState,
  DRAG_THRESHOLD,
  STATE_TRANSITIONS,
} from "@/types/bottomSheet";

export interface BottomSheetProps {
  /**
   * BottomSheet 상태 (minimized, default, expanded)
   */
  state: BottomSheetState;

  /**
   * 상태 변경 콜백
   * @param newState - 새로운 상태
   */
  onStateChange: (newState: BottomSheetState) => void;

  /**
   * BottomSheet 내부에 표시될 콘텐츠
   */
  children: ReactNode;

  /**
   * 추가 CSS 클래스 (선택적)
   */
  className?: string;

  /**
   * HTML id 속성 (선택적)
   */
  id?: string;
}

export const BottomSheet = ({
  state,
  onStateChange,
  children,
  className,
  id,
}: BottomSheetProps) => {
  const spec = BOTTOM_SHEET_SPECS[state];

  /**
   * 드래그 종료 시 이동 거리를 계산하여 상태 전환
   *
   * @param _event - 드래그 이벤트 (사용하지 않음)
   * @param info - 드래그 정보 (offset: 이동 거리)
   */
  const handleDragEnd = (
    _event: MouseEvent | TouchEvent | PointerEvent,
    info: PanInfo,
  ) => {
    const offsetY = info.offset.y; // 드래그 시작점으로부터 Y 이동 거리

    // 임계값 이상 드래그했는지 확인
    if (Math.abs(offsetY) < DRAG_THRESHOLD) {
      // 임계값 미만 → 원래 상태 유지
      return;
    }

    const transitions = STATE_TRANSITIONS[state];

    if (offsetY > 0 && transitions.down) {
      // 아래로 드래그 -> 축소
      onStateChange(transitions.down);
    } else if (offsetY < 0 && transitions.up) {
      // 위로 드래그 -> 확대
      onStateChange(transitions.up);
    }
  };

  return (
    <motion.div
      id={id}
      className={cn(
        // 기본 스타일
        "absolute left-0 right-0", // 부모 기준 위치
        "w-full", // 부모 너비에 맞춤
        "rounded-t-[32px]", // border-top-left/right-radius: 32px
        "bg-white",
        "shadow-[0px_0px_32px_0px_rgba(0,0,0,0.24)]", // box-shadow
        "transition-all",
        "duration-500",
        // "overflow-hidden", // 내용 넘침 방지
        state.includes("minimized") ? "overflow-visible" : "overflow-hidden", // minimized는 overflow visible
        "z-50", // 다른 요소 위에 표시
        className,
      )}
      // 상태별 높이 및 위치 (하단 고정 방식)
      style={{
        height: `${spec.height}px`,
        top: `calc(100% - ${spec.height}px)`,
      }}
      transition={{
        type: "spring", // 스프링 애니메이션
        stiffness: 400, // 강성 증가 (더 빠르게 스냅)
        damping: 40, // 감쇠 증가 (빠르게 정지)
        duration: 0.3, // 최대 애니메이션 시간
      }}
      // 드래그 설정
      drag="y" // Y축(세로)으로만 드래그
      dragElastic={0} // 탄성 제거 (경계 밖으로 드래그 불가)
      dragConstraints={{
        top: 0, // 위쪽 제한 (부모 기준)
        bottom: 0, // 아래쪽 제한 (부모 기준, 현재 위치 고정)
      }}
      dragSnapToOrigin // 드래그 종료 시 원래 위치로 돌아감 (스냅 효과)
      onDragEnd={handleDragEnd}
    >
      {/* 핸들 영역 */}
      <div
        className="relative w-full h-[19px] flex items-center justify-center cursor-grab
  active:cursor-grabbing"
      >
        <div
          className={cn(
            "w-[50px] h-1", // width: 50px, height: 4px
            "rounded-[100px]", // border-radius: 100px
            "bg-gray-300", // background: #D3D2D2
            "mt-2", // top: 8px
          )}
          aria-label="바텀시트 핸들"
        />
      </div>

      {/* 콘텐츠 영역 */}
      <div
        className={cn(
          "w-full h-[calc(100%-19px)]", // 전체 높이 - 핸들 높이
          "overflow-hidden", // 스크롤 비활성화 (마우스 및 터치 스크롤 방지)
          "px-5", // 좌우 padding
        )}
      >
        {children}
      </div>
    </motion.div>
  );
};

BottomSheet.displayName = "BottomSheet";
