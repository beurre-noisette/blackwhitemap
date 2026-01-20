import { DailyBestChef } from "@/types/chef";
import { RankCard } from "./RankCard";

export interface Top5ContentProps {
  chefs: DailyBestChef[];
}

/**
 * Top5Content 컴포넌트
 *
 * 일일 인기 셰프 Top5 섹션
 * - 서브타이틀 (제목은 App.tsx에서 표시)
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
      {/* 서브타이틀 */}
      <p className="text-sm font-semibold leading-none tracking-tight text-[#3F3F3F] pt-2 pb-6">
        지금 사람들이 가장 많이 찾아보는 인기 셰프는?!
      </p>

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