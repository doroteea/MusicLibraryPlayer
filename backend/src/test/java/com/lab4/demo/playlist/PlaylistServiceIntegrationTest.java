package com.lab4.demo.playlist;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PlaylistServiceIntegrationTest {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistRepository playlistRepository;

    @BeforeEach
    void setUp() {
        playlistRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Playlist> playlists = TestCreationFactory.listOf(Playlist.class);
        playlistRepository.saveAll(playlists);

        List<PlaylistDTO> all = playlistService.findAll();

        Assertions.assertEquals(playlists.size(), all.size());
    }

    @Test
    void create(){
        PlaylistDTO reqItem = PlaylistDTO.builder()
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO resItem = playlistService.create(reqItem);

        Assertions.assertNotNull(resItem);
        Assertions.assertEquals(reqItem.getTitle(),resItem.getTitle());
    }

    @Test
    void update(){
        PlaylistDTO playlist = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        PlaylistDTO playlistDTO =  playlistService.create(playlist);

        playlistDTO.setTitle("Another Playlist");
        PlaylistDTO playlistDTO1 = playlistService.update(playlistDTO.getId(),playlistDTO);

        Assertions.assertNotNull(playlistDTO1);
        Assertions.assertEquals(playlistDTO.getTitle(),playlistDTO1.getTitle());
    }

    @Test
    void delete(){
        PlaylistDTO req = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        PlaylistDTO PlaylistDTO =  playlistService.create(req);

        playlistService.delete(PlaylistDTO.getId());
        List<PlaylistDTO> all = playlistService.findAll();

        Assertions.assertEquals(0, all.size());
    }
}
