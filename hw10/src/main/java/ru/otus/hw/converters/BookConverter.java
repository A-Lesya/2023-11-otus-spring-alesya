package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BookConverter {
    public BookDto toDto(Book book) {
        var dto = new BookDto();

        var genres = new ArrayList<>(book.getGenres());
        var author = new Author(book.getAuthor().getId(), book.getAuthor().getFullName());

        dto.setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(author)
                .setGenres(genres);

        return dto;
    }

    public Book toDomainObject(long id, String title, Author author, List<Genre> genres) {
        return new Book()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setGenres(genres);
    }
}
