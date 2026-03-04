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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    private static final UUID TEST_UUID = UUID.randomUUID();

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicService musicService;

    @Test
    public void getAllSongs_ReturnsAllSongs(){
        Song expectedSongOne = createSong(TEST_UUID);
        Song expectedSongTwo = createSong(UUID.randomUUID());

        when(musicRepository
                .findAll())
                .thenReturn(List.of(expectedSongOne, expectedSongTwo));

        List<Song> testSongs = musicService.getAllSongs();

        assertEquals(2, testSongs.size());
        assertEquals(expectedSongOne.getId(), testSongs.get(0).getId());
        assertEquals(expectedSongTwo.getId(), testSongs.get(1).getId());
    }

    @Test
    public void getSongById_ValidId_ReturnsSong() throws ResourceNotFoundException {
        Song expectedSong = createSong(TEST_UUID);

        when(musicRepository
                .getSongById(TEST_UUID))
                .thenReturn(Optional.of(expectedSong));

        Song testSong = musicService.getSongById(TEST_UUID);

        assertEquals(expectedSong.getId(), testSong.getId());
    }

    @Test
    public void getSongById_MissingResource_ThrowsException(){
        when(musicRepository
                .getSongById(TEST_UUID))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> musicService.getSongById(TEST_UUID));
    }

    @Test
    public void createSong_ValidSong_SongSaved(){
        Song songToSave = createSong(TEST_UUID);
        Song expectedSong = createSong(TEST_UUID);

        when(musicRepository
                .save(songToSave))
                .thenReturn(expectedSong);

        Song savedSong = musicService.createSong(songToSave);

        assertEquals(expectedSong.getId(), savedSong.getId());
    }

    @Test
    public void deleteSong_ValidId_SongDeleted(){
        UUID songToDeleteId = TEST_UUID;

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

    private Song createSong(UUID id){
        return createSong(id, "Test Song", "Test Artist", "https://testurl.com/art.png", "https://testurl.com/song.mp3");

    }

    private Song createSong(UUID id, String songName, String artist, String artwork, String songUrl){
        return new Song(id, songName, artist, artwork, songUrl);
    }
}
