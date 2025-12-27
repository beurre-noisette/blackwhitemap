-- 흑요리사 데이터 INSERT 쿼리
-- Chef 테이블과 chef_image 테이블에 데이터 삽입
-- 참고: (정보 없음)은 NULL로 처리, 휴무일의 '없음'은 문자열 '없음'으로 저장

-- 1. 신동민 - 요리과학자 (멘야미코)
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
    '신동민',
    '요리과학자',
    'BLACK',
    0,
    '멘야미코',
    '강남',
    '서울특별시 강남구 삼성로 642 삼성빌딩 103호',
    37.517401366555,
    127.051322820558,
    '일',
    'JAPANESE',
    NULL,
    NULL,
    'https://www.instagram.com/menyamiko2020/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '신동민' AND nickname = '요리과학자' AND restaurant_name = '멘야미코' AND restaurant_small_address = '강남'),
    'black_menyamiko_cookscientist.jpg',
    0
);

-- 2. 신동민 - 요리과학자 (당옥)
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
    '신동민',
    '요리과학자',
    'BLACK',
    0,
    '당옥',
    '가로수길',
    '서울특별시 강남구 강남대로162길 22 1층',
    37.5201091623325,
    127.02044360538,
    '없음',
    'CAFE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/dangok?type=DINING',
    'https://www.instagram.com/dangok_sinsa/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '신동민' AND nickname = '요리과학자' AND restaurant_name = '당옥' AND restaurant_small_address = '가로수길'),
    'black_dankok_cookscientist.jpg',
    0
);

-- 3. 유용욱 - 바베큐연구소장 (유용욱바베큐연구소)
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
    '유용욱',
    '바베큐연구소장',
    'BLACK',
    0,
    '유용욱바베큐연구소',
    '남영',
    '서울특별시 용산구 한강대로84길 5-7 남영아케이드',
    37.5439572071211,
    126.972781798522,
    '없음',
    'BBQ',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/imok?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_imok',
    'https://www.instagram.com/yooyongwook/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '유용욱' AND nickname = '바베큐연구소장' AND restaurant_name = '유용욱바베큐연구소' AND restaurant_small_address = '남영'),
    'black_yooyongwookbbqlab_bbqresearchdirector.jpg',
    0
);

-- 4. 유용욱 - 바베큐연구소장 (이목스모크다이닝)
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
    '유용욱',
    '바베큐연구소장',
    'BLACK',
    0,
    '이목스모크다이닝',
    '신사',
    '서울특별시 강남구 압구정로2길 6 지하1층',
    37.5210863385776,
    127.019138224359,
    '없음',
    'BBQ',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/imok?%22%EB%9D%BC%EB%8A%94',
    'https://www.instagram.com/imok.smokedining/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '유용욱' AND nickname = '바베큐연구소장' AND restaurant_name = '이목스모크다이닝' AND restaurant_small_address = '신사'),
    'black_emoksmokedining_bbqresearchdirector.jpg',
    0
);

-- 5. 신계숙 - 중식 폭주족 (계향각)
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
    '신계숙',
    '중식 폭주족',
    'BLACK',
    0,
    '계향각',
    '혜화',
    '서울특별시 종로구 동숙길 86 1층',
    37.5821048,
    127.0043083,
    '월',
    'CHINESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/heestoryfood?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_heestoryfood',
    'https://www.instagram.com/gaehyanggak/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '신계숙' AND nickname = '중식 폭주족' AND restaurant_name = '계향각' AND restaurant_small_address = '혜화'),
    'black_gyeheyanggak_chineseracer.jpg',
    0
);

-- 6. 이문정 - 중식 마녀 ((정보 없음))
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
    '이문정',
    '중식 마녀',
    'BLACK',
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
    (SELECT id FROM chef WHERE name = '이문정' AND nickname = '중식 마녀'),
    'black_unknown_chinesewitch.jpg',
    0
);

-- 7. 신현도 - 칼마카세 (히카리모노)
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
    '신현도',
    '칼마카세',
    'BLACK',
    0,
    '히카리모노',
    '광화문',
    '서울특별시 종로구 종로 33 그랑서울타워1 스타필드에비뉴 2층',
    37.5709617749066,
    126.981437983842,
    '없음',
    'JAPANESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/hikarimono?type=DINING',
    'https://www.instagram.com/hikarimono_seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '신현도' AND nickname = '칼마카세' AND restaurant_name = '히카리모노' AND restaurant_small_address = '광화문'),
    'black_hikarimono_knifemakase.jpg',
    0
);

-- 8. 신현도 - 칼마카세 (모노로그)
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
    '신현도',
    '칼마카세',
    'BLACK',
    0,
    '모노로그',
    '청담',
    '서울특별시 강남구 도산대로 442 피엔폴루스 1층',
    37.5233111622682,
    127.044119009562,
    '없음',
    'OMAKASE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/monologue?type=DINING',
    'https://www.instagram.com/monologue_seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '신현도' AND nickname = '칼마카세' AND restaurant_name = '모노로그' AND restaurant_small_address = '청담'),
    'black_monologue_knifemakase.jpg',
    0
);

-- 9. 김상훈 - 4평 외톨이 (독립식당)
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
    '김상훈',
    '4평 외톨이',
    'BLACK',
    0,
    '독립식당',
    '종로',
    '서울특별시 종로구 자하문로33길 10 1층',
    37.5862132697459,
    126.96936721839,
    '없음',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/dokrip_sikdang?type=DINING',
    'https://www.instagram.com/dokripsikdang/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김상훈' AND nickname = '4평 외톨이' AND restaurant_name = '독립식당' AND restaurant_small_address = '종로'),
    'black_dokripsikdang_4pyeongloner.jpg',
    0
);

-- 10. 김상훈 - 4평 외톨이 (독도16도)
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
    '김상훈',
    '4평 외톨이',
    'BLACK',
    0,
    '독도16도',
    '서촌',
    '1. 서울특별시 종로구 필운대로5가길 2, 3층',
    NULL,
    NULL,
    '일, 월',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/dokdo.16.celsius',
    'https://www.instagram.com/dokdo.16.celsius/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김상훈' AND nickname = '4평 외톨이' AND restaurant_name = '독도16도' AND restaurant_small_address = '서촌'),
    'black_dokdo16do_4pyeongloner.jpg',
    0
);

-- 11. 김만희 - 휴게소 총괄셰프 (용인(인천)휴게소)
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
    '김만희',
    '휴게소 총괄셰프',
    'BLACK',
    0,
    '용인(인천)휴게소',
    NULL,
    '경기 용인시 처인구 주북로 66-14',
    37.2483623913529,
    127.238305881038,
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
    (SELECT id FROM chef WHERE name = '김만희' AND nickname = '휴게소 총괄셰프' AND restaurant_name = '용인(인천)휴게소'),
    'black_yongin(incheon)restarea_restareaheadchef.jpg',
    0
);

-- 12. 안진호 - 쓰리스타 킬러 ((정보 없음))
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
    '안진호',
    '쓰리스타 킬러',
    'BLACK',
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
    'https://www.instagram.com/comfortrueshine/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '안진호' AND nickname = '쓰리스타 킬러'),
    'black_unknown_3starkiller.jpg',
    0
);

-- 13. 방효숙 - 전설의 학식뷰페 (구들장흑도야지)
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
    '방효숙',
    '전설의 학식뷰페',
    'BLACK',
    0,
    '구들장흑도야지',
    '용인',
    '경기도 용인시 수지구 죽전로 176, 죽전프라자 2층',
    37.3262603372193,
    127.125042066771,
    '없음',
    'BBQ',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/gudeuljangheugdoyaji?type=DINING',
    'https://www.instagram.com/blackpig3532/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '방효숙' AND nickname = '전설의 학식뷰페' AND restaurant_name = '구들장흑도야지' AND restaurant_small_address = '용인'),
    'black_gudeuljanghyukdoyaji_legendarycafeteriabuffet.jpg',
    0
);

-- 14. 이재훈 - 서촌 황태자 (까델루포)
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
    '이재훈',
    '서촌 황태자',
    'BLACK',
    0,
    '까델루포',
    '서촌',
    '서울특별시 종로구 지하문로16길 5-5',
    37.5818097,
    126.9714224,
    '없음',
    'ITALIAN',
    'https://m.booking.naver.com/booking/6/bizes/142189/items/2730327',
    'https://app.catchtable.co.kr/ct/shop/CadelLupoJongno?utm_source=namuwiki&utm_medium=link&utm_campaign=culinaryclasswars2_CadelLupoJongno',
    'https://www.instagram.com/romantic_owner_chef/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이재훈' AND nickname = '서촌 황태자' AND restaurant_name = '까델루포' AND restaurant_small_address = '서촌'),
    'black_cadellupo_seochoncrownprince.jpg',
    0
);

-- 15. 마티유 몰스 - 안녕 봉주르 (셰누프라이빗키친)
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
    '마티유 몰스',
    '안녕 봉주르',
    'BLACK',
    0,
    '셰누프라이빗키친',
    '용산',
    '서울특별시 용산구 녹사평대로 210-6, 마이하우스 3층',
    37.5363433391664,
    126.987404190597,
    '일, 월',
    'FRENCH',
    NULL,
    NULL,
    'https://www.instagram.com/cheznouseoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '마티유 몰스' AND nickname = '안녕 봉주르' AND restaurant_name = '셰누프라이빗키친' AND restaurant_small_address = '용산'),
    'black_chefnuprivatekitchen_bonjourhello.jpg',
    0
);

-- 16. 김훈 - 유행왕 (쌤쌤쌤 용산점)
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
    '김훈',
    '유행왕',
    'BLACK',
    0,
    '쌤쌤쌤 용산점',
    '삼각지',
    '서울특별시 용산구 한강대로50길 25, 1층',
    37.5315767768856,
    126.972158951911,
    '없음',
    'AMERICAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/samsamsam_kr?type=WAITING',
    'https://www.instagram.com/samsamsam.kr/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김훈' AND nickname = '유행왕' AND restaurant_name = '쌤쌤쌤 용산점' AND restaurant_small_address = '삼각지'),
    'black_samsamsamyongsan_trendking.jpg',
    0
);

-- 17. 김훈 - 유행왕 (남도돼지촌)
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
    '김훈',
    '유행왕',
    'BLACK',
    0,
    '남도돼지촌',
    '삼각지',
    '서울특별시 용산구 한강대로40가길 40 1,2층',
    37.53153799,
    126.972398,
    '없음',
    'BBQ',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/namdopig?type=WAITINGG',
    'https://www.instagram.com/namdopig.kr/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김훈' AND nickname = '유행왕' AND restaurant_name = '남도돼지촌' AND restaurant_small_address = '삼각지'),
    'black_namdodwejichon_trendking.jpg',
    0
);

-- 18. 정시우 - 닭발로 16억 (삼미분식)
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
    '정시우',
    '닭발로 16억',
    'BLACK',
    0,
    '삼미분식',
    '이천',
    '경기 이천시 중리천로21번길 9 삼미분식',
    37.2809109321334,
    127.440425113645,
    '매달 3번째 일요일',
    'BUNSIK',
    NULL,
    NULL,
    'https://www.instagram.com/3mebunsik/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '정시우' AND nickname = '닭발로 16억' AND restaurant_name = '삼미분식' AND restaurant_small_address = '이천'),
    'black_sammibunsik_chickenfeet1.6billion.jpg',
    0
);

-- 19. 이하성 - 요리괴물 (오야트(Oyatte)
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
    '이하성',
    '요리괴물',
    'BLACK',
    0,
    '오야트(Oyatte)',
    '뉴욕',
    '125 East 39th Street, New York, NY',
    NULL,
    NULL,
    NULL,
    'DINING',
    NULL,
    NULL,
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이하성' AND nickname = '요리괴물' AND restaurant_name = '오야트(Oyatte)'),
    'black_oyatte_cookingmonster.jpg',
    0
);

-- 20. 김재호 - 디어 그랜마 ((정보 없음))
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
    '김재호',
    '디어 그랜마',
    'BLACK',
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
    (SELECT id FROM chef WHERE name = '김재호' AND nickname = '디어 그랜마'),
    'black_unknown_deargrandma.jpg',
    0
);

-- 21. 담소룡 - 지랄 쓰부 (동보성)
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
    '담소룡',
    '지랄 쓰부',
    'BLACK',
    0,
    '동보성',
    '선릉',
    '서울특별시 강남구 테헤란로 406 샹제리제센터 A동 2층',
    37.5043809944112,
    127.049963989803,
    '없음',
    'CHINESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/dongbosung?type=DINING',
    'https://www.instagram.com/dongbosung_official/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '담소룡' AND nickname = '지랄 쓰부' AND restaurant_name = '동보성' AND restaurant_small_address = '선릉'),
    'black_dongboseong_crazytsuru.jpg',
    0
);

-- 22. 박주성 - 무쇠팔 (소바쥬)
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
    '박주성',
    '무쇠팔',
    'BLACK',
    0,
    '소바쥬',
    '마포',
    '서울특별시 마포구 큰우물로 75, 상가 지하1층 22호',
    37.5412654954538,
    126.946294819719,
    '화',
    'JAPANESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/sauvage?type=DINING',
    'https://www.instagram.com/sobajuu/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '박주성' AND nickname = '무쇠팔' AND restaurant_name = '소바쥬' AND restaurant_small_address = '마포'),
    'black_sauvage_ironarm.jpg',
    0
);

-- 23. 이정수 - 김치다이닝 (온6.5)
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
    '이정수',
    '김치다이닝',
    'BLACK',
    0,
    '온6.5',
    '안국',
    '서울특별시 종로구 북촌로1길 28 1층',
    37.5773675392768,
    126.984277651202,
    '없음',
    'DINING',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/on65?type=DINING',
    'https://www.instagram.com/on6.5_seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이정수' AND nickname = '김치다이닝' AND restaurant_name = '온6.5' AND restaurant_small_address = '안국'),
    'black_on6.5_kimchidining.jpg',
    0
);

-- 24. 이순실 - 평양 큰형님 (이순실평양명가)
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
    '이순실',
    '평양 큰형님',
    'BLACK',
    0,
    '이순실평양명가',
    '화성시',
    '경기도 화성시 동탄대로시범길 148-20, B110~B112호',
    37.2003224692968,
    127.114516457675,
    '월',
    'KOREAN',
    NULL,
    NULL,
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이순실' AND nickname = '평양 큰형님' AND restaurant_name = '이순실평양명가' AND restaurant_small_address = '화성시'),
    'black_isoonsilpyeongyangmyeongga_pyeongyangbigbrother.jpg',
    0
);

-- 25. 정희승 - 연마대왕 ((정보 없음))
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
    '정희승',
    '연마대왕',
    'BLACK',
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
    (SELECT id FROM chef WHERE name = '정희승' AND nickname = '연마대왕'),
    'black_unknown_grindingemperor.jpg',
    0
);

-- 26. 윤나라 - 술 빚는 윤주모 (해방촌 윤주당)
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
    '윤나라',
    '술 빚는 윤주모',
    'BLACK',
    0,
    '해방촌 윤주당',
    '해방촌',
    '서울특별시 용산구 신흥로 81-1, 1층',
    37.5453534474955,
    126.98597835382,
    '확인불가',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/yunjudang_?type=DINING',
    'https://www.instagram.com/yunjudang_brewery/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '윤나라' AND nickname = '술 빚는 윤주모' AND restaurant_name = '해방촌 윤주당' AND restaurant_small_address = '해방촌'),
    'black_haebangchunyounjudang_brewingmaster.jpg',
    0
);

-- 27. 채명희 - 밥도둑 포차 (은진포차)
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
    '채명희',
    '밥도둑 포차',
    'BLACK',
    0,
    '은진포차',
    '영등포',
    '서울특별시 영등포구 도림로133길 20',
    37.512762521754,
    126.893682197611,
    '일',
    'SEAFOOD',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/eunjinpoca?type=WAITING',
    'https://www.instagram.com/explore/locations/1468608923226601/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '채명희' AND nickname = '밥도둑 포차' AND restaurant_name = '은진포차' AND restaurant_small_address = '영등포'),
    'black_eunjinpocha_ricethiefpocha.jpg',
    0
);

-- 28. 김호윤 - 키친 보스 (더이탈리안 클럽)
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
    '김호윤',
    '키친 보스',
    'BLACK',
    0,
    '더이탈리안 클럽',
    '판교',
    '경기도 성남시 분당구 분당내곡로 131 1층 5-2호',
    37.3954648629951,
    127.112113596734,
    '없음',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/coresociety2021?type=DINING',
    'https://www.instagram.com/the_italianclub/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김호윤' AND nickname = '키친 보스' AND restaurant_name = '더이탈리안 클럽' AND restaurant_small_address = '판교'),
    'black_theitalianclub_kitchenboss.jpg',
    0
);

-- 29. 김호윤 - 키친 보스 (중식당 청 한남점)
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
    '김호윤',
    '키친 보스',
    'BLACK',
    0,
    '중식당 청 한남점',
    '한남',
    '서울특별시 용산구 한남대로20길 47-24 2층',
    37.5353665378761,
    127.010053434924,
    '월',
    'CHINESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/chung_hn?type=DINING',
    'https://www.instagram.com/chung.hannam/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김호윤' AND nickname = '키친 보스' AND restaurant_name = '중식당 청 한남점' AND restaurant_small_address = '한남'),
    'black_chunghannamsik_kitchenboss.jpg',
    0
);

-- 30. 타미 리 - 프렌치 파파 (비스트로 드 욘트빌)
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
    '타미 리',
    '프렌치 파파',
    'BLACK',
    0,
    '비스트로 드 욘트빌',
    '청담',
    '서울특별시 강남구 선릉로158길 13-7 1층',
    37.526074227507,
    127.041233246229,
    '월',
    'FRENCH',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/yountville?type=DINING',
    'https://www.instagram.com/yountvillebistro/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '타미 리' AND nickname = '프렌치 파파' AND restaurant_name = '비스트로 드 욘트빌' AND restaurant_small_address = '청담'),
    'black_bistrodeyontville_frenchpapa.jpg',
    0
);

-- 31. 남성렬 - 황금손 (어물전 청)
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
    '남성렬',
    '황금손',
    'BLACK',
    0,
    '어물전 청',
    '한남',
    '서울특별시 용산구 한남대로 104 H104 2층',
    37.5377482946181,
    127.004996440907,
    '매달 2번째 월요일',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/eomuljeonchunghannam',
    'https://www.instagram.com/eomuljeon_chung/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '남성렬' AND nickname = '황금손' AND restaurant_name = '어물전 청' AND restaurant_small_address = '한남'),
    'black_eomuljeoncheong_goldenhand.jpg',
    0
);

-- 32. 남성렬 - 황금손 (신안가옥)
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
    '남성렬',
    '황금손',
    'BLACK',
    0,
    '신안가옥',
    '논현',
    '서울특별시 강남구 논현로149길 6 1층',
    37.5188086878868,
    127.027782346303,
    '없음',
    'BBQ',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/shinan_gaok?type=DINING',
    'https://www.instagram.com/sinangaok/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '남성렬' AND nickname = '황금손' AND restaurant_name = '신안가옥' AND restaurant_small_address = '논현'),
    'black_sinangaok_goldenhand.jpg',
    0
);

-- 33. 김시현 - 아기 맹수 (솔밤)
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
    '김시현',
    '아기 맹수',
    'BLACK',
    0,
    '솔밤',
    '논현',
    '서울 강남구 학동로 231 2층',
    37.5154927557092,
    127.0341900991,
    '일',
    'CONTEMPORARY',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/solbam_restaurant?type=DINING&currentSuggestionType=SHOP_NAME',
    'https://www.instagram.com/solbam_restaurant/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김시현' AND nickname = '아기 맹수' AND restaurant_name = '솔밤' AND restaurant_small_address = '논현'),
    'black_solbam_babybeast.jpg',
    0
);

-- 34. 김효숙 - 2등의 손맛 ((정보 없음))
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
    '김효숙',
    '2등의 손맛',
    'BLACK',
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
    (SELECT id FROM chef WHERE name = '김효숙' AND nickname = '2등의 손맛'),
    'black_unknown_secondplacetaste.jpg',
    0
);

-- 35. 박가람 - 천생연분 (드레스덴 그린)
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
    '박가람',
    '천생연분',
    'BLACK',
    0,
    '드레스덴 그린',
    '청담',
    '서울특별시 강남구 도산대로 420 청담스퀘어 B동 2층',
    37.5230539082669,
    127.042195064505,
    '없음',
    'COURSE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/dresden_?type=DINING',
    'https://www.instagram.com/dresdengreen.dining/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '박가람' AND nickname = '천생연분' AND restaurant_name = '드레스덴 그린' AND restaurant_small_address = '청담'),
    'black_dresdengreen_soulmate.jpg',
    0
);

-- 36. 윤태호 - 튀김의 기술 (덴푸라 키이로)
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
    '윤태호',
    '튀김의 기술',
    'BLACK',
    0,
    '덴푸라 키이로',
    '가로수길',
    '서울특별시 강남구 강남대로 652, 206호',
    37.5195659036492,
    127.019091004701,
    NULL,
    'JAPANESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/kiro?type=DINING',
    'https://www.instagram.com/tempura_kiiro/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '윤태호' AND nickname = '튀김의 기술' AND restaurant_name = '덴푸라 키이로' AND restaurant_small_address = '가로수길'),
    'black_tempurakiiro_fryingskill.jpg',
    0
);

-- 37. 옥동식 - 뉴욕 기사식당 (옥동식)
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
    '옥동식',
    '뉴욕 기사식당',
    'BLACK',
    0,
    '옥동식',
    '합정',
    '서울특별시 마포구 양화로7길 44-10 3차신도빌라 1층',
    37.5526647710073,
    126.914527535623,
    '없음',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/okdongsik?type=WAITING',
    'https://www.instagram.com/okdongsik/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '옥동식' AND nickname = '뉴욕 기사식당' AND restaurant_name = '옥동식' AND restaurant_small_address = '합정'),
    'black_okdongsik_newyorkdriverrestaurant.jpg',
    0
);

-- 38. 옥동식 - 뉴욕 기사식당 (옥동식)
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
    '옥동식',
    '뉴욕 기사식당',
    'BLACK',
    0,
    '옥동식',
    '하남',
    '경기도 하남시 감일로 17 1층',
    37.5077421420075,
    127.143162229267,
    '없음',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/okdongsiksongpa?type=WAITING',
    'https://www.instagram.com/okdongsik/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '옥동식' AND nickname = '뉴욕 기사식당' AND restaurant_name = '옥동식' AND restaurant_small_address = '하남'),
    'black_okdongsik_newyorkdriverrestaurant.jpg',
    0
);

-- 39. 윤아름 - 앵그리 코리안 (비스트로 앤트로)
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
    '윤아름',
    '앵그리 코리안',
    'BLACK',
    0,
    '비스트로 앤트로',
    '압구정',
    '서울특별시 강남구 논현로159길 12 1층',
    37.5232002293902,
    127.02716529607,
    '없음',
    'CONTEMPORARY',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/bistroanthro?type=DINING',
    'https://www.instagram.com/bistroanthro/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '윤아름' AND nickname = '앵그리 코리안' AND restaurant_name = '비스트로 앤트로' AND restaurant_small_address = '압구정'),
    'black_bistroantro_angrykorean.jpg',
    0
);

-- 40. 이선희 - 5성급 김치대가 (그랜드 워커힐 서울)
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
    '이선희',
    '5성급 김치대가',
    'BLACK',
    0,
    '그랜드 워커힐 서울',
    NULL,
    '서울 광진구 워커힐로 177 그랜드워커힐 서울',
    37.5586287308609,
    127.11170683593,
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
    (SELECT id FROM chef WHERE name = '이선희' AND nickname = '5성급 김치대가' AND restaurant_name = '그랜드 워커힐 서울'),
    'black_grandwalkerhillseoul_5starkimchimaster.jpg',
    0
);

-- 41. (정보 없음) - 최저시급 ((정보 없음))
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
    NULL,
    '최저시급',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '최저시급'),
    'black_unknown_minimumwage.jpg',
    0
);

-- 42. 장보원 - 삼대가중식 (보보식당)
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
    '장보원',
    '삼대가중식',
    'BLACK',
    0,
    '보보식당',
    '광화문',
    '서울특별시 종로구 세종대로 178 KT 광화문 빌딩 West 지하1층',
    37.5721506390766,
    126.977791560982,
    '없음',
    'CHINESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/paopaorestaurant?type=DINING',
    'https://www.instagram.com/pao_pao_chang/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '장보원' AND nickname = '삼대가중식' AND restaurant_name = '보보식당' AND restaurant_small_address = '광화문'),
    'black_bobosikdang_3rdgenerationchinese.jpg',
    0
);

-- 43. 김민성 - 줄서는 돈가스 (헤키)
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
    '김민성',
    '줄서는 돈가스',
    'BLACK',
    0,
    '헤키',
    '망원',
    '서울특별시 마포구 동교로9길 33, 1층',
    37.5552406222699,
    126.909789774459,
    '월요일',
    'TONKATSU',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/haruheki?type=WAITING&currentSuggestionType=SHOP_NAME',
    'https://www.instagram.com/haru.heki/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김민성' AND nickname = '줄서는 돈가스' AND restaurant_name = '헤키' AND restaurant_small_address = '망원'),
    'black_heki_lineduptonkatsu.jpg',
    0
);

-- 44. (정보 없음) - 5500원 영양사 ((정보 없음))
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
    NULL,
    '5500원 영양사',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '5500원 영양사'),
    'black_unknown_5500wonnutritionist.jpg',
    0
);

-- 45. 김시연 - 반찬술사 (온하루 사랑방)
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
    '김시연',
    '반찬술사',
    'BLACK',
    0,
    '온하루 사랑방',
    '서촌',
    '서울특별시 종로구 효자로 17 지하 1층',
    37.5776445198285,
    126.973724781257,
    '확인 불가',
    'CAFE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/Y2F0Y2hfMkFGSFJsR2RSazVteTRKbktJcXFvUT09?type=DINING',
    'https://www.instagram.com/onharu_official/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김시연' AND nickname = '반찬술사' AND restaurant_name = '온하루 사랑방' AND restaurant_small_address = '서촌'),
    'black_onharusarangbang_sidedishmagician.jpg',
    0
);

-- 46. 명현지 - 그때 명셰프 (아선재)
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
    '명현지',
    '그때 명셰프',
    'BLACK',
    0,
    '아선재',
    '대치동',
    '서울특별시 강남구 영동대로 333 지하581층',
    37.5041516513046,
    127.064870211708,
    '없음',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/Y2F0Y2hfckhGL1R6WmhjWkI2bVVhRGgyd0N2QT09',
    'https://www.instagram.com/asunje_official/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '명현지' AND nickname = '그때 명셰프' AND restaurant_name = '아선재' AND restaurant_small_address = '대치동'),
    'black_aseonjae_thenfamouschef.jpg',
    0
);

-- 47. 옥동식 - 뉴욕에 간 돼지곰탕 (옥동식)
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
    '옥동식',
    '뉴욕에 간 돼지곰탕',
    'BLACK',
    0,
    '옥동식',
    '합정',
    '서울특별시 마포구 양화로7길 44-10 3차신도빌라 1층',
    37.5526647710073,
    126.914527535623,
    '없음',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/okdongsik',
    'https://www.instagram.com/okdongsik/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '옥동식' AND nickname = '뉴욕에 간 돼지곰탕' AND restaurant_name = '옥동식' AND restaurant_small_address = '합정'),
    'black_okdongsik_newyorkdwejigomtang.jpg',
    0
);

-- 48. (정보 없음) - 번아웃 요리사 ((정보 없음))
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
    NULL,
    '번아웃 요리사',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '번아웃 요리사'),
    'black_unknown_burnoutchef.jpg',
    0
);

-- 49. (정보 없음) - 빠다걸 ((정보 없음))
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
    NULL,
    '빠다걸',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '빠다걸'),
    'black_unknown_paddagirl.jpg',
    0
);

-- 50. (정보 없음) - 샐딱한 천재 ((정보 없음))
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
    NULL,
    '샐딱한 천재',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '샐딱한 천재'),
    'black_unknown_sharpgenius.jpg',
    0
);

-- 51. 나원계 - 내장백서 (호루몬)
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
    '나원계',
    '내장백서',
    'BLACK',
    0,
    '호루몬',
    '도산공원',
    '서울특별시 강남구 언주로152길 11-5 상복빌딩 2층',
    37.5230452500407,
    127.034446038903,
    '없음',
    'COURSE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/horumon?type=DINING',
    'https://www.instagram.com/horumon.kr/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '나원계' AND nickname = '내장백서' AND restaurant_name = '호루몬' AND restaurant_small_address = '도산공원'),
    'black_horumon_innardwhitepaper.jpg',
    0
);

-- 52. 손영철 - 메이드 인 코리아 (보타르가 비노)
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
    '손영철',
    '메이드 인 코리아',
    'BLACK',
    0,
    '보타르가 비노',
    '청담',
    '서울특별시 강남구 도산대로66길 19 4층',
    37.5223586388498,
    127.044315622673,
    '일, 월',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/bottargavino?type=DINING',
    'https://www.instagram.com/chef_sonyoungchul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '손영철' AND nickname = '메이드 인 코리아' AND restaurant_name = '보타르가 비노' AND restaurant_small_address = '청담'),
    'black_bottargavino_madeinkorea.jpg',
    0
);

-- 53. 김진래 - 8국마스터 (불래)
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
    '김진래',
    '8국마스터',
    'BLACK',
    0,
    '불래',
    '한남',
    '서울 용산구 한남대로18길 28 2층',
    37.5346111494099,
    127.008791575449,
    '없음',
    'CONTEMPORARY',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/bruleeseoul?type=DINING',
    'https://www.instagram.com/brulee_dining/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김진래' AND nickname = '8국마스터' AND restaurant_name = '불래' AND restaurant_small_address = '한남'),
    'black_bullae_8countrymaster.jpg',
    0
);

-- 54. 김진래 - 8국마스터 (서울다이닝)
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
    '김진래',
    '8국마스터',
    'BLACK',
    0,
    '서울다이닝',
    '장충동',
    '서울특별시 중구 동호로 272 디자인하우스 2층',
    37.5592661759373,
    127.003626712191,
    '확인 불가',
    'COURSE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/seoul-dining?type=DINING',
    'https://www.instagram.com/seoul.dining/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김진래' AND nickname = '8국마스터' AND restaurant_name = '서울다이닝' AND restaurant_small_address = '장충동'),
    'black_seouldining_8countrymaster.jpg',
    0
);

-- 55. 용석원 - 파리 4.9 (Perception)
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
    '용석원',
    '파리 4.9',
    'BLACK',
    0,
    'Perception',
    NULL,
    '53 Rue Blanche, Paris',
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
    (SELECT id FROM chef WHERE name = '용석원' AND nickname = '파리 4.9' AND restaurant_name = 'Perception'),
    'black_perception_paris4.9.jpg',
    0
);

-- 56. 김두래 - 떡볶이 명인 (떡산)
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
    '김두래',
    '떡볶이 명인',
    'BLACK',
    0,
    '떡산',
    '연신내',
    '서울특별시 은평구 연서로 247-1 좌측',
    37.6195590926439,
    126.922392857469,
    NULL,
    'BUNSIK',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/tteoksan?type=DINING',
    'https://www.instagram.com/tteoksan/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김두래' AND nickname = '떡볶이 명인' AND restaurant_name = '떡산' AND restaurant_small_address = '연신내'),
    'black_tteoksan_tteokbokkimaster.jpg',
    0
);

-- 57. 양수현 - 채소 프린스 (양출서울)
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
    '양수현',
    '채소 프린스',
    'BLACK',
    0,
    '양출서울',
    '논현',
    '서울특별시 강남구 언주로135길 34 1층',
    37.5166188678736,
    127.032459279407,
    NULL,
    'WINE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/yangchulseoul?type=DINING',
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '양수현' AND nickname = '채소 프린스' AND restaurant_name = '양출서울' AND restaurant_small_address = '논현'),
    'black_yangchulseoul_vegetableprince.jpg',
    0
);

-- 58. 윤대현 - 평냉신성 (옥돌현옥)
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
    '윤대현',
    '평냉신성',
    'BLACK',
    0,
    '옥돌현옥',
    '가락',
    '서울특별시 송파구 오금로36길 26-1, 1층',
    37.5015343677051,
    127.124072910932,
    NULL,
    'NAENGMYEON',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/okdolhyeonok_?type=WAITING',
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '윤대현' AND nickname = '평냉신성' AND restaurant_name = '옥돌현옥' AND restaurant_small_address = '가락'),
    'black_okdolhyeonok_pyeongnaengrising.jpg',
    0
);

-- 59. 신동일 - 축구 국대 요리사 (대한축구협회 (경력))
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
    '신동일',
    '축구 국대 요리사',
    'BLACK',
    0,
    '대한축구협회 (경력)',
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
    (SELECT id FROM chef WHERE name = '신동일' AND nickname = '축구 국대 요리사' AND restaurant_name = '대한축구협회 (경력)'),
    'black_kfa_nationalteamchef.jpg',
    0
);

-- 60. 양수현 - 망원시장 히어로 (바삭마차)
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
    '양수현',
    '망원시장 히어로',
    'BLACK',
    0,
    '바삭마차',
    '마포',
    '서울특별시 마포구 월드컵로13길 64',
    37.5553291238709,
    126.906576003223,
    '없음',
    'TONKATSU',
    NULL,
    NULL,
    'https://www.instagram.com/basakmacha_cutlets_shop/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '양수현' AND nickname = '망원시장 히어로' AND restaurant_name = '바삭마차' AND restaurant_small_address = '마포'),
    'black_basakmacha_mangwonmarkethero.jpg',
    0
);

-- 61. 원성훈 - 라오스홀릭 (라오삐약)
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
    '원성훈',
    '라오스홀릭',
    'BLACK',
    0,
    '라오삐약',
    '용리단길',
    '서울특별시 용산구 한강대로46길 16 1층',
    37.5310802320864,
    126.971022906335,
    '없음',
    'ASIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/Y2F0Y2hfYkJnWkVxK0t1aFZTeitZSW8wMDd1dz09?type=DINING&currentSuggestionType=SHOP_NAME_LIKE',
    'https://www.instagram.com/laopiak/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '원성훈' AND nickname = '라오스홀릭' AND restaurant_name = '라오삐약' AND restaurant_small_address = '용리단길'),
    'black_laopiyak_laosholic.jpg',
    0
);

-- 62. 김태우 - 부채도사 (동경밥상 본점)
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
    '김태우',
    '부채도사',
    'BLACK',
    0,
    '동경밥상 본점',
    '부산 광안리',
    '부산광역시 수영구 남천바다로 34-6 1층',
    35.1483944403014,
    129.11385120473,
    '없음',
    'GRILLED_EEL',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/tokyotable?type=WAITING',
    'https://www.instagram.com/tokyotable__/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김태우' AND nickname = '부채도사' AND restaurant_name = '동경밥상 본점' AND restaurant_small_address = '부산 광안리'),
    'black_tokyobapsang_fanmaster.jpg',
    0
);

-- 63. 김태우 - 부채도사 (오코메)
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
    '김태우',
    '부채도사',
    'BLACK',
    0,
    '오코메',
    '부산 광안리',
    '부산광역시 수영구 남천바다로 34-8 1층',
    35.1483114491927,
    129.113773463182,
    '월',
    'JAPANESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/okome?type=WAITING',
    'https://www.instagram.com/dokripsikdang/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김태우' AND nickname = '부채도사' AND restaurant_name = '오코메' AND restaurant_small_address = '부산 광안리'),
    'black_okome_fanmaster.jpg',
    0
);

-- 64. 홍석진 - 선 넘은 짜장 (무탄)
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
    '홍석진',
    '선 넘은 짜장',
    'BLACK',
    0,
    '무탄',
    '압구정',
    '서울특별시 강남구 논현로176길 22 1층',
    37.5272989341349,
    127.030310574565,
    '없음',
    'CHINESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/mutan?type=DINING&foodKeywords=%EB%AC%B4%ED%83%84&currentSuggestionType=SHOP_NAME',
    'https://www.instagram.com/mutandining/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '홍석진' AND nickname = '선 넘은 짜장' AND restaurant_name = '무탄' AND restaurant_small_address = '압구정'),
    'black_mutan_crossedlinejjajang.jpg',
    0
);

-- 65. (정보 없음) - 오른팔 셰프 ((정보 없음))
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
    NULL,
    '오른팔 셰프',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '오른팔 셰프'),
    'black_unknown_righthandchef.jpg',
    0
);

-- 66. (정보 없음) - 트로피 콜렉터 ((정보 없음))
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
    NULL,
    '트로피 콜렉터',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '트로피 콜렉터'),
    'black_unknown_trophycollector.jpg',
    0
);

-- 67. 양호실 - 마담 샐러드 ((정보 없음))
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
    '양호실',
    '마담 샐러드',
    'BLACK',
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
    (SELECT id FROM chef WHERE name = '양호실' AND nickname = '마담 샐러드'),
    'black_unknown_madamsalad.jpg',
    0
);

-- 68. 김도형 - 어나더 미트볼 (만가타)
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
    '김도형',
    '어나더 미트볼',
    'BLACK',
    0,
    '만가타',
    '삼청',
    '서울특별시 종로구 삼청로2길 40-5',
    37.5801813093084,
    126.980982059626,
    '없음',
    'EUROPEAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/kdh_mangata?type=DINING',
    'https://www.instagram.com/kdh_mangata/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김도형' AND nickname = '어나더 미트볼' AND restaurant_name = '만가타' AND restaurant_small_address = '삼청'),
    'black_mangata_anothermeatball.jpg',
    0
);

-- 69. 김석현 - 후레쉬 치즈맨 (몽도)
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
    '김석현',
    '후레쉬 치즈맨',
    'BLACK',
    0,
    '몽도',
    '신사',
    '서울특별시 서초구 나루터로10길 29 (주)용마일렉트로닉스 107, 108호',
    37.5135939293426,
    127.016300866311,
    '화',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/mondoseoul?type=DINING',
    'https://www.instagram.com/mondo.seoul_/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '김석현' AND nickname = '후레쉬 치즈맨' AND restaurant_name = '몽도' AND restaurant_small_address = '신사'),
    'black_mongdo_freshcheeseman.jpg',
    0
);

-- 70. 안유성 - 못 먹을텐데 (한국횟집)
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
    '안유성',
    '못 먹을텐데',
    'BLACK',
    0,
    '한국횟집',
    '중화',
    '서울특별시 중랑구 중랑역로 34, 1층',
    37.5970796029997,
    127.076202532202,
    '없음',
    'SASHIMI',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/korea_sashimi_house?type=DINING',
    'https://www.instagram.com/korea_sashimi_house/?hl=ko'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '안유성' AND nickname = '못 먹을텐데' AND restaurant_name = '한국횟집' AND restaurant_small_address = '중화'),
    'black_koreahwejip_canteattho.jpg',
    0
);

-- 71. 임홍근 - 파스타스타 (페리지)
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
    '임홍근',
    '파스타스타',
    'BLACK',
    0,
    '페리지',
    '삼성',
    '서울특별시 강남구 봉은사로68길 6-5, 1층',
    37.5112491980008,
    127.049479155493,
    '일, 월',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/perigee_seoul?type=DINING',
    'https://www.instagram.com/perigee.seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '임홍근' AND nickname = '파스타스타' AND restaurant_name = '페리지' AND restaurant_small_address = '삼성'),
    'black_perigi_pastastar.jpg',
    0
);

-- 72. 전지오 - 샤퀴테리 요정 (랑빠스81)
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
    '전지오',
    '샤퀴테리 요정',
    'BLACK',
    0,
    '랑빠스81',
    '연남',
    '서울특별시 마포구 동교로30길 17-1 빌딩 이카토 1층 101호',
    37.5602626166874,
    126.925153533842,
    '없음',
    'FRENCH',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/limpasse81?type=DINING',
    'https://www.instagram.com/limpasse81/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '전지오' AND nickname = '샤퀴테리 요정' AND restaurant_name = '랑빠스81' AND restaurant_small_address = '연남'),
    'black_lapas81_charcuteriefairy.jpg',
    0
);

-- 73. 전지오 - 샤퀴테리 요정 (바 라핀 부쉬)
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
    '전지오',
    '샤퀴테리 요정',
    'BLACK',
    0,
    '바 라핀 부쉬',
    '이태원',
    '서울특별시 용산구 우사단로14길 4 103호',
    37.5339878242387,
    126.995942427076,
    '없음',
    'DINING',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/lafinebouche?type=DINING',
    'https://www.instagram.com/barlafinebouche.seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '전지오' AND nickname = '샤퀴테리 요정' AND restaurant_name = '바 라핀 부쉬' AND restaurant_small_address = '이태원'),
    'black_barlapinbouche_charcuteriefairy.jpg',
    0
);

-- 74. 배재훈 - 마스터 갓포 (갓포아키 신세주점)
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
    '배재훈',
    '마스터 갓포',
    'BLACK',
    0,
    '갓포아키 신세주점',
    '제주 제주시',
    '제주특별자치도 제주시 노연로 80 메종글래드 제주 1층',
    33.4856033240627,
    126.488905281133,
    '일',
    'IZAKAYA',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/kappoakiijj?uniqueListId=1766386053510&showSubFilterInMap=0&isShowListLabelExpanded=1&centerBoundsLat=35.448105551014955&centerBoundsLng=127.51143262445315',
    'https://www.instagram.com/kappo_akii/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '배재훈' AND nickname = '마스터 갓포' AND restaurant_name = '갓포아키 신세주점' AND restaurant_small_address = '제주 제주시'),
    'black_kappoakisinseju_masterkappo.jpg',
    0
);

-- 75. 황제 - 버거 챔피언 (래빗홀버거)
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
    '황제',
    '버거 챔피언',
    'BLACK',
    0,
    '래빗홀버거',
    '화양',
    '서울특별시 광진구 광나루로 424 1층',
    37.5460394694832,
    127.076527027435,
    '일',
    'HAMBURGER',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/rabbitholeburger?type=WAITING&currentSuggestionType=SHOP_NAME',
    'https://www.instagram.com/rabbithole_burger/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '황제' AND nickname = '버거 챔피언' AND restaurant_name = '래빗홀버거' AND restaurant_small_address = '화양'),
    'black_rabbitholeburger_burgerchampion.jpg',
    0
);

-- 76. 윤영민 - 을지로 윤차장 ((정보 없음))
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
    '윤영민',
    '을지로 윤차장',
    'BLACK',
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
    (SELECT id FROM chef WHERE name = '윤영민' AND nickname = '을지로 윤차장'),
    'black_unknown_euljiroyunchajang.jpg',
    0
);

-- 77. 황지훈 - 불타는 호텔맨 (숯. 더 부처스 엣지)
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
    '황지훈',
    '불타는 호텔맨',
    'BLACK',
    0,
    '숯. 더 부처스 엣지',
    '여의도',
    '서울특별시 영등포구 국제금융로 10 서울 국제금융 센터 콘래드서울 37층',
    37.5251775245928,
    126.924876706923,
    '없음',
    'DINING',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/37grillandbar?type=DINING',
    NULL
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '황지훈' AND nickname = '불타는 호텔맨' AND restaurant_name = '숯. 더 부처스 엣지' AND restaurant_small_address = '여의도'),
    'black_soot.thebutchersedge_burninghotelman.jpg',
    0
);

-- 78. 최규덕 - 사시미 검객 (미가키)
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
    '최규덕',
    '사시미 검객',
    'BLACK',
    0,
    '미가키',
    '청담',
    '서울특별시 강남구 선릉로158길 14-2, 4층',
    37.5251906223084,
    127.04095242964,
    '일',
    'OMAKASE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/migaki?type=DINING',
    'https://www.instagram.com/migaki_cheongdam/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '최규덕' AND nickname = '사시미 검객' AND restaurant_name = '미가키' AND restaurant_small_address = '청담'),
    'black_migaki_sashimiswordsman.jpg',
    0
);

-- 79. 김민기 - 최연소 대사관셰프 (주한 덴마크/노르웨이 대사관)
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
    '김민기',
    '최연소 대사관셰프',
    'BLACK',
    0,
    '주한 덴마크/노르웨이 대사관',
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
    (SELECT id FROM chef WHERE name = '김민기' AND nickname = '최연소 대사관셰프' AND restaurant_name = '주한 덴마크/노르웨이 대사관'),
    'black_denmarknorwayembassy_youngestembassychef.jpg',
    0
);

-- 80. 조경찬 - 미스터 사이공 (을지깐깐)
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
    '조경찬',
    '미스터 사이공',
    'BLACK',
    0,
    '을지깐깐',
    '을지로',
    '서울특별시 중구 을지로12길 12 202호',
    37.5657437884452,
    126.991063733657,
    '없음',
    'VIETNAMESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/euljicanhcanh?type=WAITING&currentSuggestionType=SHOP_NAME',
    'https://www.instagram.com/eulji_canhcanh/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '조경찬' AND nickname = '미스터 사이공' AND restaurant_name = '을지깐깐' AND restaurant_small_address = '을지로'),
    'black_euljikkankan_mistersaigon.jpg',
    0
);

-- 81. 조경찬 - 미스터 사이공 (깐깐)
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
    '조경찬',
    '미스터 사이공',
    'BLACK',
    0,
    '깐깐',
    '광화문',
    '서울특별시 종로구 종로 33 그랑서울 지하1층 116호',
    37.5709617749066,
    126.981437983842,
    '없음',
    'VIETNAMESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/canhcanhgranseoul?type=WAITING',
    'https://www.instagram.com/eulji_canhcanh/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '조경찬' AND nickname = '미스터 사이공' AND restaurant_name = '깐깐' AND restaurant_small_address = '광화문'),
    'black_kkankan_mistersaigon.jpg',
    0
);

-- 82. 박정현 - 평가절하 (포그)
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
    '박정현',
    '평가절하',
    'BLACK',
    0,
    '포그',
    '마포',
    '서울특별시 마포구 백범로16안길 15-1 1층',
    37.54649379406,
    126.942984652684,
    '없음',
    'PIZZA',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/pog_seoul1',
    'https://www.instagram.com/pog_seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '박정현' AND nickname = '평가절하' AND restaurant_name = '포그' AND restaurant_small_address = '마포'),
    'black_porg_underrated.jpg',
    0
);

-- 83. (정보 없음) - 남해 힙스터 (힙한식)
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
    NULL,
    '남해 힙스터',
    'BLACK',
    0,
    '힙한식',
    '남해',
    '경상남도 남해군 미조면 동부대로 2',
    NULL,
    NULL,
    '화',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/HiphansikNamhae?type=WAITING',
    'https://www.instagram.com/hip_hansik/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '남해 힙스터' AND restaurant_name = '힙한식' AND restaurant_small_address = '남해'),
    'black_hiphansik_namhaehipster.jpg',
    0
);

-- 84. 윤석환 - 투스타 라면 (칸세이)
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
    '윤석환',
    '투스타 라면',
    'BLACK',
    0,
    '칸세이',
    '가락',
    '서울특별시 송파구 송파대로30길 41-7 1층 102호',
    37.4964972129419,
    127.121113303638,
    '없음',
    'CONTEMPORARY',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/kanseiseoul?type=DINING',
    'https://www.instagram.com/kansei_seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '윤석환' AND nickname = '투스타 라면' AND restaurant_name = '칸세이' AND restaurant_small_address = '가락'),
    'black_kansei_2starramen.jpg',
    0
);

-- 85. (정보 없음) - 파주부전 ((정보 없음))
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
    NULL,
    '파주부전',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '파주부전'),
    'black_unknown_pajubujeon.jpg',
    0
);

-- 86. 박세효 - 철판대장 (죠죠)
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
    '박세효',
    '철판대장',
    'BLACK',
    0,
    '죠죠',
    '성수',
    '서울특별시 성동구 성수동2가 272-1',
    37.5414563724618,
    127.061030563865,
    '없음',
    'JAPANESE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/jyojyo?type=WAITING&currentSuggestionType=SHOP_NAME_LIKE',
    'https://www.instagram.com/jyojyo.kr/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '박세효' AND nickname = '철판대장' AND restaurant_name = '죠죠' AND restaurant_small_address = '성수'),
    'black_jojo_griddleboss.jpg',
    0
);

-- 87. 권옥식 - 인생 소시지 (급이다른부대찌개)
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
    '권옥식',
    '인생 소시지',
    'BLACK',
    0,
    '급이다른부대찌개',
    '대전',
    '대전광역시 유성구 은구비서로23번길 8-14, 1층',
    36.3748681824689,
    127.32033066002,
    '일',
    'KOREAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/geubidaleunbudaejjigae?type=WAITING',
    'https://www.instagram.com/anotherlv.buzzi/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '권옥식' AND nickname = '인생 소시지' AND restaurant_name = '급이다른부대찌개' AND restaurant_small_address = '대전'),
    'black_geubidareunbudaejjigae_lifesausage.jpg',
    0
);

-- 88. (정보 없음) - 영블러드 (레스토랑 온 ON)
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
    NULL,
    '영블러드',
    'BLACK',
    0,
    '레스토랑 온 ON',
    '청담',
    '서울특별시 강남구 도산대로92길 42 도빌딩청담 B1',
    NULL,
    NULL,
    '월',
    'FRENCH',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/restauranton?type=DINING',
    'https://www.instagram.com/restaurant_on/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '영블러드' AND restaurant_name = '레스토랑 온 ON' AND restaurant_small_address = '청담'),
    'black_restauranton_youngblood.jpg',
    0
);

-- 89. (정보 없음) - 장어 박사 ((정보 없음))
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
    NULL,
    '장어 박사',
    'BLACK',
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
    (SELECT id FROM chef WHERE name IS NULL AND nickname = '장어 박사'),
    'black_unknown_eeldoctor.jpg',
    0
);

-- 90. 이찬양 - 삐딱한 천재 (Original Numbers 청담)
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
    '이찬양',
    '삐딱한 천재',
    'BLACK',
    0,
    'Original Numbers 청담',
    '청담',
    '서울특별시 강남구 도산대로55길 24',
    37.5250720429425,
    127.040713439647,
    '일, 월',
    'COURSE',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/original_numbers?type=DINING',
    'https://www.instagram.com/original.numbers/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이찬양' AND nickname = '삐딱한 천재' AND restaurant_name = 'Original Numbers 청담' AND restaurant_small_address = '청담'),
    'black_originalnumberscheongdam_rebelliousgenius.jpg',
    0
);

-- 91. 우정욱 - 서울 엄마 (수퍼판)
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
    '우정욱',
    '서울 엄마',
    'BLACK',
    0,
    '수퍼판',
    '압구정',
    '서울특별시 강남구 논현로167길 15. 휴에프빌딩 2층',
    37.5244390021596,
    127.026839035907,
    '일',
    'FUSION',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/superpan?type=DINING',
    'https://www.instagram.com/superpan_seoul/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '우정욱' AND nickname = '서울 엄마' AND restaurant_name = '수퍼판' AND restaurant_small_address = '압구정'),
    'black_superpan_seoulmother.jpg',
    0
);

-- 92. 이선영 - 요리하는 방송작가 (동남방앗간)
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
    '이선영',
    '요리하는 방송작가',
    'BLACK',
    0,
    '동남방앗간',
    '한남',
    '서울특별시 용산구 유엔빌리지길 20 지하1층',
    37.5337736776764,
    127.009825582217,
    '없음',
    'ITALIAN',
    NULL,
    'https://app.catchtable.co.kr/ct/shop/dongnam?type=DINING',
    'https://www.instagram.com/dongnam_official/'
);

INSERT INTO chef_image (
    chef_id,
    image_url,
    display_order
) VALUES (
    (SELECT id FROM chef WHERE name = '이선영' AND nickname = '요리하는 방송작가' AND restaurant_name = '동남방앗간' AND restaurant_small_address = '한남'),
    'black_dongnambangatgan_cookingbwriter.jpg',
    0
);

commit;
