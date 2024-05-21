package ru.otus.hw.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entity, long id) {
        this("%s with id %d not found".formatted(entity, id));
    }
}
