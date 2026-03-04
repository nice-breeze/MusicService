package com.breeze.music.controller;

import com.breeze.music.exception.ResourceNotFoundException;
import com.breeze.music.model.Song;
import com.breeze.music.service.MusicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MusicController.class)
class MusicControllerTest {
    private static final UUID TEST_UUID = UUID.randomUUID();

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private MusicService musicService;

    @Test
    void getSongById_ValidId_ReturnsSong() throws ResourceNotFoundException {
        when(musicService.getSongById(TEST_UUID))
                .thenReturn(defaultTestSong(TEST_UUID));

        assertThat(mockMvcTester
                .get()
                .uri("/music/v1/songs/{id}", TEST_UUID))
                .hasStatusOk()
                .bodyJson()
                .convertTo(Song.class)
                .satisfies(response ->
                       assertThat(response.getId()).isEqualTo(TEST_UUID));
    }

    @Test
    void getSongById_NullId_ReturnsSong() throws Exception {
        when(musicService.getSongById(TEST_UUID)).thenThrow(ResourceNotFoundException.class);

        assertThat(mockMvcTester
                .get()
                .uri("/music/v1/songs/{id}", TEST_UUID))
                .hasStatus(HttpStatus.NOT_FOUND.value())
                .hasFailed();
    }

    @Test
    void getAllSongs_ReturnsAllSongs() {
        List<Song> testSongs = List.of(defaultTestSong(TEST_UUID));
        when(musicService.getAllSongs())
                .thenReturn(testSongs);

        assertThat(mockMvcTester
                .get()
                .uri("/music/v1/songs"))
                .hasStatusOk()
                .bodyJson()
                .convertTo(List.class)
                .satisfies(response ->
                        assertThat(response.size()).isEqualTo(1));
    }

    @Test
    void createSong_ValidSong_SongSaved() throws JsonProcessingException {
        Song songToSave = defaultTestSong(TEST_UUID);

        String jsonSong = "{\"songName\":\"Test Song\",\"artist\":\"Test Artist\",\"artwork\":\"https://testurl.com/art.png\", \"songUrl\":\"https://testurl.com/song.mp3\"}";

        when(musicService.createSong(any(Song.class)))
                .thenReturn(songToSave);

        assertThat(mockMvcTester
                .post()
                .content(jsonSong)
                .contentType(MediaType.APPLICATION_JSON)
                .uri("/music/v1/songs"))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .convertTo(Song.class)
                .satisfies(response ->
                        assertThat(response.getId()).isEqualTo(TEST_UUID));

    }

    @Test
    void createSong_InvalidId_Returns500() {
        String songToSave = "{\"id\":123, \"songName\":\"Test Song\",\"artist\":\"Test Artist\",\"artwork\":\"https://testurl.com/art.png\", \"songUrl\":\"https://testurl.com/song.mp3\"}";

        assertThat(mockMvcTester
                .post()
                .content(songToSave)
                .contentType(MediaType.APPLICATION_JSON)
                .uri("/music/v1/songs"))
                .hasStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .hasFailed()
                .failure();

    }

    @Test
    void createSong_InvalidSongName_Returns400() {
        String songToSave = "{\"songTitle\":\"Test Song\",\"artist\":\"Test Artist\", \"artwork\":\"https://testurl.com/art.png\", \"songUrl\":\"https://testurl.com/song.mp3\"}";

        assertThat(mockMvcTester
                .post()
                .content(songToSave)
                .contentType(MediaType.APPLICATION_JSON)
                .uri("/music/v1/songs"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasFailed()
                .failure();

    }

    @Test
    void createSong_InvalidArtist_Returns400() {
        String songToSave = "{\"songName\":\"Test Song\",\"person\":\"Test Artist\",\"artwork\":\"https://testurl.com/art.png\", \"songUrl\":\"https://testurl.com/song.mp3\"}";

        assertThat(mockMvcTester
                .post()
                .content(songToSave)
                .contentType(MediaType.APPLICATION_JSON)
                .uri("/music/v1/songs"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasFailed()
                .failure();

    }

    @Test
    void createSong_InvalidArtworkUrl_Returns400() {
        String songToSave = "{\"songName\":\"Test Song\",\"artist\":\"Test Artist\", \"artwork\":\"Test Artist\", \"songUrl\":\"htt://testurl.com/song.mp3\"}";

        assertThat(mockMvcTester
                .post()
                .content(songToSave)
                .contentType(MediaType.APPLICATION_JSON)
                .uri("/music/v1/songs"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasFailed()
                .failure();

    }



    @Test
    void createSong_InvalidSongUrl_Returns400() {
        String songToSave = "{\"songName\":\"Test Song\",\"artist\":\"Test Artist\", \"artwork\":\"https://testurl.com/art.png\", \"songUrl\":\"htt://testurl.com/song.mp3\"}";

        assertThat(mockMvcTester
                .post()
                .content(songToSave)
                .contentType(MediaType.APPLICATION_JSON)
                .uri("/music/v1/songs"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasFailed()
                .failure();

    }

    @Test
    void deleteSong_ValidId_SongDeleted() throws Exception {
        assertThat(mockMvcTester
                .delete()
                .uri("/music/v1/songs/{id}", TEST_UUID))
                .hasStatusOk()
                .hasBodyTextEqualTo(String.format("Song id: %s deleted successfully", TEST_UUID));
    }

    private Song defaultTestSong(UUID id){
        return createSong(id, "Test Song", "Test Artist", "https://testurl.com/artwork.png", "https://testurl.com/song.mp3");
    }

    private Song createSong(UUID id, String songName, String artist, String artwork, String songUrl){
        return new Song(id, songName, artist, artwork, songUrl);
    }
}