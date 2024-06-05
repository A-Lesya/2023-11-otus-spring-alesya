package ru.otus.hw.services.impl;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CommentServiceImpl service;

    @Test
    void findById() {
        var comment = new Comment()
                .setId(1L)
                .setText("Comment");

        given(commentRepository.findById(eq(comment.getId())))
                .willReturn(Optional.of(comment));

        var result = service.findById(comment.getId());
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId())
                .isEqualTo(comment.getId());
        assertThat(result.get().getText())
                .isEqualTo(comment.getText());
    }

    @Test
    void findAllByBookIdMethodThrowsExceptionIfBookNotExists() {
        var bookId = 1L;

        given(bookRepository.existsById(eq(bookId))).willReturn(false);
        var exception = catchThrowableOfType(() -> service.findAllByBookId(bookId),
                EntityNotFoundException.class);

        assertThat(exception.getEntity()).isEqualTo("Book");
        assertThat(exception.getIds()).containsExactlyInAnyOrder(bookId);
    }

    @Test
    void findAllByBookId() {
        var bookId = 1L;
        var comments = List.of(
                new Comment().setId(1L).setText("comment 1"),
                new Comment().setId(1L).setText("comment 1"),
                new Comment().setId(1L).setText("comment 1")
        );

        given(commentRepository.findByBookId(bookId))
                .willReturn(comments);
        given(bookRepository.existsById(eq(bookId))).willReturn(true);

        var result = service.findAllByBookId(bookId);

        Tuple[] tuples = comments.stream()
                .map(comment -> new Tuple(comment.getId(), comment.getText()))
                .toArray(Tuple[]::new);

        assertThat(result)
                .extracting(CommentDto::getId, CommentDto::getText)
                .containsExactlyInAnyOrder(tuples);
    }

    @Test
    void insertMethodThrowsExceptionIfBookNotExists() {
        var bookId = 1L;
        var text = "Comment.";

        given(bookRepository.existsById(eq(bookId))).willReturn(false);

        var exception = catchThrowableOfType(() -> service.insert(text, bookId),
                EntityNotFoundException.class);

        assertThat(exception.getEntity()).isEqualTo("Book");
        assertThat(exception.getIds()).containsExactlyInAnyOrder(bookId);
    }

    @Test
    void shouldCorrectlyInsertNewComment() {
        var bookId = 1L;
        var text = "Comment.";

        var book = new Book().setId(bookId);
        var expectedComment = new Comment()
                .setText(text)
                .setBook(book);

        given(bookRepository.existsById(eq(bookId))).willReturn(true);
        given(commentRepository.save(argThat((Comment comment) ->
                                Objects.equals(comment.getText(), text) &&
                                        comment.getBook() != null &&
                                        comment.getBook().getId() == bookId
                        )
                )
        )
                .willReturn(expectedComment);

        var result = service.insert(text, bookId);

        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo(text);
        assertThat(result.getBookId()).isEqualTo(bookId);
    }

    @Test
    void updateMethodThrowsExceptionIfCommentNotExists() {
        var id = 1L;
        var text = "Comment.";

        given(commentRepository.findById(eq(id))).willReturn(Optional.empty());

        var exception = catchThrowableOfType(() -> service.update(id, text),
                EntityNotFoundException.class);

        assertThat(exception.getEntity()).isEqualTo("Comment");
        assertThat(exception.getIds()).containsExactlyInAnyOrder(id);
    }

    @Test
    void shouldCorrectlyUpdateComment() {
        var id = 1L;
        var text = "Comment.";
        var editedText = "Edited comment.";

        var comment = new Comment().setId(id).setText(text);

        var expectedComment = new Comment().setId(id).setText(editedText);

        given(commentRepository.findById(eq(id))).willReturn(Optional.of(comment));
        given(commentRepository.save(argThat((Comment editedComment) ->
                                Objects.equals(editedComment.getText(), editedText) &&
                                        editedComment.getId() == id
                        )
                )
        )
                .willReturn(expectedComment);

        var result = service.update(id, editedText);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expectedComment.getId());
        assertThat(result.getText()).isEqualTo(expectedComment.getText());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(commentRepository, times(1)).deleteById(1L);
    }
}