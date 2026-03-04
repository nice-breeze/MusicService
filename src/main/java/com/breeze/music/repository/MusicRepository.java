package com.breeze.music.repository;

import com.breeze.music.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MusicRepository extends JpaRepository<Song, UUID> {
    Optional<Song> getSongById(UUID id);
}
