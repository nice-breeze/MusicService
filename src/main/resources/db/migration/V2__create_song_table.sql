CREATE SEQUENCE IF NOT EXISTS song_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE song
(
    id        BIGINT NOT NULL,
    song_name VARCHAR(255),
    artist    VARCHAR(255),
    song_url  VARCHAR(255),
    CONSTRAINT pk_song PRIMARY KEY (id)
);