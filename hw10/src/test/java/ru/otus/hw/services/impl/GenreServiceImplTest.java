package ru.otus.hw.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl service;

    @Test
    void findAll() {
        var genres = List.of(new Genre(1L, "Genre 1"),
                new Genre(2L, "Genre 2"),
                new Genre(3L, "Genre 3")
        );

        given(genreRepository.findAll())
                .willReturn(genres);

        var result = service.findAll();
        assertThat(result)
                .containsExactlyInAnyOrder(genres.toArray(new Genre[0]));
    }
}