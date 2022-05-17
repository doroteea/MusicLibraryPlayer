package com.lab4.demo.playlist;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.dto.TrackDTO;
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
    void find(){
        PlaylistDTO playlist = PlaylistDTO.builder()
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO playlistDTO = playlistService.create(playlist);
        Playlist playlist1 = playlistService.findById(playlistDTO.getId());

        Assertions.assertEquals(playlistDTO.getTitle(),playlist1.getTitle());
    }

    @Test
    void create(){
        PlaylistDTO playlist = PlaylistDTO.builder()
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO resItem = playlistService.create(playlist);

        Assertions.assertNotNull(resItem);
        Assertions.assertEquals(playlist.getTitle(),resItem.getTitle());
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
        PlaylistDTO playlist = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        PlaylistDTO PlaylistDTO =  playlistService.create(playlist);

        playlistService.delete(PlaylistDTO.getId());
        List<PlaylistDTO> all = playlistService.findAll();

        Assertions.assertEquals(0, all.size());
    }

    @Test
    void addTrack(){
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO playlistDTO1 = playlistService.create(playlistDTO);

        TrackDTO track1 = TrackDTO.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();

        PlaylistDTO playlistDTO2 = playlistService.addTrackInPlaylist(playlistDTO1.getId(),track1);

        Assertions.assertEquals(playlistDTO2.getTracks().get(0).getTitle(), track1.getTitle());
    }

}
