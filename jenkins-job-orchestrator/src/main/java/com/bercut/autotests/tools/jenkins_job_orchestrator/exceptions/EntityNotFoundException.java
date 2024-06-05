package com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final String entity;

    private final List<String> ids;

    public EntityNotFoundException(String entity, String id) {
        this(entity, List.of(id));
    }

    public EntityNotFoundException(String entity, long id) {
        this(entity, List.of(String.valueOf(id)));
    }

    public EntityNotFoundException(String entity, List<String> ids) {
        super("%s with id %s not found".formatted(entity, ids));
        this.entity = entity;
        this.ids = ids;
    }
}
