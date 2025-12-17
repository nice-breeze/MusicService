package com.breeze.music.repository;

import com.breeze.music.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Song, Long> {
    Optional<Song> getSongById(Long id);
}
