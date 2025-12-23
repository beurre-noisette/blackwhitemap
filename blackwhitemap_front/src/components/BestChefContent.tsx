import { BestChef } from "@/types/chef";
import { ChefCard } from "./ChefCard";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination } from "swiper/modules";

// Swiper 스타일 import
import "swiper/css";
import "swiper/css/pagination";

export interface BestChefContentProps {
  chefs: BestChef[];
  onChefClick?: (chef: BestChef) => void;
  showPreview?: boolean; // minimized에서 미리보기 표시 여부
}

/**
 * BestChefContent 컴포넌트
 *
 * 이번주 Best Chef 5명을 Swiper로 표시
 * - 좌우 스크롤 가능
 * - 현재 카드 중앙 배치
 * - 양옆 카드 8px씩 보임
 */
export const BestChefContent = ({
  chefs,
  onChefClick,
}: BestChefContentProps) => {
  if (!chefs.length) {
    return <p className="text-gray-500 text-center py-8">데이터가 없습니다</p>;
  }

  return (
    <div className="w-full">
      <Swiper
        modules={[Pagination]}
        slidesPerView="auto" // 카드 크기에 맞춤
        spaceBetween={12} // 카드 간격 12px
        centeredSlides={true} // 현재 슬라이드 중앙
        pagination={{
          clickable: true,
          bulletClass: "swiper-pagination-bullet",
          bulletActiveClass: "swiper-pagination-bullet-active",
        }}
        className="best-chef-swiper"
      >
        {chefs.map((chef) => (
          <SwiperSlide key={chef.id} style={{ width: "335px" }}>
            <ChefCard
              chef={chef}
              variant="bestChef"
              onReservationClick={() => onChefClick?.(chef)}
            />
          </SwiperSlide>
        ))}
      </Swiper>

      {/* Swiper 페이지네이션 스타일 커스터마이징 */}
      <style>{`
          .best-chef-swiper .swiper-pagination {
            position: relative;
            margin-top: 16px;
          }
          .best-chef-swiper .swiper-pagination-bullet {
            width: 8px;
            height: 8px;
            background: #D3D2D2;
            opacity: 1;
          }
          .best-chef-swiper .swiper-pagination-bullet-active {
            background: #0E0D0D;
          }
        `}</style>
    </div>
  );
};

BestChefContent.displayName = "BestChefContent";
