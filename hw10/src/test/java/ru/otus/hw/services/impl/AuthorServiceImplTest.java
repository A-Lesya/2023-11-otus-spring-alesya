package ru.otus.hw.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    void findAll() {
        var authors = List.of(new Author(1L, "Author 1"),
                new Author(2L, "Author 2"));
        given(repository.findAll())
                .willReturn(authors);

        var result = service.findAll();
        assertThat(result).containsExactlyInAnyOrder(authors.toArray(new Author[0]));
    }
}