package ru.otus.hw.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final String entity;

    private final List<Long> ids;

    public EntityNotFoundException(String entity, long id) {
        this(entity, List.of(id));
    }

    public EntityNotFoundException(String entity, List<Long> ids) {
        super("%s with id %s not found".formatted(entity, ids));
        this.entity = entity;
        this.ids = ids;
    }
}
