-- 테스트용 Chef 데이터 (5명)
-- Restaurant은 @Embeddable이므로 chef 테이블에 함께 저장됨
-- restaurant_category는 @Enumerated(EnumType.STRING)으로 문자열 저장

INSERT INTO chef (name, nickname, chef_type, restaurant_name, restaurant_address, restaurant_category, naver_reservation_url, catch_table_url, instagram_url, view_count, version, created_at, updated_at)
VALUES
    ('유용욱', '바베큐 연구소장', 'BLACK', '유용옥 바베큐 연구소', '서울특별시 용산구 한강대로84길 5-7', 'BBQ', 'https://naver.com/1', 'https://catchtable.com/1', 'https://instagram.com/1', 0, 0, NOW(), NOW()),
    ('손종원', '요리 천재', 'WHITE', '라망 시크레', '서울특별시 중구 퇴계로 67', 'WESTERN', 'https://naver.com/2', 'https://catchtable.com/2', 'https://instagram.com/2', 0, 0, NOW(), NOW()),
    ('에드워드 리', '컬리넌', 'BLACK', '에드워드의 가게', '서울시 종로구', 'WESTERN', 'https://naver.com/3', 'https://catchtable.com/3', 'https://instagram.com/3', 0, 0, NOW(), NOW()),
    ('김도윤', '마시마로', 'WHITE', '윤서울', '서울시 마포구 연남동', 'ITALIAN', 'https://naver.com/4', 'https://catchtable.com/4', 'https://instagram.com/4', 0, 0, NOW(), NOW()),
    ('정지선', NULL, 'BLACK', '티엔미미', '서울시 강남구 신사동', 'ITALIAN', 'https://naver.com/5', 'https://catchtable.com/5', 'https://instagram.com/5', 0, 0, NOW(), NOW());

-- 테스트용 ChefImages 데이터
-- @ElementCollection이므로 별도 chef_image 테이블에 저장
INSERT INTO chef_image (chef_id, image_url, display_order)
VALUES
    (1, 'https://example.com/image1.jpg', 0),
    (2, 'https://example.com/image2.jpg', 0),
    (3, 'https://example.com/image3.jpg', 0),
    (4, 'https://example.com/image4.jpg', 0),
    (5, 'https://example.com/image5.jpg', 0);

-- 테스트용 ChefRanking 데이터 (이번 주 화요일 기준, TOP 5)
-- periodStart는 동적으로 계산: 이번 주 화요일
-- date_trunc('week', CURRENT_DATE)는 이번 주 월요일을 반환
-- + INTERVAL '1 day'로 화요일 계산
INSERT INTO chef_ranking (chef_id, ranking_type, period_start, rank_position, score, created_at, updated_at)
VALUES
    (1, 'WEEKLY', (date_trunc('week', CURRENT_DATE) + INTERVAL '1 day')::date, 1, NULL, NOW(), NOW()),
    (2, 'WEEKLY', (date_trunc('week', CURRENT_DATE) + INTERVAL '1 day')::date, 2, NULL, NOW(), NOW()),
    (3, 'WEEKLY', (date_trunc('week', CURRENT_DATE) + INTERVAL '1 day')::date, 3, NULL, NOW(), NOW()),
    (4, 'WEEKLY', (date_trunc('week', CURRENT_DATE) + INTERVAL '1 day')::date, 4, NULL, NOW(), NOW()),
    (5, 'WEEKLY', (date_trunc('week', CURRENT_DATE) + INTERVAL '1 day')::date, 5, NULL, NOW(), NOW());
