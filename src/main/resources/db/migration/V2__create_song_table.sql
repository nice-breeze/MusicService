CREATE TABLE IF NOT EXISTS pomodoro.song
(
    id        BIGINT NOT NULL,
    song_name VARCHAR(255),
    artist    VARCHAR(255),
    song_url  VARCHAR(255),
    CONSTRAINT pk_song PRIMARY KEY (id)
);

