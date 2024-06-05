package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с жанрами")
@DataJpaTest
class GenreRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GenreRepository repository;

    @DisplayName("должен находить жанр по id")
    @Test
    void find() {
        var genre = new Genre();
        genre.setName("new genre");

        var savedObject = entityManager.persist(genre);
        var actualObject = repository.findById(savedObject.getId());

        assertThat(actualObject)
                .isPresent();

        assertThat(actualObject.get().getName())
                .isEqualTo(genre.getName());
    }

    @DisplayName("должен сохранять жанр")
    @Test
    void save() {
        var genre = entityManager.find(Genre.class, 2L);
        var editedField = genre.getName() + " edited tail";
        genre.setName(editedField);

        var savedObject = repository.save(genre);

        assertThat(savedObject).isNotNull();
        assertThat(savedObject.getName())
                .isEqualTo(editedField);
    }
}