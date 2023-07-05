CREATE TABLE celeb
(
    id                   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name                 VARCHAR(255) NOT NULL,
    youtube_id           VARCHAR(255) NOT NULL,
    subscriber_count     INT          NOT NULL,
    link                 VARCHAR(511) NOT NULL,
    background_image_url VARCHAR(511) NOT NULL,
    profile_image_url    VARCHAR(511) NOT NULL,
    created_date         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
