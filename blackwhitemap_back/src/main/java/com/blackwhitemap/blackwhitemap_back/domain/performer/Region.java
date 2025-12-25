package com.blackwhitemap.blackwhitemap_back.domain.performer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 대한민국 시/도 정보 및 중심 좌표
 * - Chef.address에서 시/도를 추출하여 Region으로 매핑
 * - 각 Region의 중심 좌표(위도/경도)를 관리
 * - 중심 좌표는 시/도청의 위치를 기준으로 함
 */
@Getter
@RequiredArgsConstructor
public enum Region {
    // 특별시
    SEOUL("서울특별시", 37.5665, 126.978),
    SEJONG("세종특별자치시", 36.4800, 127.2890),

    // 광역시
    BUSAN("부산광역시", 35.1796, 129.0756),
    DAEGU("대구광역시", 35.8714, 128.6014),
    INCHEON("인천광역시", 37.4563, 126.7052),
    GWANGJU("광주광역시", 35.1595, 126.8526),
    DAEJEON("대전광역시", 36.3504, 127.3845),
    ULSAN("울산광역시", 35.5384, 129.3114),

    // 도
    GYEONGGI("경기도", 37.4138, 127.5183),
    GANGWON("강원특별자치도", 37.8228, 128.1555),
    CHUNGBUK("충청북도", 36.6357, 127.4917),
    CHUNGNAM("충청남도", 36.5184, 126.8000),
    JEONBUK("전북특별자치도", 35.7175, 127.1530),
    JEONNAM("전라남도", 34.8162, 126.4630),
    GYEONGBUK("경상북도", 36.4919, 128.8889),
    GYEONGNAM("경상남도", 35.4606, 128.2132),
    JEJU("제주특별자치도", 33.4890, 126.4983);

    private final String name;
    private final double latitude;
    private final double longitude;

    /**
     * 주소 문자열에서 시/도를 추출하여 Region으로 변환
     * <p>
     * 예시:
     * - "서울특별시 용산구 이태원로..." → SEOUL
     * - "경기도 성남시 분당구..." → GYEONGGI
     * - "부산광역시 해운대구..." → BUSAN
     *
     * @param address 전체 주소 문자열
     * @return 매칭되는 Region, 매칭 실패 시 null
     */
    public static Region fromAddress(String address) {
        if (address == null || address.isBlank()) {
            return null;
        }

        // 주소의 첫 번째 토큰(공백 기준)을 시/도로 간주
        String firstToken = address.trim().split(" ")[0];

        // 모든 Region을 순회하며 koreanName과 일치하는지 확인
        for (Region region : Region.values()) {
            if (region.name.equals(firstToken)) {
                return region;
            }
        }

        // 매칭되는 Region이 없으면 null 반환
        return null;
    }
}
