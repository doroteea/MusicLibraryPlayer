package com.lab4.demo.playlist;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.PLAYLIST;
import static com.lab4.demo.UrlMapping.PLAYLIST_ID_PART;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlaylistControllerTest extends BaseControllerTest {

    @InjectMocks
    private PlaylistController playlistController;

    @Mock
    private PlaylistService playlistService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        playlistController = new PlaylistController(playlistService);
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
    }

    @Test
    void getAllPlaylists() throws Exception {
        List<PlaylistDTO> playlistDTOS = TestCreationFactory.listOf(Playlist.class);
        when(playlistService.findAll()).thenReturn(playlistDTOS);

        ResultActions response = performGet(PLAYLIST);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(playlistDTOS));
    }

    @Test
    void create() throws Exception {
        PlaylistDTO playlist = PlaylistDTO.builder()
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistService.create(playlist)).thenReturn(playlist);

        ResultActions result = performPostWithRequestBody(PLAYLIST, playlist);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(playlist));
    }

    @Test
    void update() throws Exception {
        long id = randomLong();
        PlaylistDTO playlist = PlaylistDTO.builder()
                .id(id)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistService.update(id, playlist)).thenReturn(playlist);

        ResultActions result = performPutWithRequestBodyAndPathVariables(PLAYLIST + PLAYLIST_ID_PART, playlist, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(playlist));
    }

    @Test
    void delete() throws Exception {
        PlaylistDTO playlist = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();

        when(playlistService.create(playlist)).thenReturn(playlist);
        doNothing().when(playlistService).delete(playlist.getId());

        ResultActions response = performDeleteWithPathVariable(PLAYLIST + PLAYLIST_ID_PART, playlist.getId());
        response.andExpect(status().isOk());
    }

    @Test
    void addTrack() throws Exception {
        Playlist playlist = Playlist.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        PlaylistDTO playlistDTO = PlaylistDTO.builder()
                .id(1L)
                .title("Playlist")
                .tracks(new ArrayList<>())
                .duration(100)
                .build();
        when(playlistService.create(playlistDTO)).thenReturn(playlistDTO);
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
        Track track = Track.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        playlistDTO1.getTracks().add(track1);

        when(playlistService.addTrackInPlaylist(playlistDTO1.getId(),track1)).thenReturn(playlistDTO1);

        ResultActions result = performPutWithRequestBodyAndPathVariables(PLAYLIST + PLAYLIST_ID_PART + "/tracks", track1, playlistDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(playlistDTO1));

    }
}