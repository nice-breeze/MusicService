-- Migration V1: Create music schema
CREATE SCHEMA IF NOT EXISTS music AUTHORIZATION my_app_user;

-- Create table song in schema music
CREATE TABLE IF NOT EXISTS music.song
(
    id        UUID NOT NULL,
    song_name VARCHAR(255),
    artist    VARCHAR(255),
    artwork    VARCHAR(255),
    song_url  VARCHAR(255),
    CONSTRAINT pk_song PRIMARY KEY (id)
);

