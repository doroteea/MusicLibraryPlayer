package com.lab4.demo.playlist;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class PlaylistServiceTest {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistMapper playlistMapper;

    @Mock PlaylistRepository  playlistRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playlistService = new PlaylistService(playlistRepository,playlistMapper);
    }

    @Test
    void findAll() {
        List<Playlist> playlists = TestCreationFactory.listOf(Playlist.class);
        when(playlistRepository.findAll()).thenReturn(playlists);

        List<PlaylistDTO> all = playlistService.findAll();

        Assertions.assertEquals(playlists.size(), all.size());
    }

    @Test
    void create() {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistMapper.toDTO(playlistRepository.save(playlistMapper.fromDTO(playlistDTO)))).thenReturn(playlistDTO);

        PlaylistDTO playlistDTO1 = playlistService.create(playlistDTO);

        Assertions.assertEquals(playlistDTO,playlistDTO1);
    }

    @Test
    void update() {
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistRepository.findById(playlist.getId())).thenReturn(Optional.of(playlist));
        when(playlistMapper.toDTO(playlistRepository.save(playlist))).thenReturn(playlistDTO);


        PlaylistDTO newPlaylist = playlistService.update(playlist.getId(), playlistDTO);
        Assertions.assertEquals(playlistDTO,newPlaylist);
    }

    @Test
    void delete() {
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistRepository.save(playlist)).thenReturn(playlist);
        when(playlistRepository.existsById(playlist.getId())).thenReturn(false);

        playlistService.delete(playlist.getId());
        Assertions.assertFalse(playlistRepository.existsById(playlist.getId()));
    }
}