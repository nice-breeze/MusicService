package com.breeze.music.controller;

import com.breeze.music.exception.ResourceNotFoundException;
import com.breeze.music.model.Song;
import com.breeze.music.service.MusicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music/v1")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(musicService.getSongById(id));
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs(){
        return ResponseEntity.ok(musicService.getAllSongs());
    }

    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song){
        Song createdSong = musicService.createSong(song);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdSong);
    }

    @DeleteMapping("/song/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id){
        musicService.deleteSongById(id);

        return ResponseEntity.ok(String.format("Song id: %d deleted successfully", id));
    }
}
