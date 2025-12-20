/**
 * BottomSheet 상태
 * - minimized: 접힌 모습 (94px)
 * - default: 기본 모습 (346px)
 * - expanded: 늘린 모습 (730px)
 */
export type BottomSheetState = "minimized" | "default" | "expanded";

/**
 * BottomSheet 상태별 스펙
 */
export interface BottomSheetSpec {
  height: number; // px
  top: number; // px (화면 상단으로부터의 거리)
}

/**
 * 상태별 스펙 정의
 * 부모 프레임: 375 x 812
 */
export const BOTTOM_SHEET_SPECS: Record<BottomSheetState, BottomSheetSpec> = {
  minimized: {
    height: 94,
    top: 718,
  },
  default: {
    height: 346,
    top: 466,
  },
  expanded: {
    height: 730,
    top: 82,
  },
};

/**
 * 드래그 임계값
 * -이 거리(px) 이상 드래그하면 다음 상태로 전환
 */
export const DRAG_THRESHOLD = 50;
