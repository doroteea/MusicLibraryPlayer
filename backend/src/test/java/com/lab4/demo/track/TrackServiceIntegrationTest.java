package com.lab4.demo.track;

import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TrackServiceIntegrationTest {
    @Autowired
    private TrackService trackService;

    @Autowired
    private TrackRepository trackRepository;

    @BeforeEach
    void setUp() {
        trackRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Track> tracks = new ArrayList<>();
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        Track track2 = Track.builder()
                .title("title song2")
                .link("link2")
                .preview("preview2")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build();
        tracks.add(track1);
        tracks.add(track2);
        trackRepository.saveAll(tracks);

        List<TrackDTO> all = trackService.findAll();

        Assertions.assertEquals(tracks.size(), all.size());
    }

    @Test
    void createTest(){
        TrackDTO track = TrackDTO.builder()
                .title("title song2")
                .link("link2")
                .preview("preview2")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build();
        TrackDTO resTrack = trackService.create(track);
        Assertions.assertNotNull(resTrack);
        Assertions.assertEquals(track.getTitle(),resTrack.getTitle());
    }

    @Test
    void filter(){
        List<Track> tracks = new ArrayList<>();
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        Track track2 = Track.builder()
                .title("title song2")
                .link("link2")
                .preview("preview2")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build();
        tracks.add(track1);
        tracks.add(track2);
        trackRepository.saveAll(tracks);

        List<TrackDTO> all = trackService.findAllByFilter("%g1%");

        Assertions.assertEquals(1, all.size());
    }

    @Test
    void editTest(){
        TrackDTO track = TrackDTO.builder()
                .title("title song2")
                .link("link2")
                .preview("preview2")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build();
        TrackDTO trackDTO =  trackService.create(track);
        trackDTO.setTitle("Mica printesa");
        TrackDTO resTrack = trackService.edit(trackDTO.getId(),trackDTO);
        Assertions.assertNotNull(resTrack);
        Assertions.assertEquals(trackDTO.getTitle(),resTrack.getTitle());
    }

    @Test
    void deleteTest(){
        TrackDTO track = TrackDTO.builder()
                .title("title song2")
                .link("link2")
                .preview("preview2")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build();
        TrackDTO trackDTO =  trackService.create(track);
        trackService.delete(trackDTO.getId());
        List<TrackDTO> all = trackService.findAll();
        Assertions.assertEquals(0, all.size());
    }
}
