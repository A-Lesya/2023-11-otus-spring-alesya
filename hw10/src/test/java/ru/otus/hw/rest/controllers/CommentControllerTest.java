package ru.otus.hw.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentService service;

    @Test
    void shouldReturnCorrectCommentsListByBookId() throws Exception {
        var bookId = 2L;
        var comments = List.of(
                new Comment()
                        .setId(1)
                        .setText("Comment 1"),
                new Comment()
                        .setId(2)
                        .setText("Comment 2"),
                new Comment()
                        .setId(3)
                        .setText("Comment 3")
        );

        var expectedResult = comments.stream()
                .map(comment -> new CommentDto(comment, bookId))
                .toList();

        given(service.findAllByBookId(bookId)).willReturn(expectedResult);

        mvc.perform(get("/api/comment").param("bookId", String.valueOf(bookId)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }
}