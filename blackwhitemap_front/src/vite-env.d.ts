/**
 * SVG 파일을 모듈로 import 할 수 있도록 타입 선언
 *
 * VITE는 SVG를 URL 문자열로 반환합니다.
 */
declare module "*.svg" {
  const content: string;
  export default content;
}
