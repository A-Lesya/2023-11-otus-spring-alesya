package com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions;

import lombok.Getter;

@Getter
public class EntityDeletingException extends RuntimeException {
    private final String entity;

    private final String ownerEntity;

    public EntityDeletingException(String entity, String ownerEntity) {
        super();
        this.entity = entity;
        this.ownerEntity = ownerEntity;
    }
}
