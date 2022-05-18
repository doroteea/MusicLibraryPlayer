package com.lab4.demo.comment;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.comment.model.Comment;
import com.lab4.demo.comment.model.dto.CommentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.COMMENTS;
import static com.lab4.demo.UrlMapping.COMMENT_ID_PART;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest extends BaseControllerTest {
    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        commentController = new CommentController(commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getAllComments() throws Exception {
        List<CommentDTO> commentDTOS = TestCreationFactory.listOf(Comment.class);
        when(commentService.findAll()).thenReturn(commentDTOS);

        ResultActions response = performGet(COMMENTS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(commentDTOS));
    }

    @Test
    void create() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        when(commentService.save(commentDTO)).thenReturn(commentDTO);

        ResultActions result = performPostWithRequestBody(COMMENTS, commentDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(commentDTO));
    }

    @Test
    void update() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();
        String id = "1L";
        when(commentService.update(id, commentDTO)).thenReturn(commentDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariables(COMMENTS + COMMENT_ID_PART, commentDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(commentDTO));
    }

    @Test
    void delete() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .id("1L")
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        when(commentService.save(commentDTO)).thenReturn(commentDTO);
        doNothing().when(commentService).delete(commentDTO.getId());

        ResultActions response = performDeleteWithPathVariable(COMMENTS + COMMENT_ID_PART, commentDTO.getId());
        response.andExpect(status().isOk());
    }
}