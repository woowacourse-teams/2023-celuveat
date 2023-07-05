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

CREATE TABLE restaurant
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    image_url    VARCHAR(511) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    address      VARCHAR(255) NOT NULL,
    category     VARCHAR(255) NOT NULL,
    rating       VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE video
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    celeb_id       BIGINT       NOT NULL,
    restaurant_id  BIGINT       NOT NULL,
    title          VARCHAR(255) NOT NULL,
    view_count     INT          NOT NULL,
    video_url      VARCHAR(511) NOT NULL,
    published_date TIMESTAMP    NOT NULL,
    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (celeb_id) REFERENCES celeb (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id)
);
