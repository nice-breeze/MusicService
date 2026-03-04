package com.breeze.music.service;

import com.breeze.music.exception.ResourceNotFoundException;
import com.breeze.music.model.Song;
import com.breeze.music.repository.MusicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MusicService {
    private static final Logger log = LoggerFactory.getLogger(MusicService.class);

    private final MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public Song createSong(Song song){
        Song savedSong = musicRepository.save(song);

        log.info("Song with id: {} saved successfully", savedSong);

        return savedSong;
    }

    public List<Song> getAllSongs(){
        return musicRepository.findAll();
    }

    public Song getSongById(UUID id) throws ResourceNotFoundException {
        Optional<Song> optionalSong = musicRepository.getSongById(id);

        return optionalSong.orElseThrow(() -> new ResourceNotFoundException(String.format("Song not found with id: %s", id)));
    }

    public void deleteSongById(UUID id){
        musicRepository.deleteById(id);
    }





}
