package ru.otus.hw.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw.models.Genre;

@Getter
@Setter
@NoArgsConstructor
public class GenreDto {
    private Long id;

    private String name;

    public GenreDto(Genre genre) {
        id = genre.getId();
        name = genre.getName();
    }
}
