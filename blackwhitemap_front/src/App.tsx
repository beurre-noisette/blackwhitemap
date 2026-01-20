import { useEffect, useState } from "react";
import { BottomSheetState } from "@/types/bottomSheet.ts";
import {
  SegmentedControl,
  SegmentValue,
} from "@/components/SegmentedControl.tsx";
import { MapControlBar } from "@/components/MapControlBar.tsx";
import { RankingButton } from "@/components/RankingButton.tsx";
import { BestChef, ChefDetail, DailyBestChef } from "@/types/chef.ts";
import { ChefCluster } from "@/types/map.ts";
import { fetchChefClusters, fetchChefs } from "@/api/chefApi";
import { fetchWeeklyBestChefs, fetchDailyBestChefs } from "@/api/rankingApi";
import loading from "@/assets/images/loading.png";
import { KakaoMap } from "@/components/KakaoMap.tsx";
import { BottomSheet } from "@/components/BottomSheet.tsx";
import { BestChefContent } from "@/components/BestChefContent.tsx";
import { ChefDetailContent } from "@/components/ChefDetailContent.tsx";
import { Top5Content } from "@/components/Top5Content.tsx";

function App() {
  const [sheetState, setSheetState] =
    useState<BottomSheetState>("bestChef-default");
  const [filter, setFilter] = useState<SegmentValue>("ALL");
  const [selectedChef, setSelectedChef] = useState<ChefDetail | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  // 전체 셰프 목록 (지도 + 상세 화면용)
  const [allChefs, setAllChefs] = useState<ChefDetail[]>([]);

  // 이번주 Best Chef 5명 (랭킹용)
  const [bestChefs, setBestChefs] = useState<BestChef[]>([]);

  // 일일 인기 셰프 Top5
  const [dailyBestChefs, setDailyBestChefs] = useState<DailyBestChef[]>([]);

  // 클러스터 데이터
  const [clusters, setClusters] = useState<ChefCluster[]>([]);

  /**
   * 초기 데이터 로드
   */
  useEffect(() => {
    const loadData = async () => {
      try {
        // 최소 로딩 시간 보장을 위한 딜레이
        const minLoadingTime = new Promise((resolve) =>
          setTimeout(resolve, 1500),
        );

        // 데이터 로딩과 최소 대기 시간을 병렬로 처리
        const [chefsData, bestChefsData, dailyBestData, clustersData] =
          await Promise.all([
            fetchChefs(), // GET /performer/chefs
            fetchWeeklyBestChefs(), // GET /ranking/weekly-best?limit=5
            fetchDailyBestChefs(), // GET /ranking/daily-best
            fetchChefClusters(), // GET /performer/chefs/cluster
            minLoadingTime,
          ]);

        setAllChefs(chefsData);
        setBestChefs(bestChefsData);
        setDailyBestChefs(dailyBestData);
        setClusters(clustersData);

        // 초기 선택 셰프 설정 (첫 번째 베스트 셰프)
        if (chefsData.length > 0 && bestChefsData.length > 0) {
          const firstBestChef = bestChefsData[0];
          if (firstBestChef) {
            const fullChef = chefsData.find(
              (chef) => chef.id === firstBestChef.id,
            );
            if (fullChef) {
              setSelectedChef(fullChef);
            }
          }
        }
      } catch (error) {
        console.error("Failed to load data:", error);
      } finally {
        setIsLoading(false);
      }
    };

    loadData();
  }, []);

  const isBestChef =
    sheetState.startsWith("bestChef") || sheetState === "top5-expanded";
  const isChefDetail = sheetState.startsWith("chefDetail");
  const isTop5Expanded = sheetState === "top5-expanded";

  /**
   * Segmented Control 필터에 따라 클러스터 데이터 필터링
   */
  const filteredClusters = clusters
    .map((cluster) => {
      if (filter === "ALL") return cluster;
      return {
        ...cluster,
        blackCount: filter === "BLACK" ? cluster.blackCount : 0,
        whiteCount: filter === "WHITE" ? cluster.whiteCount : 0,
      };
    })
    .filter((cluster) => cluster.blackCount > 0 || cluster.whiteCount > 0);

  /**
   * Segmented Control 필터에 따라 셰프 데이터 필터링
   */
  const filteredChefsForMap = allChefs.filter((chef) => {
    if (filter === "ALL") return true;
    return chef.type === filter;
  });

  /**
   * 개별 셰프 마커 클릭 핸들러
   */
  const handleChefMarkerClick = (chef: ChefDetail) => {
    setSelectedChef(chef);
    setSheetState("chefDetail-default");
  };

  /**
   * 랭킹 버튼 클릭 핸들러
   * - 현재 바텀시트가 bestChef-default가 아닌 경우에만 상태 변경
   */
  const handleRankingClick = () => {
    if (sheetState !== "bestChef-default") {
      setSheetState("bestChef-default");
    }
  };

  const handleMapInteract = () => {
    setSheetState((prev) => {
      if (prev === "bestChef-default") return "bestChef-minimized";
      if (prev === "chefDetail-default") return "chefDetail-minimized";
      if (prev === "top5-expanded") return "bestChef-minimized";
      return prev;
    });
  };

  // 로딩 중이거나 selectedChef가 없으면 로딩 화면 표시
  if (isLoading || !selectedChef) {
    return (
      <div className="h-dvh w-full bg-gray-50 flex justify-center">
        <div className="relative w-full max-w-[430px] h-full bg-gray-100 flex items-center justify-center overflow-hidden">
          <img
            src={loading}
            alt="loading"
            className="w-full h-full object-cover"
          />
        </div>
      </div>
    );
  }

  return (
    <div className="h-dvh w-full bg-gray-50 flex justify-center">
      <div className="relative w-full max-w-[430px] h-full bg-gray-100 overflow-hidden">
        {/* MapControlBar: 랭킹 버튼 + 세그먼트 컨트롤 */}
        <div className="absolute top-0 left-0 right-0 z-40 flex justify-center">
          <MapControlBar>
            <RankingButton onClick={handleRankingClick} isActive={isBestChef} />
            <SegmentedControl value={filter} onChange={setFilter} />
          </MapControlBar>
        </div>

        {/* 카카오맵 */}
        <div className="w-full h-full">
          <KakaoMap
            clusters={filteredClusters}
            chefs={filteredChefsForMap}
            onChefClick={handleChefMarkerClick}
            onMapInteract={handleMapInteract}
          />
        </div>

        {/* BottomSheet */}
        <BottomSheet state={sheetState} onStateChange={setSheetState}>
          {isBestChef && (
            <>
              <div className="pt-[15px] pb-[24px]">
                <h2 className="text-lg font-bold leading-none tracking-tight text-black mb-2">
                  이번주 흑백요리사2 Best Chef
                </h2>
                <p className="text-sm font-semibold leading-none tracking-tight text-gray-600">
                  이번주 흑백요리사에서 핫한 셰프의 가게를 알려드려요
                </p>
              </div>

              <BestChefContent
                chefs={bestChefs}
                showPreview={sheetState === "bestChef-minimized"}
              />

              <h2 className="text-lg font-bold leading-none tracking-tight text-[#0E0D0D] mt-4">
                인기 셰프 Top5
              </h2>
            </>
          )}

          {isChefDetail && (
            <ChefDetailContent
              chef={selectedChef}
              variant={
                sheetState === "chefDetail-minimized" ? "minimized" : "default"
              }
            />
          )}

          {isTop5Expanded && <Top5Content chefs={dailyBestChefs} />}
        </BottomSheet>
      </div>
    </div>
  );
}

export default App;
