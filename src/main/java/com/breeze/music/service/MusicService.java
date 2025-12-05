package com.breeze.music.service;

import com.breeze.music.model.Song;
import com.breeze.music.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicService {

    private final MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public Song getSongById(Long id){
        Optional<Song> optionalSong = musicRepository.getSongById(id);

        if(optionalSong.isPresent()){

        }
        return song.orElse(null);
    }
}
