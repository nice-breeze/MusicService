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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MusicController.class)
class MusicControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private MusicService musicService;

    @Test
    void getSongById_ValidId_ReturnsSong() throws Exception {

        when(musicService.getSongById(1L))
                .thenReturn(defaultTestSong(1L));

        assertThat(mockMvcTester
                .get()
                .uri("/music/v1/songs/{id}", 1L))
                .hasStatusOk()
                .bodyJson()
                .convertTo(Song.class)
                .satisfies(response ->
                       assertThat(response.getId()).isEqualTo(1L));
    }

    @Test
    void getSongById_NullId_Returns404() throws Exception {
        when(musicService.getSongById(0L)).thenThrow(ResourceNotFoundException.class);

        assertThat(mockMvcTester
                .get()
                .uri("/music/v1/songs/{id}", 0L))
                .hasStatus(HttpStatus.NOT_FOUND.value())
                .hasFailed();
    }

    @Test
    void getAllSongs_ReturnsAllSongs() {
        List<Song> testSongs = List.of(defaultTestSong(1L));
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
        Song songToSave = defaultTestSong(1L);

        String jsonSong = "{\"id\":1,\"songName\":\"Test Song\",\"artist\":\"Test Artist\",\"songUrl\":\"https://testurl.com/song.mp3\"}";

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
                        assertThat(response.getId()).isEqualTo(1L));

    }

    @Test
    void deleteSong_ValidId_SongDeleted() throws Exception {
        Long songToDeleteId = 1L;

        assertThat(mockMvcTester
                .delete()
                .uri("/music/v1/song/{id}", songToDeleteId))
                .hasStatusOk()
                .hasBodyTextEqualTo(String.format("Song id: %d deleted successfully", songToDeleteId));
    }

    private Song defaultTestSong(Long id){
        return createSong(id, "Test Song", "Test Artist", "https://testurl.com/song.mp3");
    }

    private Song createSong(Long id, String songName, String artist, String songUrl){
        return new Song(id, songName, artist, songUrl);
    }
}