package com.lab4.demo.track;


import com.lab4.demo.book.model.Book;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TrackServiceTest {
    @InjectMocks
    private TrackService trackService;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private TrackMapper trackMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trackService = new TrackService(trackRepository, trackMapper);
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
        when(trackRepository.findAll()).thenReturn(tracks);

        List<TrackDTO> all = trackService.findAll();

        Assertions.assertEquals(tracks.size(), all.size());
    }

    @Test
    void create(){
        TrackDTO track1 = TrackDTO.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackMapper.toDto(trackRepository.save(trackMapper.fromDto(track1)))).thenReturn(track1);
        when(trackService.create(track1)).thenReturn(track1);
        TrackDTO track2 = trackService.create(track1);
        Assertions.assertEquals(track1, track2);
    }
    
    @Test
    void edit(){
        TrackDTO trackDTO = TrackDTO.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        Track track = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackMapper.fromDto(trackDTO)).thenReturn(track);
        when(trackMapper.toDto(track)).thenReturn(trackDTO);

        when(trackRepository.save(track)).thenReturn(track);
        when(trackRepository.findById(track.getId())).thenReturn(Optional.of(track));

        track.setTitle("Edited Title");

        TrackDTO edited = trackService.edit(track.getId(),trackMapper.toDto(track));
        when(trackRepository.save(track)).thenReturn(track);
        Assertions.assertEquals(edited.getId(), track.getId());

    }

    @Test
    void delete(){
        Track track = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackRepository.save(track)).thenReturn(track);
        when(trackRepository.existsById(track.getId())).thenReturn(false);

        trackService.delete(track.getId());
        Assertions.assertTrue(!trackRepository.existsById(track.getId()));
    }

}