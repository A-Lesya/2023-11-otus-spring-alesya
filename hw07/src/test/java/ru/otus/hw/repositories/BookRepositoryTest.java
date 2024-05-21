package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с книгами")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository repository;

    @DisplayName("должен находить книгу по id")
    @Test
    void find() {
        val optionalActualBook = repository.findById(1L);
        val expectedBook = entityManager.find(Book.class, 1L);

        assertThat(optionalActualBook)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен сохранять книгу")
    @Test
    void save() {
        val book = entityManager.find(Book.class, 1L);

        var newTitle = "new tittle";
        book.setTitle(newTitle);
        repository.save(book);

        val savedObject = entityManager.find(Book.class, book.getId());
        assertThat(savedObject)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", newTitle);
    }
}