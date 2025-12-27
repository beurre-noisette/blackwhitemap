/**
 * Region ENUM name을 한글 축약 표시로 변환하는 유틸리티
 *
 * 백엔드에서 Region ENUM의 name() 값을 반환하므로 (e.g. "SEOUL", "BUSAN")
 * 이를 사용자에게 보여줄 한글 축약형으로 변환합니다.
 *
 * 변환 규칙:
 * 1. 시(특별시/광역시/특별자치시): xx만 표시 (e.g. 서울, 부산)
 * 2. 도: 축약형 표시 (e.g. 경기, 충북, 경남)
 */

/**
 * Region ENUM name과 한글 축약 표시를 매핑하는 맵
 *
 * - 특별시/광역시: "서울", "부산" 등으로 축약
 * - 도: "경기", "충북", "경남" 등으로 축약
 */
const REGION_NAME_MAP: Record<string, string> = {
  // 특별시
  SEOUL: "서울",
  SEJONG: "세종",

  // 광역시
  BUSAN: "부산",
  DAEGU: "대구",
  INCHEON: "인천",
  GWANGJU: "광주",
  DAEJEON: "대전",
  ULSAN: "울산",

  // 도
  GYEONGGI: "경기",
  GANGWON: "강원",
  CHUNGBUK: "충북",
  CHUNGNAM: "충남",
  JEONBUK: "전북",
  JEONNAM: "전남",
  GYEONGBUK: "경북",
  GYEONGNAM: "경남",
  JEJU: "제주",
} as const;

/**
 * Region ENUM name을 한글 축약 표시로 변환
 *
 * @param regionEnumName - 백엔드에서 받은 Region ENUM name (e.g. "SEOUL", "GYEONGGI")
 * @returns 한글 축약 표시 (e.g. "서울", "경기")
 *
 * @example
 * formatRegionName("SEOUL") // "서울"
 * formatRegionName("GYEONGGI") // "경기"
 * formatRegionName("CHUNGBUK") // "충북"
 * formatRegionName("UNKNOWN") // "UNKNOWN" (매핑되지 않은 값은 그대로 반환)
 */
export function formatRegionName(regionEnumName: string): string {
  return REGION_NAME_MAP[regionEnumName] ?? regionEnumName;
}