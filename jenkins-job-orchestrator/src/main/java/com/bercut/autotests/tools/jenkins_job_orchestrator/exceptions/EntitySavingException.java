package com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions;

import lombok.Getter;

@Getter
public class EntitySavingException extends RuntimeException {
    private final String entity;

    private String key;

    public EntitySavingException(String entity, String key, Throwable cause) {
        super("%s with key %s already exists".formatted(entity, key), cause);
        this.entity = entity;
        this.key = key;
    }
}
