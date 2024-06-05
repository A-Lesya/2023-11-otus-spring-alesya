package ru.otus.hw.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw.models.Author;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDto {
    private long id;

    private String fullName;

    public AuthorDto(Author author) {
        id = author.getId();
        fullName = author.getFullName();
    }
}
