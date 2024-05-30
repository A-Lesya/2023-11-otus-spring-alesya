package ru.otus.hw.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BookDto {
    @NotBlank(message = "{book-title-field-should-not-be-blank}")
    private String title;

    @NotNull(message = "{book-author-field-should-not-be-blank}")
    private Long authorId;

    @NotNull
    @NotEmpty(message = "{book-genre-field-should-not-be-blank}")
    private List<@NotNull(message = "{book-genre-field-should-not-be-blank}") Long> genresId;
}
