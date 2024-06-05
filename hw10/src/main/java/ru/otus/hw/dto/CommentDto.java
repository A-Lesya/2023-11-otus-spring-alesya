package ru.otus.hw.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.otus.hw.models.Comment;

@Getter
@Setter
@Accessors(chain = true)
public class CommentDto {
    private Long id;

    private Long bookId;

    private String text;

    public CommentDto(Comment comment, Long bookId) {
        this.id = comment.getId();
        this.bookId = bookId;
        this.text = comment.getText();
    }
}
