package ru.otus.hw.converters;

import org.junit.jupiter.api.Test;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BookConverterTest {
    private final BookConverter converter = new BookConverter();

    @Test
    void toDto() {
        var author = new Author(3L, "Author 3");
        var genres = List.of(
                new Genre(1L, "Genre 4"),
                new Genre(3L, "Genre 3"),
                new Genre(2L, "Genre 2")
        );

        Book book = new Book()
                .setId(22L)
                .setTitle("title of book 22")
                .setAuthor(author)
                .setGenres(genres);

        var dto = converter.toDto(book);

        assertEquals(book.getId(), dto.getId());

        assertEquals(book.getAuthor().getId(), dto.getAuthor().getId());
        assertEquals(book.getAuthor().getFullName(), dto.getAuthor().getFullName());
        assertThat(dto.getGenres()).containsExactlyInAnyOrder(book.getGenres().toArray(new Genre[0]));
    }

    @Test
    void toDomainObject() {
        long id = 1L;
        var title = "Title";
        var author = new Author(3L, "Author 3");
        var genres = List.of(
                new Genre(1L, "Genre 4"),
                new Genre(3L, "Genre 3"),
                new Genre(2L, "Genre 2")
        );

        var book = converter.toDomainObject(id, title, author, genres);

        assertEquals(id, book.getId());
        assertSame(author, book.getAuthor());
        assertThat(book.getGenres()).containsExactlyInAnyOrder(genres.toArray(new Genre[0]));
    }
}