/**
 * BottomSheet 상태 (5가지)
 *
 * BestChef 관련:
 * - bestChef-minimized: 제목 + 부제목만 (118px)
 * - bestChef-default: ChefCard + Swiper
 *
 * ChefDetail 관련:
 * - chefDetail-minimized: 가게명 + 닉네임 + 버튼 (162px)
 * - chefDetail-default: 사진 + 상세정보 + 버튼 (260px)
 *
 * Top10:
 * - top10-expanded: 인기 셰프 Top10 리스트 (730px)
 */
export type BottomSheetState =
  | "bestChef-minimized"
  | "bestChef-default"
  | "chefDetail-minimized"
  | "chefDetail-default"
  | "top10-expanded";

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
  "bestChef-minimized": {
    height: 118,
    top: -1,
  },
  "bestChef-default": {
    height: 346,
    top: -1,
  },
  "chefDetail-minimized": {
    height: 162,
    top: 650,
  },
  "chefDetail-default": {
    height: 260,
    top: 552,
  },
  "top10-expanded": {
    height: 730,
    top: 82,
  },
};

/**
 * 드래그 임계값
 * -이 거리(px) 이상 드래그하면 다음 상태로 전환
 */
export const DRAG_THRESHOLD = 50;

/**
 * 컨텐츠 타입별 가능한 상태 전환
 */
export const STATE_TRANSITIONS: Record<
  BottomSheetState,
  { up?: BottomSheetState; down?: BottomSheetState }
> = {
  "bestChef-minimized": {
    up: "bestChef-default",
    // TODO 추후 up: "top10-expanded" 추가
  },
  "bestChef-default": {
    down: "bestChef-minimized",
    // TODO 추후 up: "top10-expanded" 추가
  },
  "chefDetail-minimized": {
    up: "chefDetail-default",
  },
  "chefDetail-default": {
    down: "chefDetail-minimized",
  },
  "top10-expanded": {
    down: "bestChef-default",
  },
};
