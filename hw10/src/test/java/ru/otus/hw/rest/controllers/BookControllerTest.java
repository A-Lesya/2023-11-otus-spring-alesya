package ru.otus.hw.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private final Locale locale = new Locale("en");

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService service;

    @Autowired
    private MessageSource messageSource;

    @Test
    void shouldReturnAllBooksByEmptyTitle() throws Exception {
        var allBooks = List.of(
                new BookDto()
                        .setId(1L)
                        .setTitle("book 1"),
                new BookDto()
                        .setId(2L)
                        .setTitle("book 2"),
                new BookDto()
                        .setId(3L)
                        .setTitle("book 3")
        );

        given(service.findAll()).willReturn(allBooks);

        mvc.perform(get("/api/book").param("title", ""))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(allBooks)));
    }

    @Test
    void shouldReturnCorrectBooksByTitle() throws Exception {
        var title = "tit";
        var booksByTitle = List.of(
                new BookDto()
                        .setId(3L)
                        .setTitle("book 31"),
                new BookDto()
                        .setId(2L)
                        .setTitle("book 2"),
                new BookDto()
                        .setId(1L)
                        .setTitle("book 1")
        );

        given(service.findByTitle(title)).willReturn(booksByTitle);

        mvc.perform(get("/api/book?title=%s".formatted(title)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(booksByTitle)));
    }

    @Test
    void shouldReturnCorrectBookByIdInPath() throws Exception {
        var book = new BookDto()
                .setId(1L)
                .setTitle("book 1");
        given(service.findById(1L)).willReturn(Optional.of(book));

        mvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));
    }

    @Test
    void shouldReturnExpectedErrorWhenBookNotFound() throws Exception {
        given(service.findById(1L)).willReturn(Optional.empty());

        mvc.perform(get("/api/book/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Book with id [1] not found"));
    }

    @Test
    void shouldReturnExpectedErrorWhenTitleNotSpecified() throws Exception {
        var book = new ru.otus.hw.rest.dto.BookDto()
                .setTitle(null)
                .setAuthorId(1L)
                .setGenresId(List.of(1L, 2L));

        checkPostInputValidation(book, "book-title-field-should-not-be-blank");
    }

    @Test
    void shouldReturnExpectedErrorWhenAuthorNotSpecified() throws Exception {
        var book = new ru.otus.hw.rest.dto.BookDto()
                .setTitle("book 1")
                .setAuthorId(null)
                .setGenresId(List.of(1L, 2L));

        checkPostInputValidation(book, "book-author-field-should-not-be-blank");
    }

    @Test
    void shouldReturnExpectedErrorWhenGenreNotSpecified() throws Exception {
        List<Long> genres = new ArrayList<>();
        genres.add(null);
        genres.add(2L);

        var book = new ru.otus.hw.rest.dto.BookDto()
                .setTitle("book 1")
                .setAuthorId(1L)
                .setGenresId(genres);

        checkPostInputValidation(book, "book-genre-field-should-not-be-blank");

        book = new ru.otus.hw.rest.dto.BookDto()
                .setTitle("book 1")
                .setAuthorId(1L)
                .setGenresId(null);

        checkPostInputValidation(book, "book-genre-field-should-not-be-blank");
    }

    @Test
    void shouldCorrectSaveNewBook() throws Exception {
        BookDto book = new BookDto()
                .setId(1L)
                .setTitle("book 1")
                .setAuthor(new Author(1L, "Author 1"))
                .setGenres(List.of(new Genre(1L, "Genre 1"), new Genre(2L, "Genre 2")));

        String title = book.getTitle();
        long authorId = book.getAuthor().getId();
        List<Long> genresIds = book.getGenres().stream().map(Genre::getId).toList();

        ru.otus.hw.rest.dto.BookDto input = new ru.otus.hw.rest.dto.BookDto()
                .setTitle(title)
                .setAuthorId(authorId)
                .setGenresId(genresIds);
        String inputJson = mapper.writeValueAsString(input);

        given(service.insert(title, authorId, new HashSet<>(genresIds))).willReturn(book);

        String expectedResult = mapper.writeValueAsString(book);
        mvc.perform(post("/api/book").contentType(APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    void shouldCorrectUpdateBookFields() throws Exception {
        BookDto book = new BookDto()
                .setId(1L)
                .setTitle("book 1")
                .setAuthor(new Author(1L, "Author 1"))
                .setGenres(List.of(new Genre(1L, "Genre 1"), new Genre(2L, "Genre 2")));
        String title = book.getTitle();
        long authorId = book.getAuthor().getId();
        List<Long> genresIds = book.getGenres().stream().map(Genre::getId).toList();

        ru.otus.hw.rest.dto.BookDto input = new ru.otus.hw.rest.dto.BookDto()
                .setTitle(title)
                .setAuthorId(authorId)
                .setGenresId(genresIds);
        String inputJson = mapper.writeValueAsString(input);
        given(service.update(book.getId(), title, authorId, new HashSet<>(genresIds)))
                .willReturn(book);
        String expectedResult = mapper.writeValueAsString(book);

        mvc.perform(patch("/api/book/1")
                        .contentType(APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mvc.perform(delete("/api/book/1"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(1L);
    }

    private void checkPostInputValidation(ru.otus.hw.rest.dto.BookDto book, String code) throws Exception {
        String bookJson = mapper.writeValueAsString(book);

        var errorMsg = messageSource.getMessage(code, null, locale);

        mvc.perform(post("/api/book")
                        .contentType(APPLICATION_JSON)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(errorMsg)));
    }
}