package com.breeze.music.service;

import com.breeze.music.exception.ResourceNotFoundException;
import com.breeze.music.model.Song;
import com.breeze.music.repository.MusicRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicService musicService;

    @Test
    public void getAllSongs_ReturnsAllSongs(){
        Song expectedSongOne = createSong(1L);
        Song expectedSongTwo = createSong(2L);

        when(musicRepository.findAll()).thenReturn(List.of(expectedSongOne, expectedSongTwo));

        List<Song> testSongs = musicService.getAllSongs();

        assertEquals(2, testSongs.size());
    }

    @Test
    public void getSongById_ValidId_ReturnsSong() throws ResourceNotFoundException {
        Long expectedId = 1L;
        Song expectedSong = createSong(expectedId);

        when(musicRepository
                .getSongById(expectedId))
                .thenReturn(Optional.of(expectedSong));

        Song testSong = musicService.getSongById(expectedId);

        assertEquals(expectedSong.getId(), testSong.getId());
    }

    @Test
    public void getSongById_InvalidId_ThrowsException(){
        Long expectedId = 1L;
        when(musicRepository
                .getSongById(expectedId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> musicService.getSongById(expectedId));
    }

    @Test
    public void createSong_ValidSong_SongSaved(){
        Long expectedId = 1L;
        Song songToSave = createSong(expectedId);
        Song expectedSong = createSong(expectedId);

        when(musicRepository
                .save(songToSave))
                .thenReturn(expectedSong);

        Song savedSong = musicService.createSong(songToSave);

        assertEquals(expectedSong.getId(), savedSong.getId());
    }

    @Test
    public void deleteSong_ValidId_SongDeleted(){
        Long songToDeleteId = createSong(1L).getId();

        musicService.deleteSongById(songToDeleteId);

        verify(musicRepository, Mockito.times(1)).deleteById(songToDeleteId
        );
    }

    @Test
    public void deleteSong_NullId_DeleteSkipped(){
        doThrow(new IllegalArgumentException("Id cannot be null"))
                .when(musicRepository).deleteById(null);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> musicService.deleteSongById(null));
    }

    private Song createSong(Long id){
        return createSong(id, "Test Song", "Test Artist", "https://testurl.com/song.mp3");

    }

    private Song createSong(Long id, String songName, String artist, String songUrl){
        return new Song(id, songName, artist, songUrl);
    }
}
