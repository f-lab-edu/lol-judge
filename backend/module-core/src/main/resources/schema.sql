-- 회원 테이블
-- 패스워드 길이는 BcryptEncoder의 인코딩 값인 60글자로 설정
-- InnoDB에서 인덱스 역순 스캔이 인덱스 정순 스캔에 비해 느리다.
-- 이유1. 페이지 잠금이 인덱스 정순 스캔에 적합한 구조
-- 이유2. 페이지 내에서 인덱스 레코드가 단방향으로만 연결된 구조
-- 따라서 index_rank_score 인덱스의 rank_score 컬럼에 DESC 키워드를 붙여 내림차순 정렬시, 정순 스캔이 되도록 한다.
CREATE TABLE IF NOT EXISTS member
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    email       VARCHAR(320) UNIQUE,
    `password`  VARCHAR(60),
    profile_url VARCHAR(200),
    judge_point INT     DEFAULT 0,
    rank_score  BIGINT  DEFAULT 0,
    deleted     BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (id),
    INDEX index_rank_score (rank_score DESC)
);

-- 리그오브레전드 계정 테이블
CREATE TABLE IF NOT EXISTS game_account
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    member_id      BIGINT,
    lol_login_id   VARCHAR(24) UNIQUE,
    nickname       VARCHAR(16) UNIQUE,
    position       VARCHAR(16),
    lol_tier_color VARCHAR(16),
    lol_tier_level TINYINT CHECK (0 <= lol_tier_level AND lol_tier_level < 5),
    lol_tier_point INT,

    PRIMARY KEY (id),

    FOREIGN KEY (member_id)
        REFERENCES member (id)
);

-- 논쟁거리 재판(선거) 테이블
CREATE TABLE IF NOT EXISTS election
(
    id                BIGINT NOT NULL AUTO_INCREMENT,
    election_no       VARCHAR(64),
    `status`          VARCHAR(15),
    cost              INT,
    total_voted_count BIGINT DEFAULT 0,
    youtube_url       VARCHAR(100),
    created_at        TIMESTAMP,
    ended_at          TIMESTAMP,

    PRIMARY KEY (id)
);

-- 인게임 정보 테이블
CREATE TABLE IF NOT EXISTS game_info
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    election_id BIGINT,
    match_id    VARCHAR(16),
    team        VARCHAR(8),
    position    VARCHAR(16),
    nickname    VARCHAR(16),
    champion    VARCHAR(8),
    `kill`      TINYINT,
    death       TINYINT,
    assist      TINYINT,
    played_at   TIMESTAMP,

    PRIMARY KEY (id),

    FOREIGN KEY (election_id)
        REFERENCES election (id)
);

-- 논쟁거리 재판(선거) 후보자 테이블
CREATE TABLE IF NOT EXISTS candidate
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    election_id  BIGINT NOT NULL,
    member_id    BIGINT NOT NULL,
    voted_status ENUM ('WIN','LOSE'),
    opinion      VARCHAR(1000),
    champion     VARCHAR(16),

    PRIMARY KEY (id),

    FOREIGN KEY (election_id)
        REFERENCES election (id),
    FOREIGN KEY (member_id)
        REFERENCES member (id)
);

-- 논쟁거리 재판(선거) 투표 테이블
CREATE TABLE IF NOT EXISTS vote
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    member_id    BIGINT,
    candidate_id BIGINT,

    FOREIGN KEY (member_id)
        REFERENCES member (id),
    FOREIGN KEY (candidate_id)
        REFERENCES candidate (id),

    PRIMARY KEY (id)
);

-- 알림 테이블
CREATE TABLE IF NOT EXISTS notification
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    member_id   BIGINT,
    contents    VARCHAR(1000),
    read_status VARCHAR(16),
    created_at  TIMESTAMP,

    FOREIGN KEY (member_id)
        REFERENCES member (id),

    PRIMARY KEY (id)
);
