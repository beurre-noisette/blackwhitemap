import { DailyBestChef } from "@/types/chef";
import { RankCard } from "./RankCard";

export interface Top5ContentProps {
  chefs: DailyBestChef[];
}

/**
 * Top5Content 컴포넌트
 *
 * 일일 인기 셰프 Top5 섹션
 * - 타이틀 + 서브타이틀
 * - 5개의 RankCard 리스트
 */
export const Top5Content = ({ chefs }: Top5ContentProps) => {
  if (!chefs.length) {
    return (
      <p className="text-gray-500 text-center py-8">
        인기 셰프 데이터가 없습니다
      </p>
    );
  }

  return (
    <div className="w-full flex flex-col">
      {/* 타이틀 섹션 */}
      <div className="flex flex-col gap-2 pb-6">
        <h2 className="text-lg font-bold leading-none tracking-tight text-[#0E0D0D]">
          인기 셰프 Top5
        </h2>
        <p className="text-sm font-semibold leading-none tracking-tight text-[#3F3F3F]">
          지금 사람들이 가장 많이 찾아보는 인기 셰프는?!
        </p>
      </div>

      {/* 랭킹 카드 리스트 */}
      <div className="flex flex-col gap-3 pb-8">
        {chefs.map((chef) => (
          <RankCard key={chef.id} chef={chef} />
        ))}
      </div>
    </div>
  );
};

Top5Content.displayName = "Top5Content";