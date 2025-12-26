import { ChefDetailContent } from "@/components/ChefDetailContent.tsx";
import { BestChefContent } from "@/components/BestChefContent.tsx";
import { useEffect, useState } from "react";
import { BottomSheetState } from "@/types/bottomSheet.ts";
import {
  SegmentedControl,
  SegmentValue,
} from "@/components/SegmentedControl.tsx";
import { BestChef, ChefDetail } from "@/types/chef.ts";
import { ChefCluster } from "@/types/map.ts";
import { BottomSheet } from "@/components/BottomSheet.tsx";
import { KakaoMap } from "@/components/KakaoMap.tsx";
import { fetchChefs, fetchChefClusters } from "@/api/chefApi";
import { fetchWeeklyBestChefs } from "@/api/rankingApi";

function App() {
  const [sheetState, setSheetState] =
    useState<BottomSheetState>("bestChef-default");
  const [filter, setFilter] = useState<SegmentValue>("ALL");
  const [selectedChef, setSelectedChef] = useState<ChefDetail | null>(null);

  // 전체 셰프 목록 (지도 + 상세 화면용)
  const [allChefs, setAllChefs] = useState<ChefDetail[]>([]);

  // 이번주 Best Chef 5명 (랭킹용)
  const [bestChefs, setBestChefs] = useState<BestChef[]>([]);

  // 클러스터 데이터
  const [clusters, setClusters] = useState<ChefCluster[]>([]);

  /**
   * 초기 데이터 로드
   */
  useEffect(() => {
    const loadData = async () => {
      try {
        // 병렬로 3개 API 호출
        const [chefsData, bestChefsData, clustersData] = await Promise.all([
          fetchChefs(), // GET /performer/chefs
          fetchWeeklyBestChefs(), // GET /ranking/weekly-best?limit=5
          fetchChefClusters(), // GET /performer/chefs/cluster
        ]);

        setAllChefs(chefsData);
        setBestChefs(bestChefsData);
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
      }
    };

    loadData();
  }, []);

  const isBestChef = sheetState.startsWith("bestChef");
  const isChefDetail = sheetState.startsWith("chefDetail");

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
   * Best Chef 카드 클릭 핸들러
   * - allChefs에서 전체 정보 찾기
   */
  const handleBestChefClick = (bestChef: BestChef) => {
    const fullChef = allChefs.find((chef) => chef.id === bestChef.id);

    if (fullChef) {
      setSelectedChef(fullChef);
      setSheetState("chefDetail-default");
    } else {
      console.error(`Chef with id ${bestChef.id} not found in allChefs`);
    }
  };

  // selectedChef가 null이면 렌더링하지 않음
  if (!selectedChef) {
    return <div>Loading...</div>;
  }

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center p-8">
      <div className="relative w-[375px] h-[812px] bg-gray-100 overflow-hidden rounded-2xl shadow-xl">
        {/* SegmentedControl */}
        <div className="absolute top-4 left-0 right-0 z-40 flex justify-center">
          <SegmentedControl value={filter} onChange={setFilter} />
        </div>

        {/* 카카오맵 */}
        <div className="w-full h-full">
          <KakaoMap
            clusters={filteredClusters}
            chefs={filteredChefsForMap}
            onChefClick={handleChefMarkerClick}
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
                onChefClick={handleBestChefClick}
              />
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
        </BottomSheet>
      </div>
    </div>
  );
}

export default App;
