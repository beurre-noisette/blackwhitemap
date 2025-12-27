-- 백요리사 데이터 INSERT 쿼리
-- Chef 테이블과 chef_image 테이블에 데이터 삽입
-- 참고: (정보 없음)은 NULL로 처리, 휴무일의 '없음'은 문자열 '없음'으로 저장

-- 1. 김건 -  (고료리 켄)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '김건',
    NULL,
    'WHITE',
    0,
    '고료리 켄',
    '도산공원',
    '서울특별시 강남구 언주로152길 15-3, 2층',
    37.523008113376,
    127.034805757216,
    '일, 월',
    'COURSE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/goryori_ken?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_goryori_ken',
    'https://www.instagram.com/ichieseoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김건' AND nickname IS NULL AND restaurant_name = '고료리 켄' AND restaurant_small_address = '도산공원'),
    'unknown.jpg',
    0
);

-- 2. 김건 -  (회현 카페)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '김건',
    NULL,
    'WHITE',
    0,
    '회현 카페',
    '회현',
    '서울특별시 중구 퇴계로2길 9-8',
    37.557010458319,
    126.977683897926,
    '토, 일',
    'WESTERN_FOOD',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/Hoehyeon_Cafe?type=DINING',
    'https://www.instagram.com/ichieseoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김건' AND nickname IS NULL AND restaurant_name = '회현 카페' AND restaurant_small_address = '회현'),
    'unknown.jpg',
    0
);

-- 3. 김성운 -  (테이블포포)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '김성운',
    NULL,
    'WHITE',
    0,
    '테이블포포',
    '한남',
    '서울특별시 용산구 대사관로31길 25-12',
    37.5346682132297,
    127.005247297399,
    '없음',
    'COURSE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/tableforfour?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_tableforfour',
    'https://www.instagram.com/tableforfour_official/?hl=ko'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김성운' AND nickname IS NULL AND restaurant_name = '테이블포포' AND restaurant_small_address = '한남'),
    'unknown.jpg',
    0
);

-- 4. 김희은 -  (소울)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '김희은',
    NULL,
    'WHITE',
    0,
    '소울',
    '해방촌',
    '서울특별시 용산구 신흥로26길 35, 지하1층',
    37.5453628880082,
    126.984383275318,
    '월, 화',
    'CONTEMPORARY',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/souldining',
    'https://www.instagram.com/souldining_seoul/?hl=ko'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김희은' AND nickname IS NULL AND restaurant_name = '소울' AND restaurant_small_address = '해방촌'),
    'unknown.jpg',
    0
);

-- 5. 김희은 -  (에그앤플라워)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '김희은',
    NULL,
    'WHITE',
    0,
    '에그앤플라워',
    '해방촌',
    '서울특별시 용산구 신흥로26길 35, 2층',
    37.5453628880082,
    126.984383275318,
    '없음',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/eggandflour',
    'https://www.instagram.com/eggnflour_pasta/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김희은' AND nickname IS NULL AND restaurant_name = '에그앤플라워' AND restaurant_small_address = '해방촌'),
    'unknown.jpg',
    0
);

-- 6. 샘 킴 -  (뜰라또리아 샘킴)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '샘 킴',
    NULL,
    'WHITE',
    0,
    '뜰라또리아 샘킴',
    '압구정로데오',
    '서울특별시 강남구 도산대로49길 10-3, 2층',
    37.5238090344097,
    127.037677142475,
    '일',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/trattoriasamkim?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_trattoriasamkim',
    'https://www.instagram.com/trattoriasamkim/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '샘 킴' AND nickname IS NULL AND restaurant_name = '뜰라또리아 샘킴' AND restaurant_small_address = '압구정로데오'),
    'unknown.jpg',
    0
);

-- 7. 샘 킴 -  (오스테리아 샘킴)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '샘 킴',
    NULL,
    'WHITE',
    0,
    '오스테리아 샘킴',
    '합정',
    '서울특별시 마포구 양화로3길 55 어반 오아시스, 2층',
    37.5513124897941,
    126.910906828256,
    '일',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/samkim?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_samkim',
    'https://www.instagram.com/osteriasamkim/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '샘 킴' AND nickname IS NULL AND restaurant_name = '오스테리아 샘킴' AND restaurant_small_address = '합정'),
    'unknown.jpg',
    0
);

-- 8. 선재스님 -  (선재사찰음식문화연구원)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '선재스님',
    NULL,
    'WHITE',
    0,
    '선재사찰음식문화연구원',
    '용인',
    '경기도 양평군 옥천면 검듸길 83-28',
    37.5505692418245,
    127.471429090266,
    NULL,
    'KOREAN',
    NULL,
    'https://place.map.kakao.com/793999',
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '선재스님' AND nickname IS NULL AND restaurant_name = '선재사찰음식문화연구원' AND restaurant_small_address = '용인'),
    'unknown.jpg',
    0
);

-- 9. 손종원 -  (이타닉 가든)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '손종원',
    NULL,
    'WHITE',
    0,
    '이타닉 가든',
    '역삼',
    '서울특별시 강남구 테헤란로 231 조선팰리스, 36층',
    37.5028813541774,
    127.041356540268,
    '월, 화',
    'COURSE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/eatanicgarden?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_eatanicgarden',
    'https://www.instagram.com/eatanicgarden/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '손종원' AND nickname IS NULL AND restaurant_name = '이타닉 가든' AND restaurant_small_address = '역삼'),
    'unknown.jpg',
    0
);

-- 10. 손종원 -  (라망 시크레)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '손종원',
    NULL,
    'WHITE',
    0,
    '라망 시크레',
    '회현',
    '서울특별시 중구 퇴계로 67 레스케이프 호텔, 26',
    37.5597323437561,
    126.979523390315,
    '일, 월',
    'CONTEMPORARY',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/lamantsecret?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_lamantsecret',
    'https://www.instagram.com/lamant_secret/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '손종원' AND nickname IS NULL AND restaurant_name = '라망 시크레' AND restaurant_small_address = '회현'),
    'unknown.jpg',
    0
);

-- 11. 이금희 -  (봉래헌)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '이금희',
    NULL,
    'WHITE',
    0,
    '봉래헌',
    '김포공항',
    '서울특별시 강서구 방화대로 94 메이필드호텔',
    37.5478957237892,
    126.819507121486,
    '월',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/bongraeheon?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_bongraeheon',
    'https://www.instagram.com/limehee11/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이금희' AND nickname IS NULL AND restaurant_name = '봉래헌' AND restaurant_small_address = '김포공항'),
    '백_스와니에_이준.jpg',
    0
);

-- 12. 이준 -  (SOIGNE)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '이준',
    NULL,
    'WHITE',
    0,
    'SOIGNE',
    '신사',
    '서울특별시 강남구 강남대로 652 Sinsa Square, 2층',
    37.5195659036492,
    127.019091004701,
    '월',
    'CONTEMPORARY',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/soigneseoul?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_soigne',
    'https://www.instagram.com/soigneseoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이준' AND nickname IS NULL AND restaurant_name = 'SOIGNE' AND restaurant_small_address = '신사'),
    'unknown.jpg',
    0
);

-- 13. 이준 -  (루드베키아)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '이준',
    NULL,
    'WHITE',
    0,
    '루드베키아',
    '광화문',
    '서울특별시 종로구 세종대로 178, KT 광화문 빌딩 West 1층',
    37.5721506390766,
    126.977791560982,
    '일',
    'BRUNCH',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/rudbeckia?type=DINING',
    'https://www.instagram.com/rudbeckia.seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이준' AND nickname IS NULL AND restaurant_name = '루드베키아' AND restaurant_small_address = '광화문'),
    'unknown.jpg',
    0
);

-- 14. 이준 -  (도우룸)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '이준',
    NULL,
    'WHITE',
    0,
    '도우룸',
    '서래마을',
    '서울특별시 서초구 동광로 99, 2층',
    37.493836355442,
    126.993345335831,
    '일, 월',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/doroom_seoraevillage?type=DINING',
    'https://www.instagram.com/doughroom/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이준' AND nickname IS NULL AND restaurant_name = '도우룸' AND restaurant_small_address = '서래마을'),
    'unknown.jpg',
    0
);

-- 15. 임성근 -  (임성근국가공인진갈비 오산점)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '임성근',
    NULL,
    'WHITE',
    0,
    '임성근국가공인진갈비 오산점',
    '오산',
    '경기도 오산시 원동로 56, 1층',
    37.1424285651211,
    127.074778903963,
    '월',
    'MEAT',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/jingalbi_osan?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_jingalbi_osan',
    'https://www.instagram.com/maroobeol.official/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '임성근' AND nickname IS NULL AND restaurant_name = '임성근국가공인진갈비 오산점' AND restaurant_small_address = '오산'),
    'unknown.jpg',
    0
);

-- 16. 정호영 -  (카덴)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '정호영',
    NULL,
    'WHITE',
    0,
    '카덴',
    '연희',
    '서울특별시 마포구 양화로7안길 2-1',
    37.5516855965118,
    126.915068576637,
    '일, 월',
    'JAPANESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/Y2F0Y2hfazRXRW5namVBajhrenNGRlMwSFgxQT09?type=DINING',
    'https://www.instagram.com/jung_hoyoung_caden_/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '정호영' AND nickname IS NULL AND restaurant_name = '카덴' AND restaurant_small_address = '연희'),
    'unknown.jpg',
    0
);

-- 17. 제니 월든 -  (Namu)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '제니 월든',
    NULL,
    'WHITE',
    0,
    'Namu',
    NULL,
    '스웨덴 말뫼 Landbygatan 5',
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    'https://www.instagram.com/namurestaurant/',
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '제니 월든' AND nickname IS NULL AND restaurant_name = 'Namu'),
    'unknown.jpg',
    0
);

-- 18. 최유강 -  (코자차)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '최유강',
    NULL,
    'WHITE',
    0,
    '코자차',
    '청담',
    '서울특별시 강남구 학동로97길 17 1층',
    37.5208683828468,
    127.055215316615,
    '일',
    'FUSION',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/kojacha?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_kojacha',
    'https://www.instagram.com/kojacha.official/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '최유강' AND nickname IS NULL AND restaurant_name = '코자차' AND restaurant_small_address = '청담'),
    'unknown.jpg',
    0
);

-- 19. 후덕죽 -  (앰배서더 서울 풀만 호빈)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '후덕죽',
    NULL,
    'WHITE',
    0,
    '앰배서더 서울 풀만 호빈',
    '장충동',
    '서울특별시 중구 동호로 287, 2층',
    37.5605989670195,
    127.002135954676,
    '없음',
    'CHINESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/theambassador_haobin?type=DINING&currentSuggestionType=SHOP_NAME',
    'https://www.instagram.com/theambassador_haobin/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '후덕죽' AND nickname IS NULL AND restaurant_name = '앰배서더 서울 풀만 호빈' AND restaurant_small_address = '장충동'),
    'unknown.jpg',
    0
);

-- 20. 레이먼 킴 -  ((정보 없음))
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '레이먼 킴',
    NULL,
    'WHITE',
    0,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '레이먼 킴' AND nickname IS NULL),
    'unknown.jpg',
    0
);

-- 21. 박효남 -  (콩두 명동)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '박효남',
    NULL,
    'WHITE',
    0,
    '콩두 명동',
    '명동',
    '서울 중구 명동7길 13 호텔28 6층',
    37.5643525309057,
    126.983969236251,
    '없음',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/congdu_myeongdong?type=DINING',
    'https://www.instagram.com/congdu.myeongdong/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '박효남' AND nickname IS NULL AND restaurant_name = '콩두 명동' AND restaurant_small_address = '명동'),
    'unknown.jpg',
    0
);

-- 22. 송훈 -  (크라운돼지)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '송훈',
    NULL,
    'WHITE',
    0,
    '크라운돼지',
    '신사',
    '서울특별시 강남구 강남대로156길 17-1',
    37.5185205990978,
    127.02046998085,
    '없음',
    'MEAT',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/crownpig?type=DINING',
    'https://www.instagram.com/song_hoon_park/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '송훈' AND nickname IS NULL AND restaurant_name = '크라운돼지' AND restaurant_small_address = '신사'),
    'unknown.jpg',
    0
);

-- 23. 심성철 -  (코치(KOCHI))
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '심성철',
    NULL,
    'WHITE',
    0,
    '코치(KOCHI)',
    NULL,
    ' 뉴욕시 652 10th Ave, New York, NY 10036 미국',
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '심성철' AND nickname IS NULL AND restaurant_name = '코치(KOCHI)'),
    'unknown.jpg',
    0
);

-- 24. 김도윤 -  (윤서울)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '김도윤',
    NULL,
    'WHITE',
    0,
    '윤서울',
    '압구정로데오',
    '서울특별시 강남구 선릉로 805 W빌딩 1층',
    37.5241263043193,
    127.038982210697,
    '일, 월',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/yunseoul?type=DINING',
    'https://www.instagram.com/yunseoul.restaurant/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김도윤' AND nickname IS NULL AND restaurant_name = '윤서울' AND restaurant_small_address = '압구정로데오'),
    'unknown.jpg',
    0
);

-- 25. 김도윤 -  (면서울)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '김도윤',
    NULL,
    'WHITE',
    0,
    '면서울',
    '압구정로데오',
    '서울특별시 강남구 선릉로 805 W빌딩',
    37.5241263043193,
    127.038982210697,
    '일',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/myeonseoul?type=WAITING',
    'https://www.instagram.com/myeonseoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김도윤' AND nickname IS NULL AND restaurant_name = '면서울' AND restaurant_small_address = '압구정로데오'),
    'unknown.jpg',
    0
);

-- 26. 최강록 -  ((정보 없음))
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '최강록',
    NULL,
    'WHITE',
    0,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '최강록' AND nickname IS NULL),
    'unknown.jpg',
    0
);

-- 27. 천상현 -  (천상현의 천상)
-- ==================================================
INSERT INTO chef (
    created_at,
    updated_at,
    version,
    name,
    nickname,
    chef_type,
    view_count,
    restaurant_name,
    restaurant_small_address,
    restaurant_address,
    latitude,
    longitude,
    restaurant_closed_days,
    restaurant_category,
    naver_reservation_url,
    catch_table_url,
    instagram_url
) VALUES (
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0,
    '천상현',
    NULL,
    'WHITE',
    0,
    '천상현의 천상',
    '양재',
    '서울특별시 서초구 매헌로 16 하이브랜드 라시따델라모다 쇼핑몰 6층',
    37.462467348157,
    127.036947909974,
    '없음',
    'CHINESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/cheonsang?type=DINING',
    'https://www.instagram.com/chunsang.21/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '천상현' AND nickname IS NULL AND restaurant_name = '천상현의 천상' AND restaurant_small_address = '양재'),
    'unknown.jpg',
    0
);

commit;
