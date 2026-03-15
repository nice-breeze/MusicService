package com.breeze.music;

import com.breeze.music.model.Song;
import com.breeze.music.repository.MusicRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicServiceIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Autowired
    private MusicRepository musicRepository;

    @LocalServerPort
    private Integer port;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        musicRepository.deleteAll();
    }

    @Test
    void getSongById_ValidId_ReturnsSong() throws Exception {
        Song saved = musicRepository.save(
                new Song(null, "Test Song", "Test Artist", null, "https://example.com/song.mp3"));

        given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/music/v1/songs/{id}", saved.getId())
                .then()
                    .statusCode(200);

    }

    @Test
    void getSongById_NullId_Returns404() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/music/v1/songs/{id}", (UUID) null)
                .then()
                    .statusCode(404);
    }

    @Test
    void getAllSongs_ReturnsAllSongs() throws Exception {
        musicRepository.save(new Song(null, "Song A", "Artist A", null, "https://example.com/a.mp3"));
        musicRepository.save(new Song(null, "Song B", "Artist B", null, "https://example.com/b.mp3"));

        given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/music/v1/songs")
                .then()
                    .statusCode(200);
    }
}
