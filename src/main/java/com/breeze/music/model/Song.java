package com.breeze.music.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue
    @NotNull(message = "Song id cannot be empty")
    private Long id;

    @NotNull(message = "Song name cannot be empty")
    @Column(name = "song_name")
    private String songName;

    @NotNull(message = "Artist name cannot be empty")
    private String artist;

    @Pattern(regexp="^(http|https)://.*", message = "Song URL must be a valid HTTP URL")
    @Column(name = "song_url")
    private String songUrl;

    public Song(Long id, String songName, String artist, String songUrl) {
        this.id = id;
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
