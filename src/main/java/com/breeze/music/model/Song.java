package com.breeze.music.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Song name cannot be empty")
    @Column(name = "song_name")
    private String songName;

    @NotNull(message = "Artist name cannot be empty")
    private String artist;

    @Pattern(regexp="^(http|https)://.*", message = "Song artwork must be a valid HTTP URL")
    private String artwork;

    @Pattern(regexp="^(http|https)://.*", message = "Song URL must be a valid HTTP URL")
    @Column(name = "song_url")
    private String songUrl;

    public Song(UUID id, String songName, String artist, String artwork, String songUrl) {
        this.id = id;
        this.songName = songName;
        this.artist = artist;
        this.artwork = artwork;
        this.songUrl = songUrl;
    }

    public Song() {

    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
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

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
