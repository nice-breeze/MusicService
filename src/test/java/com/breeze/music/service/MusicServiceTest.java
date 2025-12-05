package com.breeze.music.service;

import com.breeze.music.repository.MusicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicService musicService;

    @Test
    public void getSongById(){

    }

}
