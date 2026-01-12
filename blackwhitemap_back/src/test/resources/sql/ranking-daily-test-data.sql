-- 일일 랭킹 테스트용 Chef 데이터
-- 손종원 셰프가 3개 매장을 운영하는 시나리오
-- nickname 기준으로 중복 제거가 되어야 함

INSERT INTO chef (name, nickname, chef_type, restaurant_name, restaurant_address, restaurant_small_address, restaurant_category, naver_reservation_url, catch_table_url, instagram_url, view_count, version, created_at, updated_at)
VALUES
    ('유용욱', '바베큐 연구소장', 'BLACK', '유용옥 바베큐 연구소', '서울특별시 용산구 한강대로84길 5-7', '용산구', 'BBQ', 'https://naver.com/1', 'https://catchtable.com/1', 'https://instagram.com/1', 0, 0, NOW(), NOW()),
    ('손종원', '요리 천재', 'WHITE', '라망 시크레 본점', '서울특별시 중구 퇴계로 67', '중구', 'WESTERN', 'https://naver.com/2', 'https://catchtable.com/2', 'https://instagram.com/2', 0, 0, NOW(), NOW()),
    ('손종원', '요리 천재', 'WHITE', '라망 시크레 2호점', '서울특별시 강남구 논현동', '강남구', 'WESTERN', 'https://naver.com/3', 'https://catchtable.com/3', 'https://instagram.com/3', 0, 0, NOW(), NOW()),
    ('손종원', '요리 천재', 'WHITE', '라망 시크레 3호점', '서울특별시 마포구 연남동', '마포구', 'WESTERN', 'https://naver.com/4', 'https://catchtable.com/4', 'https://instagram.com/4', 0, 0, NOW(), NOW()),
    ('에드워드 리', '컬리넌', 'BLACK', '에드워드의 가게', '서울시 종로구', '종로구', 'WESTERN', 'https://naver.com/5', 'https://catchtable.com/5', 'https://instagram.com/5', 0, 0, NOW(), NOW()),
    ('김도윤', '마시마로', 'WHITE', '윤서울', '서울시 마포구 연남동', '마포구', 'ITALIAN', 'https://naver.com/6', 'https://catchtable.com/6', 'https://instagram.com/6', 0, 0, NOW(), NOW()),
    ('정지선', NULL, 'BLACK', '티엔미미', '서울시 강남구 신사동', '강남구', 'ITALIAN', 'https://naver.com/7', 'https://catchtable.com/7', 'https://instagram.com/7', 0, 0, NOW(), NOW());

-- 테스트용 ChefRanking 데이터 (DAILY, 오늘 기준)
-- 예상 순위:
--   rank 1: 유용욱 (id=1)
--   rank 2: 손종원 본점 (id=2) <- 채택
--   rank 3: 손종원 2호점 (id=3) <- 제외 (nickname 중복)
--   rank 4: 손종원 3호점 (id=4) <- 제외 (nickname 중복)
--   rank 5: 에드워드 리 (id=5)
--   rank 6: 김도윤 (id=6)
--   rank 7: 정지선 (id=7) <- name 기준 (nickname이 null)
--
-- 최종 결과: 유용욱(1위), 손종원(2위), 에드워드 리(5위), 김도윤(6위), 정지선(7위)
INSERT INTO chef_ranking (chef_id, ranking_type, period_start, rank_position, score, created_at, updated_at)
VALUES
    (1, 'DAILY', CURRENT_DATE, 1, 1000, NOW(), NOW()),
    (2, 'DAILY', CURRENT_DATE, 2, 950, NOW(), NOW()),
    (3, 'DAILY', CURRENT_DATE, 3, 900, NOW(), NOW()),
    (4, 'DAILY', CURRENT_DATE, 4, 850, NOW(), NOW()),
    (5, 'DAILY', CURRENT_DATE, 5, 800, NOW(), NOW()),
    (6, 'DAILY', CURRENT_DATE, 6, 750, NOW(), NOW()),
    (7, 'DAILY', CURRENT_DATE, 7, 700, NOW(), NOW());
