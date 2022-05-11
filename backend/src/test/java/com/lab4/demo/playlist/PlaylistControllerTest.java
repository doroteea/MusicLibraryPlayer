package com.lab4.demo.playlist;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.playlist.model.Playlist;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
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
}