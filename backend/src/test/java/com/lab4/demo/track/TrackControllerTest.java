package com.lab4.demo.track;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.BookController;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import com.lab4.demo.user.UserController;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrackControllerTest extends BaseControllerTest {
    @InjectMocks
    private TrackController controller;

    @Mock
    private TrackService trackService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new TrackController(trackService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

//    @Test
//    void allTracks() throws Exception {
//        List<TrackDTO> tracks;
//
//        tracks = controller.searchTracks("eminem");
//        tracks.forEach(track -> {
//            System.out.println(track.getTitle());
//        });
//
//    }
//
//    @Test
//    void filterBY(){
//        List<TrackDTO> tracks;
//
//        tracks = controller.findAllByFilter("eminem");
//        tracks.forEach(track -> {
//            System.out.println(track.getTitle());
//        });
//    }

    @Test
    void create() throws Exception {
        TrackDTO track1 = TrackDTO.builder()
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackService.create(track1)).thenReturn(track1);
        ResultActions result = performPostWithRequestBody(TRACKS,track1);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(track1));
    }

    @Test
    void update() throws Exception {
        TrackDTO track1 = TrackDTO.builder()
                .id(1L)
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackService.create(track1)).thenReturn(track1);
        when(trackService.edit(track1.getId(),track1)).thenReturn(track1);

        ResultActions result = performPutWithRequestBodyAndPathVariables(TRACKS+TRACK_ID_PART, track1, track1.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(track1));
    }

    @Test
    void delete() throws Exception {
        TrackDTO track = TrackDTO.builder()
                .id(1L)
                .title("title song1")
                .link("link1")
                .preview("preview1")
                .duration(122)
                .explicit_lyrics(true)
                .artist("name1")
                .album("title album1")
                .build();
        when(trackService.create(track)).thenReturn(track);
        doNothing().when(trackService).delete(track.getId());

        ResultActions response = performDeleteWithPathVariable(TRACKS + TRACK_ID_PART, track.getId());
        response.andExpect(status().isOk());
    }


}