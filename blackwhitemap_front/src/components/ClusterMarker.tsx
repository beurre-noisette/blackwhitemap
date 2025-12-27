import { ChefCluster } from "@/types/map.ts";
import { cn } from "@/utils/cn.ts";
import { formatRegionName } from "@/utils/regionUtils.ts";

interface ClusterMarkerProps {
  cluster: ChefCluster;
  onClick: () => void;
}

/**
 * 클러스터 마커 컴포넌트
 *
 * 시/도별 셰프 수를 표시하는 마커입니다.
 *
 * 2가지 타입:
 * 1. 흑+백 둘 다 있는 경우: "서울 25 15" (지명 + 흑count + 백count)
 * 2. 하나만 있는 경우: "제주 2" (지명 + count)
 *
 * 디자인:
 * - 흰색 배경에 회색 테두리
 * - 둥근 모서리 (border-radius: 14px)
 * - 흑count: 검은색 텍스트
 * - 백count: 흰색 텍스트 + 검은색 outline (text-stroke)
 * - width: hug contents (자동 조절)
 */
export const ClusterMarker = ({ cluster, onClick }: ClusterMarkerProps) => {
  const { blackCount, whiteCount, region } = cluster;

  // Region ENUM name을 한글 축약 표시로 변환 (e.g. "SEOUL" → "서울", "GYEONGGI" → "경기")
  const regionName = formatRegionName(region);

  const hasBoth = blackCount > 0 && whiteCount > 0;

  const textBaseStyle = "font-bold text-xs leading-4 tracking-[-0.02em]";

  return (
    <div
      onClick={onClick}
      className={cn(
        "flex items-center justify-center",
        "bg-white border border-gray-500 rounded-[14px]",
        "cursor-pointer hover:bg-gray-100 transition-colors",
        "pt-1 pr-3 pb-[6px] pl-3", // padding: 4px 12px 6px 12px
        "gap-[10px]",
        "h-[26px]",
        {
          /* FIXME 1. 행간이 안맞나 2. 텍스트(하위 컨테이너)가 가운데 정렬이 안되나 3. 상위 컨테이너 서울+(숫자)의 컨테이너가 버튼의 가운데 정렬로 정의되지 않았나 */
        },
      )}
    >
      <div
        className={cn(
          "flex items-center h-4", // height: 16px
          "gap-[6px]", // 지명과 첫 번째 count 간격
        )}
      >
        {/* 지명 */}
        <span
          className={cn(textBaseStyle, "text-black")}
          style={{ fontFamily: "Noto Sans KR" }}
        >
          {regionName}
        </span>

        {/* 흑과 백 둘 다 있는 경우 */}
        {hasBoth ? (
          <>
            {/* 흑 갯수 */}
            <span
              className={cn(textBaseStyle, "text-black")}
              style={{ fontFamily: "Noto Sans KR" }}
            >
              {blackCount}
            </span>

            {/* 백 갯수 - 흰색 텍스트 + 검은색 outline */}
            <span
              className={cn(textBaseStyle, "text-white")}
              style={{
                fontFamily: "Noto Sans KR",
                WebkitTextStroke: "0.4px var(--color-black)",
                marginLeft: "-2px",
              }}
            >
              {whiteCount}
            </span>
          </>
        ) : /* 하나만 있는 경우 - 조건부 스타일 */
        blackCount > 0 ? (
          /* 흑 갯수: 검은색 텍스트 */
          <span
            className={cn(textBaseStyle, "text-black")}
            style={{ fontFamily: "Noto Sans KR" }}
          >
            {blackCount}
          </span>
        ) : (
          /* 백 갯수: 흰색 텍스트 + 검은색 outline */
          <span
            className={cn(textBaseStyle, "text-white")}
            style={{
              fontFamily: "Noto Sans KR",
              WebkitTextStroke: "0.4px var(--color-black)",
            }}
          >
            {whiteCount}
          </span>
        )}
      </div>
    </div>
  );
};
