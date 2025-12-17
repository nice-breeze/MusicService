package com.breeze.music;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicServiceIT {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Test
    void getSongById_ValidId_ReturnsSong() throws Exception {
        assertThat(mockMvcTester
                .get()
                .uri("/music/v1/songs/{id}", 1L))
                .hasStatusOk();
    }

    @Test
    void getSongById_NullId_Returns404() {
    }

    @Test
    void getAllSongs_ReturnsAllSongs() {
    }
}
