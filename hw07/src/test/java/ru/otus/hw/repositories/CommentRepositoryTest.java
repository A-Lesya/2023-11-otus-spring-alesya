package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с комментариями")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository repository;

    @DisplayName("должен находить комментарий по id")
    @Test
    void find() {
        var newCommentText = "new comment";
        var book = entityManager.find(Book.class, 1L);
        var comment = new Comment()
                .setBook(book)
                .setText(newCommentText);

        var savedComment = entityManager.persist(comment);

        val actualComment = repository.findById(savedComment.getId());

        assertThat(actualComment)
                .isPresent();

        assertThat(actualComment.get().getText())
                .isEqualTo(comment.getText());
    }

    @DisplayName("должен сохранять отредактированный комментарий")
    @Test
    void save() {
        var comment = entityManager.find(Comment.class, 2L);
        var editedText = comment.getText() + " edited comment";
        comment.setText(editedText);

        var savedComment = repository.save(comment);

        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getText())
                .isEqualTo(editedText);
    }

    @DisplayName("должен удалять все комментарии к книге")
    @Test
    void deleteByBookId() {
        val bookId = 2L;

        var expectedCommentsId = computeExpectedCommentsId(bookId);

        repository.deleteByBookId(bookId);

        val allCommentsIdAfter = repository.findAll()
                .stream()
                .map(Comment::getId)
                .toList();

        assertThat(allCommentsIdAfter)
                .containsExactlyInAnyOrder(expectedCommentsId.toArray(Long[]::new));
    }

    private List<Long> computeExpectedCommentsId(long bookId) {
        val bookComments = entityManager.find(Book.class, bookId)
                .getComments()
                .stream()
                .map(Comment::getId)
                .toList();

        assertThat(bookComments)
                .as("Некорректные тестовые данные: необходима книга с комментариями.")
                .isNotEmpty();

        val allCommentsIdBefore = repository.findAll()
                .stream()
                .map(Comment::getId)
                .toList();

        assertThat(allCommentsIdBefore)
                .as("Некорректные тестовые данные: " +
                        "необходимо наличие комментариев к нескольким книгам.")
                .hasSizeGreaterThan(bookComments.size());

        var expectedCommentsId = new ArrayList<>(allCommentsIdBefore);
        expectedCommentsId.removeAll(bookComments);
        return expectedCommentsId;
    }
}