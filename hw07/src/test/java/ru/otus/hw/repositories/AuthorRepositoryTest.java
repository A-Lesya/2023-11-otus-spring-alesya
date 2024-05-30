package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с авторами")
@DataJpaTest
class AuthorRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorRepository repository;

    @DisplayName("должен находить автора по id")
    @Test
    void find() {
        var author = new Author();
        author.setFullName("new author");

        var savedObject = entityManager.persist(author);
        var actualObject = repository.findById(savedObject.getId());

        assertThat(actualObject)
                .isPresent();

        assertThat(actualObject.get().getFullName())
                .isEqualTo(author.getFullName());
    }

    @DisplayName("должен сохранять автора")
    @Test
    void save() {
        var author = entityManager.find(Author.class, 2L);
        var editedField = author.getFullName() + " edited tail";
        author.setFullName(editedField);

        var savedObject = repository.save(author);

        assertThat(savedObject).isNotNull();
        assertThat(savedObject.getFullName())
                .isEqualTo(editedField);
    }
}