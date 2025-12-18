import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

/**
 * Tailwind 클래스를 조건부로 결합하고 충돌을 해결하는 유틸리티 함수
 *
 * @param inputs - 결합할 클래스명들
 * @returns 최종 결합된 문자열의 클래스명
 *
 * e.g.
 * cn('px-2', isActive && 'bg-red-500', undefined, 'py-1')
 * -> 'px-2 bg-red-500 py-1'
 */

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}
