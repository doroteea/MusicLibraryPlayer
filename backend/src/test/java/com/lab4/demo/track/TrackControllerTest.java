package com.lab4.demo.track;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.BookController;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.user.UserController;
import com.lab4.demo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.UrlMapping.BOOKS;
import static com.lab4.demo.UrlMapping.TRACKS;
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

    @Test
    void allBooks() throws Exception {
        List<Track> tracks = new ArrayList<>();

        tracks = controller.searchTracks("ariana");
        tracks.forEach(track -> {
            System.out.println(track.getTitle());
        });

    }

}
