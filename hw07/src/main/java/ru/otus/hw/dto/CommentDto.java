package ru.otus.hw.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CommentDto {
    private Long id;

    private Long bookId;

    private String text;
}
