-- 회원 테이블
CREATE TABLE member
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    email       VARCHAR(320) UNIQUE,
    `password`  VARCHAR(32),
    profile_url VARCHAR(200),
    judge_point INT     DEFAULT 0,
    deleted     BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (id)
);

-- 리그오브레전드 계정 테이블
CREATE TABLE game_account
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    member_id       BIGINT,
    login_id        VARCHAR(24) UNIQUE,
    nickname        VARCHAR(16) UNIQUE,
    rank_tier_group VARCHAR(16),
    rank_tier_level TINYINT CHECK (0 < rank_tier_level AND rank_tier_level < 5),

    PRIMARY KEY (id),

    FOREIGN KEY (member_id)
        REFERENCES member (id)
);

-- 인게임 정보 테이블
CREATE TABLE game_info
(
    id        BIGINT NOT NULL AUTO_INCREMENT,

    team      VARCHAR(8),
    position  VARCHAR(16),
    nickname  VARCHAR(16),
    champion  VARCHAR(8),
    `kill`    TINYINT,
    death     TINYINT,
    assist    TINYINT,
    played_at TIMESTAMP,

    PRIMARY KEY (id)
);

-- 논쟁거리 재판(선거) 테이블
CREATE TABLE election
(
    id               BIGINT NOT NULL AUTO_INCREMENT,
    game_info_id     BIGINT,
    election_no      VARCHAR(64),
    `status`         VARCHAR(8),
    contents         VARCHAR(1000),
    cost             INT,
    youtube_share_id VARCHAR(20),
    modified_at      TIMESTAMP,
    ended_at         TIMESTAMP,

    PRIMARY KEY (id),

    FOREIGN KEY (game_info_id)
        REFERENCES game_info (id)
);

-- 논쟁거리 재판(선거) 후보자 테이블
CREATE TABLE candidate
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    election_id  BIGINT NOT NULL,
    member_id    BIGINT NOT NULL,
    voted_count  BIGINT,
    voted_status ENUM ('WIN','LOSE'),

    PRIMARY KEY (id),

    FOREIGN KEY (election_id)
        REFERENCES election (id),
    FOREIGN KEY (member_id)
        REFERENCES member (id)
);

-- 논쟁거리 재판(선거) 후보자 의견 테이블
CREATE TABLE opinion
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    candidate_id BIGINT NOT NULL,
    game_info_id BIGINT NOT NULL,
    contents     VARCHAR(1000),
    modified_at  TIMESTAMP,
    is_agreed    BOOLEAN,

    FOREIGN KEY (candidate_id)
        REFERENCES candidate (id),

    PRIMARY KEY (id)
);

-- 논쟁거리 재판(선거) 투표 테이블
CREATE TABLE vote
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
