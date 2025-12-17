package com.breeze.music.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    private String songName;

    private String artist;

    private String songUrl;

    public Song(Long id, String songName, String artist, String songUrl) {
        this(songName, artist, songUrl);
        this.id = id;
    }

    public Song(String songName, String artist, String songUrl) {
        this.songName = songName;
        this.artist = artist;
        this.songUrl = songUrl;
    }

    public Song() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
