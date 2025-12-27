-- =========================================
-- 스키마 초기화 및 테이블 생성 DDL
-- =========================================

-- 기존 테이블 삭제 (역순으로 삭제)
DROP TABLE IF EXISTS chef_image CASCADE;
DROP TABLE IF EXISTS chef_ranking CASCADE;
DROP TABLE IF EXISTS chef CASCADE;

-- =========================================
-- Chef 테이블 생성
-- =========================================
CREATE TABLE chef
(
    -- BaseEntity 필드
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    -- Chef 필드
    name       VARCHAR(15),
    nickname   VARCHAR(15),
    chef_type  VARCHAR(10) NOT NULL,
    view_count BIGINT      NOT NULL DEFAULT 0,
    version    BIGINT,

    -- Restaurant 임베디드 필드
    restaurant_name           VARCHAR(30),
    restaurant_address        VARCHAR(50),
    restaurant_small_address  VARCHAR(20),
    latitude                  DOUBLE PRECISION,
    longitude                 DOUBLE PRECISION,
    restaurant_closed_days    VARCHAR(50),
    restaurant_category       VARCHAR(20),
    naver_reservation_url     TEXT,
    catch_table_url           TEXT,
    instagram_url             TEXT,

    -- 제약조건: name 또는 nickname 중 하나는 필수
    CONSTRAINT chk_chef_name_or_nickname CHECK (
        (name IS NOT NULL AND name != '') OR
        (nickname IS NOT NULL AND nickname != '')
        ),

    -- chef_type ENUM 제약조건
    CONSTRAINT chk_chef_type CHECK (chef_type IN ('BLACK', 'WHITE')),

    -- restaurant_category ENUM 제약조건
    CONSTRAINT chk_restaurant_category CHECK (
        restaurant_category IS NULL OR
        restaurant_category IN (
            'KOREAN', 'CHINESE', 'JAPANESE', 'IZAKAYA', 'ITALIAN', 'WESTERN',
            'BISTRO', 'OMAKASE', 'BBQ', 'FRENCH', 'AMERICAN', 'BUNSIK',
            'DINING', 'CONTEMPORARY', 'SEAFOOD', 'COURSE', 'WINE', 'TONKATSU',
            'NAENGMYEON', 'ASIAN', 'GRILLED_EEL', 'EUROPEAN', 'SASHIMI',
            'HAMBURGER', 'VIETNAMESE', 'PIZZA', 'FUSION', 'WESTERN_FOOD',
            'BRUNCH', 'MEAT', 'CAFE'
        )
    )
);

-- Chef 테이블 인덱스
CREATE INDEX idx_chef_type ON chef (chef_type);
CREATE INDEX idx_chef_deleted_at ON chef (deleted_at);
CREATE INDEX idx_chef_view_count ON chef (view_count DESC);

-- Chef 테이블 코멘트
COMMENT ON TABLE chef IS '흑백요리사 출연 셰프 정보';
COMMENT ON COLUMN chef.chef_type IS '셰프 타입: BLACK(흑요리사), WHITE(백요리사)';
COMMENT ON COLUMN chef.view_count IS '셰프 상세 페이지 조회수 (낙관적 락 사용)';
COMMENT ON COLUMN chef.version IS 'JPA 낙관적 락 버전';

-- =========================================
-- ChefImage 테이블 생성 (ElementCollection)
-- =========================================
CREATE TABLE chef_image
(
    chef_id       BIGINT       NOT NULL,
    image_url     VARCHAR(500) NOT NULL,
    display_order INTEGER      NOT NULL,

    -- 외래키 제약조건
    CONSTRAINT fk_chef_image_chef
        FOREIGN KEY (chef_id) REFERENCES chef (id)
            ON DELETE CASCADE,

    -- 복합 기본키 (chef_id + display_order)
    CONSTRAINT pk_chef_image PRIMARY KEY (chef_id, display_order)
);

-- ChefImage 테이블 인덱스
CREATE INDEX idx_chef_image_chef_id ON chef_image (chef_id);

-- ChefImage 테이블 코멘트
COMMENT ON TABLE chef_image IS '셰프 이미지 목록 (최대 3개)';
COMMENT ON COLUMN chef_image.display_order IS '이미지 표시 순서 (0부터 시작)';

-- =========================================
-- ChefRanking 테이블 생성
-- =========================================
CREATE TABLE chef_ranking
(
    -- BaseEntity 필드
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at   TIMESTAMP,

    -- ChefRanking 필드
    chef_id      BIGINT      NOT NULL,
    ranking_type VARCHAR(10) NOT NULL,
    period_start DATE        NOT NULL,
    rank_position INTEGER     NOT NULL,
    score        BIGINT,

    -- 외래키 제약조건
    CONSTRAINT fk_chef_ranking_chef
        FOREIGN KEY (chef_id) REFERENCES chef (id)
            ON DELETE CASCADE,

    -- ranking_type ENUM 제약조건
    CONSTRAINT chk_ranking_type CHECK (ranking_type IN ('WEEKLY')),

    -- 유니크 제약조건: 같은 기간에 한 셰프는 하나의 랭킹만 가능
    CONSTRAINT uk_ranking_period_chef
        UNIQUE (ranking_type, period_start, chef_id),

    -- 유니크 제약조건: 같은 기간에 같은 순위는 하나만 존재
    CONSTRAINT uk_ranking_period_rank
        UNIQUE (ranking_type, period_start, rank_position)
);

-- ChefRanking 테이블 인덱스
CREATE INDEX idx_ranking_period ON chef_ranking (ranking_type, period_start, rank_position);
CREATE INDEX idx_ranking_chef ON chef_ranking (chef_id);
CREATE INDEX idx_ranking_deleted_at ON chef_ranking (deleted_at);

-- ChefRanking 테이블 코멘트
COMMENT ON TABLE chef_ranking IS '셰프 랭킹 정보 (주간/월간 등)';
COMMENT ON COLUMN chef_ranking.ranking_type IS '랭킹 타입: WEEKLY(주간)';
COMMENT ON COLUMN chef_ranking.period_start IS '랭킹 집계 시작일';
COMMENT ON COLUMN chef_ranking.rank_position IS '랭킹 순위 (1위, 2위, ...)';
COMMENT ON COLUMN chef_ranking.score IS '랭킹 점수 (조회수 등)';