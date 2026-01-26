-- ==================================================
-- 백요리사 (WHITE) 최종성적 업데이트
-- ==================================================

-- 우승
UPDATE chef SET final_placement = '우승' WHERE name = '최강록' AND chef_type = 'WHITE';

-- 3위
UPDATE chef SET final_placement = '3위' WHERE name = '후덕죽' AND chef_type = 'WHITE';

-- 4위
UPDATE chef SET final_placement = '4위' WHERE name = '정호영' AND chef_type = 'WHITE';

-- 6위
UPDATE chef SET final_placement = '6위' WHERE name = '선재스님' AND chef_type = 'WHITE';

-- 7위
UPDATE chef SET final_placement = '7위' WHERE name = '임성근' AND chef_type = 'WHITE';

-- 5라운드 진출
UPDATE chef SET final_placement = '5라운드 진출' WHERE name = '손종원' AND chef_type = 'WHITE';

-- 4-2라운드 진출
UPDATE chef SET final_placement = '4-2라운드 진출' WHERE name = '김희은' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '4-2라운드 진출' WHERE name = '천상현' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '4-2라운드 진출' WHERE name = '박효남' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '4-2라운드 진출' WHERE name = '최유강' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '4-2라운드 진출' WHERE name = '김성운' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '4-2라운드 진출' WHERE name = '샘 킴' AND chef_type = 'WHITE';

-- 2라운드 진출
UPDATE chef SET final_placement = '2라운드 진출' WHERE name = '이준' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '2라운드 진출' WHERE name = '레이먼 킴' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '2라운드 진출' WHERE name = '송훈' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '2라운드 진출' WHERE name = '심성철' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '2라운드 진출' WHERE name = '이금희' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '2라운드 진출' WHERE name = '제니 월든' AND chef_type = 'WHITE';
UPDATE chef SET final_placement = '2라운드 진출' WHERE name = '김건' AND chef_type = 'WHITE';

-- 1라운드 탈락
UPDATE chef SET final_placement = '1라운드 탈락' WHERE name = '김도윤' AND chef_type = 'WHITE';

-- ==================================================
-- 흑요리사 (BLACK) 최종성적 업데이트
-- ==================================================

-- 준우승
UPDATE chef SET final_placement = '준우승' WHERE nickname = '요리괴물' AND chef_type = 'BLACK';

-- 5위
UPDATE chef SET final_placement = '5위' WHERE nickname = '술 빚는 윤주모' AND chef_type = 'BLACK';

-- 4-1라운드 진출
UPDATE chef SET final_placement = '4-1라운드 진출' WHERE nickname = '뉴욕에 간 돼지곰탕' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '4-1라운드 진출' WHERE nickname = '바베큐연구소장' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '4-1라운드 진출' WHERE nickname = '서울 엄마' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '4-1라운드 진출' WHERE nickname = '안녕 봉주르' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '4-1라운드 진출' WHERE nickname = '중식 마녀' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '4-1라운드 진출' WHERE nickname = '삐딱한 천재' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '4-1라운드 진출' WHERE nickname = '칼마카세' AND chef_type = 'BLACK';

-- 2라운드 진출
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '무쇠팔' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '반찬술사' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '부채도사' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '쓰리스타 킬러' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '아기 맹수' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '유행왕' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '키친 보스' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '천생연분' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '프렌치 파파' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '2라운드 진출' WHERE nickname = '4평 외톨이' AND chef_type = 'BLACK';

-- 1라운드 탈락
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '그때 명셰프' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '김치다이닝' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '남해 힙스터' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '내장백서' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '뉴욕 기사식당' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '닭발로 16억' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '디어 그랜마' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '떡볶이 명인' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '라오스홀릭' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '마담 샐러드' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '마스터 갓포' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '망원시장 히어로' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '메이드 인 코리아' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '못 먹을텐데' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '미스터 사이공' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '밥도둑 포차' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '버거 챔피언' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '번아웃 요리사' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '불타는 호텔맨' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '빠다걸' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '사시미 검객' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '삼대가 중식' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '샤퀴테리 요정' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '서촌 황태자' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '선 넘은 짜장' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '수타킹' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '앵그리 코리안' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '어나더 미트볼' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '연마대왕' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '영블러드' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '오른팔 셰프' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '요리하는 방송작가' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '을지로 윤차장' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '인생 소시지' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '장어박사' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '전설의 학식뷔페' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '줄서는 돈가스' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '중식 폭주족' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '지랄 쓰부' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '채소 프린스' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '철판대장' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '최연소 대사관셰프' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '최저시급' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '축구 국대 요리사' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '투스타 라면' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '튀김의 기술' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '트로피 콜렉터' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '파리 4.9' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '파스타스타' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '파주부전' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '평가절하' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '평냉신성' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '평양 큰형님' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '황금손' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '후레쉬 치즈맨' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '휴게소 총괄셰프' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '2등의 손맛' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '5500원 영양사' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '5성급 김치대가' AND chef_type = 'BLACK';
UPDATE chef SET final_placement = '1라운드 탈락' WHERE nickname = '8국마스터' AND chef_type = 'BLACK';

COMMIT;
