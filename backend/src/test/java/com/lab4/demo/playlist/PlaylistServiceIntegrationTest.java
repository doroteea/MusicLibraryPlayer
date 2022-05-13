package com.lab4.demo.playlist;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.TrackService;
import com.lab4.demo.track.model.TrackDTO;
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

    @Autowired
    private TrackService trackService;

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

        System.out.println(playlistDTO2.getTracks().get(0).getTitle());
        Assertions.assertTrue(playlistDTO2.getTracks().get(0).getTitle().equals(track1.getTitle()));
    }

    @Test
    void editTrackInPlaylist(){
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
        TrackDTO trackDTO = trackService.create(track1);

        PlaylistDTO playlistDTO2 = playlistService.addTrackInPlaylist(playlistDTO1.getId(),trackDTO);

        trackDTO.setTitle("vue");

        PlaylistDTO playlistDTO3 = playlistService.editTrackInPlaylist(playlistDTO2.getId(),trackDTO);

        System.out.println(playlistDTO3.getTracks().get(0).getTitle());
        Assertions.assertTrue(playlistDTO3.getTracks().get(0).getTitle().equals(trackDTO.getTitle()));

    }

    @Test
    void deleteTrackInPlaylist(){
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
        TrackDTO trackDTO = trackService.create(track1);

        PlaylistDTO playlistDTO2 = playlistService.addTrackInPlaylist(playlistDTO1.getId(),trackDTO);
        PlaylistDTO playlistDTO3 = playlistService.deleteTrackFromPlaylist(playlistDTO2.getId(),trackDTO.getId());

        Assertions.assertEquals(0,playlistDTO3.getTracks().size());

    }
}
