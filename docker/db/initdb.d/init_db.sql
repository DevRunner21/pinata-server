USE db_pinata;

## Create Tables

CREATE TABLE tb_event_histories
(
    event_history_id  BIGINT       NOT NULL AUTO_INCREMENT,
    created_at        DATETIME,
    updated_at        DATETIME,
    use_flag          BIT          NOT NULL,
    event_id          BIGINT       NOT NULL,
    event_item_id     BIGINT       NOT NULL,
    is_hit            BIT          NOT NULL,
    participant_email VARCHAR(255) NOT NULL,
    participant_id    BIGINT       NOT NULL,
    participant_name  VARCHAR(100) NOT NULL,
    title             VARCHAR(500) NOT NULL,
    PRIMARY KEY (event_history_id)
) ENGINE = InnoDB;

CREATE TABLE tb_event_items
(
    event_item_id   BIGINT       NOT NULL AUTO_INCREMENT,
    created_at      DATETIME,
    updated_at      DATETIME,
    use_flag        BIT          NOT NULL,
    image_file_name VARCHAR(100),
    is_accepted     BIT          NOT NULL,
    ranking         INTEGER      NOT NULL,
    title           VARCHAR(500) NOT NULL,
    event_id        BIGINT,
    PRIMARY KEY (event_item_id)
) ENGINE = InnoDB;

CREATE TABLE tb_events
(
    event_id             BIGINT       NOT NULL AUTO_INCREMENT,
    created_at           DATETIME,
    updated_at           DATETIME,
    use_flag             BIT          NOT NULL,
    close_at             DATETIME,
    code                 VARCHAR(500) NOT NULL,
    hit_count            INTEGER      NOT NULL,
    hit_image_file_name  VARCHAR(100),
    hit_message          VARCHAR(1000),
    limit_count          INTEGER      NOT NULL,
    miss_image_file_name VARCHAR(100),
    miss_message         VARCHAR(1000),
    open_at              DATETIME,
    participant_count    INTEGER      NOT NULL,
    title                VARCHAR(500) NOT NULL,
    type                 VARCHAR(255),
    PRIMARY KEY (event_id)
) ENGINE = InnoDB;

CREATE TABLE tb_users
(
    user_id           BIGINT       NOT NULL AUTO_INCREMENT,
    created_at        DATETIME,
    updated_at        DATETIME,
    email             VARCHAR(100) NOT NULL,
    nickname          VARCHAR(255),
    prifile_image_url VARCHAR(255),
    provider_id       BIGINT       NOT NULL,
    state             VARCHAR(255),
    PRIMARY KEY (user_id)
) ENGINE = InnoDB;

## Set Constraint
ALTER TABLE tb_event_items
    ADD CONSTRAINT fk_event_item_to_event
        FOREIGN KEY (event_id)
            REFERENCES tb_events (event_id);