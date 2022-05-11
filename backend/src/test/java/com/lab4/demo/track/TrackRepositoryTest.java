package com.lab4.demo.track;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.track.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrackRepositoryTest {

    @Autowired
    private TrackRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Track trackSaved = repository.save(Track.builder()
                        .id(1L)
                .title("title song")
                .link("link")
                .preview("preview")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name")
                .album("title album")
                .build());
        assertNotNull(trackSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Track.builder().build());
        });
    }

    @Test
    public void testFindAll() {
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
        repository.saveAll(tracks);
        List<Track> all = repository.findAll();
        assertEquals(tracks.size(), all.size());
    }

    @Test
    public void deleteTrack() {
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        repository.save(track1);
        repository.delete(track1);
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void editTrack(){
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        repository.save(track1);
        String oldTitle = track1.getTitle();
        track1.setTitle("new title");
        repository.save(track1);
        assertNotEquals(oldTitle, repository.findById(track1.getId()).get().getTitle());

    }

    @Test
    public void create(){
        Track track1 = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();

        repository.save(track1);
        assertEquals(track1, repository.findById(track1.getId()).get());
    }

    @Test
    void testPaginationFilter(){
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
        repository.saveAll(tracks);
        int page = 0;
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Track> filtered = repository.findAllByTitleLikeOrArtistLikeOrAlbumLike("%g1%","%g1%","%g1%", pageable);
        assertEquals(1, filtered.getTotalElements());
    }



}