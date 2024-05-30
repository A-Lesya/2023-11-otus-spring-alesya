package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

@RequiredArgsConstructor
@Component
public class CommentConverter {
    public String commentToString(CommentDto comment) {
        return "Id: %d, text:%s%s".formatted(comment.getId(), System.lineSeparator(), comment.getText());
    }

    public CommentDto convert(Comment comment, Long bookId) {
        return new CommentDto()
                .setId(comment.getId())
                .setBookId(bookId)
                .setText(comment.getText());
    }
}
