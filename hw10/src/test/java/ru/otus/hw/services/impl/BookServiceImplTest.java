package ru.otus.hw.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookConverter bookConverter;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    void findById() {
        var book = createBook(1L);
        var dto = createDto(book);

        given(bookRepository.findById(eq(1L)))
                .willReturn(Optional.of(book));

        given(bookConverter.toDto(book))
                .willReturn(dto);

        var result = service.findById(1L);
        assertThat(result)
                .isNotEmpty()
                .get()
                .isSameAs(dto);
    }


    @Test
    void findAll() {
        var books = List.of(createBook(1L),
                createBook(2L),
                createBook(3L)
        );

        var expectedDtoList = buildDtoAndPrepareConverterMock(books);

        given(bookRepository.findAll())
                .willReturn(books);

        var result = service.findAll();
        assertThat(result)
                .containsExactlyInAnyOrder(expectedDtoList.toArray(new BookDto[0]));
    }

    @Test
    void findByTitle() {
        var title = "title";

        var books = List.of(createBook(1L),
                createBook(2L),
                createBook(3L)
        );

        var expectedDtoList = buildDtoAndPrepareConverterMock(books);

        given(bookRepository.findByTitleContainingIgnoreCase(eq(title)))
                .willReturn(books);

        var result = service.findByTitle(title);
        assertThat(result)
                .containsExactlyInAnyOrder(expectedDtoList.toArray(new BookDto[0]));
    }

    @Test
    void insertMethodShouldThrowExceptionIfAuthorIdNotExists() {
        String title = "title";
        long authorId = 1;
        Set<Long> genresIds = Set.of(1L, 2L);

        given(authorRepository.findById(authorId)).willReturn(Optional.empty());

        var exception = catchThrowableOfType(() -> service.insert(title, authorId, genresIds),
                EntityNotFoundException.class);

        assertThat(exception.getEntity()).isEqualTo("Author");
        assertThat(exception.getIds()).containsExactlyInAnyOrder(authorId);
    }

    @Test
    void insertMethodShouldThrowExceptionIfGenreIdNotExists() {
        String title = "title";
        long authorId = 1;
        Set<Long> genresIds = Set.of(1L, 2L);
        List<Genre> genres = List.of(new Genre(2L, "Genre 2"));

        given(authorRepository.findById(authorId)).willReturn(Optional.of(new Author()));
        given(genreRepository.findAllById(genresIds)).willReturn(genres);

        var exception = catchThrowableOfType(() -> service.insert(title, authorId, genresIds),
                EntityNotFoundException.class);

        assertThat(exception.getEntity()).isEqualTo("Genre");
        assertThat(exception.getIds()).containsExactlyInAnyOrder(1L);
    }

    @Test
    void shouldCorrectlyInsertNewBook() {
        String title = "title";
        long authorId = 1;
        Set<Long> genresIds = Set.of(1L, 2L);
        List<Genre> genres = List.of(new Genre(2L, "Genre 2"), new Genre(1L, "Genre 1"));
        var author = new Author(authorId, "Author N");

        var book = new Book()
                .setId(0)
                .setTitle(title)
                .setAuthor(author)
                .setGenres(genres);

        var dto = createDto(book);

        given(authorRepository.findById(authorId)).willReturn(Optional.of(author));
        given(genreRepository.findAllById(genresIds)).willReturn(genres);
        given(bookRepository.save(book)).willReturn(book);
        given(bookConverter.toDto(book)).willReturn(dto);
        given(bookConverter.toDomainObject(eq(0L), eq(title), eq(author), eq(genres))).willReturn(book);
        var result = service.insert(title, authorId, genresIds);
        assertThat(result).isSameAs(dto);
    }

    @Test
    void updateMethodShouldThrowExceptionIfAuthorIdNotExists() {
        String title = "title";
        long authorId = 1;
        Set<Long> genresIds = Set.of(1L, 2L);

        given(authorRepository.findById(authorId)).willReturn(Optional.empty());

        var exception = catchThrowableOfType(() -> service.update(1L, title, authorId, genresIds),
                EntityNotFoundException.class);

        assertThat(exception.getEntity()).isEqualTo("Author");
        assertThat(exception.getIds()).containsExactlyInAnyOrder(authorId);
    }

    @Test
    void updateMethodShouldThrowExceptionIfGenreIdNotExists() {
        String title = "title";
        long authorId = 1;
        Set<Long> genresIds = Set.of(1L, 2L);
        List<Genre> genres = List.of(new Genre(2L, "Genre 2"));

        given(authorRepository.findById(authorId)).willReturn(Optional.of(new Author()));
        given(genreRepository.findAllById(genresIds)).willReturn(genres);

        var exception = catchThrowableOfType(() -> service.update(1L, title, authorId, genresIds),
                EntityNotFoundException.class);

        assertThat(exception.getEntity()).isEqualTo("Genre");
        assertThat(exception.getIds()).containsExactlyInAnyOrder(1L);
    }

    @Test
    void shouldCorrectlyUpdateBook() {
        var bookId = 1L;
        String title = "title";
        long authorId = 1;
        Set<Long> genresIds = Set.of(1L, 2L);
        List<Genre> genres = List.of(new Genre(2L, "Genre 2"), new Genre(1L, "Genre 1"));
        var author = new Author(authorId, "Author N");

        var book = new Book()
                .setId(bookId)
                .setTitle(title)
                .setAuthor(author)
                .setGenres(genres);
        var dto = createDto(book);

        given(authorRepository.findById(authorId)).willReturn(Optional.of(author));
        given(genreRepository.findAllById(genresIds)).willReturn(genres);
        given(bookRepository.save(book)).willReturn(book);
        given(bookConverter.toDto(book)).willReturn(dto);
        given(bookConverter.toDomainObject(eq(bookId), eq(title), eq(author), eq(genres))).willReturn(book);

        var result = service.update(bookId, title, authorId, genresIds);
        assertThat(result).isSameAs(dto);
    }

    @Test
    void shouldCorrectDeleteBook() {
        service.deleteById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
        verify(commentRepository, times(1)).deleteByBookId(1L);
    }

    private Book createBook(long id) {
        var author = new Author(3L, "Author 3");
        var genres = List.of(
                new Genre(1L, "Genre 4"),
                new Genre(3L, "Genre 3"),
                new Genre(2L, "Genre 2")
        );

        return new Book()
                .setId(id)
                .setTitle("title of book 22")
                .setAuthor(author)
                .setGenres(genres);
    }

    private BookDto createDto(Book book) {
        return new BookDto()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setGenres(book.getGenres());
    }

    private List<BookDto> buildDtoAndPrepareConverterMock(List<Book> books) {
        var expectedDtoList = new LinkedList<BookDto>();

        books.forEach(book -> {
            var dto = createDto(book);

            given(bookConverter.toDto(book))
                    .willReturn(dto);

            expectedDtoList.add(dto);
        });

        return expectedDtoList;
    }
}