package ru.otus.hw.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;

import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BookDto {
    private Long id;

    private String title;

    private Author author;

    private List<Genre> genres;
}
