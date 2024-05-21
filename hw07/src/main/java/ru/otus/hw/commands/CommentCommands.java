package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final BookService bookService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(long id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse(getCommentNotFountMsg(id));
    }

    @ShellMethod(value = "Find comments by book id", key = "cbbid")
    public String findCommentsByBookId(long bookId) {
        List<CommentDto> comments;
        try {
            comments = commentService.findAllByBookId(bookId);
        } catch (EntityNotFoundException e) {
            return "Book with id %d not found".formatted(bookId);
        }

        if (comments.isEmpty()) {
            return "Book with id %d has no comments yet".formatted(bookId);
        }

        return comments.stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    // cins 2 newComment
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(long bookId, String text) {
        try {
            var savedComment = commentService.insert(text, bookId);
            return commentConverter.commentToString(savedComment);
        } catch (EntityNotFoundException e) {
            log.info("failed attempt to add the comment", e);
            return "Book with id %d not found".formatted(bookId);
        }
    }

    // cupd 4 editedComment
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(long id, String text) {
        try {
            var savedComment = commentService.update(id, text);
            return commentConverter.commentToString(savedComment);
        } catch (EntityNotFoundException e) {
            log.info("failed attempt to update the comment", e);
            return getCommentNotFountMsg(id);
        }
    }

    // cdel 4
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteComment(long id) {
        commentService.deleteById(id);
    }

    private String getCommentNotFountMsg(long id) {
        return "Comment with id %d not found".formatted(id);
    }
}
