/**
 * BottomSheet 상태 (5가지)
 *
 * BestChef 관련:
 * - bestChef-minimized: 제목 + 부제목만 (118px)
 * - bestChef-default: ChefCard + Swiper (346px)
 *
 * ChefDetail 관련:
 * - chefDetail-minimized: 가게명 + 닉네임 + 버튼 (162px)
 * - chefDetail-default: 사진 + 상세정보 + 버튼 (260px)
 *
 * Top5:
 * - top5-expanded: 인기 셰프 Top5 리스트 (730px)
 */
export type BottomSheetState =
  | "bestChef-minimized"
  | "bestChef-default"
  | "chefDetail-minimized"
  | "chefDetail-default"
  | "top5-expanded";

/**
 * BottomSheet 상태별 스펙
 * 기본적으로 하단 고정 방식 (calc(100% - height)) 사용
 * expanded 상태는 상단 고정 방식 (top 값 지정) 사용
 */
export interface BottomSheetSpec {
  height: number; // px
  top?: number; // px (상단 고정 위치, expanded 상태에서 사용)
}

/**
 * 상태별 스펙 정의
 * 화면 높이에 관계없이 하단에서 height만큼 올라옴
 */
export const BOTTOM_SHEET_SPECS: Record<BottomSheetState, BottomSheetSpec> = {
  "bestChef-minimized": { height: 118 },
  "bestChef-default": { height: 395 },
  "chefDetail-minimized": { height: 162 },
  "chefDetail-default": { height: 260 },
  "top5-expanded": { height: 730, top: 82 },
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
  },
  "bestChef-default": {
    down: "bestChef-minimized",
    up: "top5-expanded",
  },
  "chefDetail-minimized": {
    up: "chefDetail-default",
  },
  "chefDetail-default": {
    down: "chefDetail-minimized",
  },
  "top5-expanded": {
    down: "bestChef-default",
  },
};
