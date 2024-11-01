CREATE TABLE game
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    score     INT                                     NOT NULL,
    time      INT                                     NOT NULL,
    player_id BIGINT                                  NOT NULL,
    status    VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_game PRIMARY KEY (id)
);

CREATE TABLE player
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name      VARCHAR(255)                            NOT NULL,
    highscore INT,
    password  VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_player PRIMARY KEY (id)
);

CREATE TABLE theme
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_theme PRIMARY KEY (id)
);

CREATE TABLE theme_words
(
    theme_id BIGINT NOT NULL,
    word     VARCHAR(255)
);

ALTER TABLE game
    ADD CONSTRAINT FK_GAME_ON_PLAYER FOREIGN KEY (player_id) REFERENCES player (id);

ALTER TABLE theme_words
    ADD CONSTRAINT fk_theme_words_on_theme_entity FOREIGN KEY (theme_id) REFERENCES theme (id);