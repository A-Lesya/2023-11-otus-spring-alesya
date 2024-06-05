package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.exception_handles;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseErrorBody {

    private final List<String> errors;

    public ResponseErrorBody(String message) {
        errors = List.of(message);
    }
}
