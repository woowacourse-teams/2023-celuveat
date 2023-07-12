CREATE TABLE IF NOT EXISTS celeb
(
    id                   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name                 VARCHAR(255) NOT NULL,
    youtube_channel_name VARCHAR(255) NOT NULL,
    youtube_channel_id   VARCHAR(255) NOT NULL,
    subscriber_count     INT          NOT NULL,
    youtube_channel_url  VARCHAR(511) NOT NULL,
    background_image_url VARCHAR(511) NOT NULL,
    profile_image_url    VARCHAR(511) NOT NULL,
    created_date         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS  restaurant
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    image_url          VARCHAR(511) NOT NULL,
    name               VARCHAR(255) NOT NULL,
    category           VARCHAR(255) NOT NULL,
    road_address       VARCHAR(255) NOT NULL,
    address_lot_number VARCHAR(255) NOT NULL,
    zip_code           VARCHAR(15)  NOT NULL,
    latitude           VARCHAR(255) NOT NULL,
    longitude          VARCHAR(255) NOT NULL,
    phone_number       VARCHAR(255) NOT NULL,
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS  video
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    celeb_id      BIGINT       NOT NULL,
    restaurant_id BIGINT       NOT NULL,
    title         VARCHAR(255) NOT NULL,
    video_id      VARCHAR(255) NOT NULL,
    view_count    INT          NOT NULL,
    upload_date   TIMESTAMP    NOT NULL,
    ads           BOOLEAN,
    created_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (celeb_id) REFERENCES celeb (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id)
);

CREATE TABLE IF NOT EXISTS  video_history
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    celeb_id     BIGINT       NOT NULL,
    title        VARCHAR(255) NOT NULL,
    video_id     VARCHAR(255) NOT NULL,
    view_count   INT          NOT NULL,
    upload_date  TIMESTAMP    NOT NULL,
    ads          BOOLEAN,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (celeb_id) REFERENCES celeb (id)
);

CREATE TABLE IF NOT EXISTS  admin
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    username     VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
