package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.hw.models.Book;

import java.util.List;

public interface BookRepository extends ListCrudRepository<Book, Long> {
    @Nonnull
    @EntityGraph(attributePaths = "author")
    List<Book> findAll();
}
