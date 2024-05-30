package ru.otus.hw.rest.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.BookService;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/book")
    public List<BookDto> getBookByTitle(@RequestParam String title) {
        if (title == null || title.isEmpty()) {
            return bookService.findAll();
        }
        return bookService.findByTitle(title);
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        return bookService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book", id));
    }

    @PostMapping("/api/book")
    public BookDto addBook(@Valid @RequestBody ru.otus.hw.rest.dto.BookDto book) {
        var title = book.getTitle();
        var authorId = book.getAuthorId();
        var genresIds = new HashSet<>(book.getGenresId());

        return bookService.insert(title, authorId, genresIds);
    }


    @PatchMapping("/api/book/{id}")
    public BookDto editBook(@PathVariable long id, @Valid @RequestBody ru.otus.hw.rest.dto.BookDto book) {
        var title = book.getTitle();
        var authorId = book.getAuthorId();
        var genresIds = new HashSet<>(book.getGenresId());

        return bookService.update(id, title, authorId, genresIds);
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}
