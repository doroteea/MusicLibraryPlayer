package com.lab4.demo.playlist;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaylistRepositoryTest {

    @Autowired
    private PlaylistRepository playlistRepository;

    @BeforeEach
    public void beforeEach() {
        playlistRepository.deleteAll();
    }

    @Test
    public void testMock() {
        Playlist playlist = playlistRepository.save(Playlist.builder()
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build());
        assertNotNull(playlist);

        assertThrows(DataIntegrityViolationException.class, () -> {
            playlistRepository.save(Playlist.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Playlist> playlists = TestCreationFactory.listOf(Playlist.class);
        playlistRepository.saveAll(playlists);
        List<Playlist> all = playlistRepository.findAll();
        assertEquals(playlists.size(), all.size());
    }

    @Test
    public void create(){
        Playlist playlist = Playlist.builder()
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        playlistRepository.save(playlist);

        assertEquals(playlist.getTitle(), playlistRepository.findById(playlist.getId()).get().getTitle());
    }

    @Test
    public void update() {
        Playlist playlist = Playlist.builder()
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        playlistRepository.save(playlist);
        String oldTitle = playlist.getTitle();
        playlist.setTitle("new title");
        playlistRepository.save(playlist);
        assertNotEquals(oldTitle, playlistRepository.findById(playlist.getId()).get().getTitle());
    }

    @Test
    public void delete() {
        Playlist playlist = Playlist.builder()
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        playlistRepository.save(playlist);
        playlistRepository.delete(playlist);
        assertEquals(0, playlistRepository.findAll().size());
    }
}