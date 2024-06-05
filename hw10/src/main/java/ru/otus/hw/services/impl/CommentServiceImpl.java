package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id)
                .map(comment -> new CommentDto(comment, null));
    }

    @Override
    public List<CommentDto> findAllByBookId(long bookId) {
        validateBookId(bookId);

        return commentRepository.findByBookId(bookId)
                .stream()
                .map(comment -> new CommentDto(comment, bookId))
                .toList();
    }

    @Transactional
    @Override
    public CommentDto insert(String text, long bookId) {
        validateBookId(bookId);

        var book = new Book().setId(bookId);

        var comment = new Comment()
                .setText(text)
                .setBook(book);

        var savedComment = commentRepository.save(comment);

        return new CommentDto(savedComment, bookId);
    }

    @Transactional
    @Override
    public CommentDto update(long id, String text) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment", id));

        comment.setText(text);

        var savedComment = commentRepository.save(comment);

        return new CommentDto(savedComment, null);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private void validateBookId(long bookId) {
        var isBookExists = bookRepository.existsById(bookId);

        if (!isBookExists) {
            throw new EntityNotFoundException("Book", bookId);
        }
    }
}
