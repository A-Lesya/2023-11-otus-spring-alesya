package ru.otus.hw.rest.exception_handles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.hw.exceptions.EntityNotFoundException;

@ControllerAdvice
public class EntityAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> accessError(EntityNotFoundException e) {
        String message = "%s with id %s not found".formatted(e.getEntity(), e.getIds());
        return ResponseEntity.badRequest().body(message);
    }
}
