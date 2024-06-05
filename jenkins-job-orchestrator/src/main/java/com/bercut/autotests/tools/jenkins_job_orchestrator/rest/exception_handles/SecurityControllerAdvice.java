package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.exception_handles;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityControllerAdvice {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseErrorBody> accessError() {
        String message = "access denied";

        return ResponseEntity.badRequest().body(new ResponseErrorBody(message));
    }
}
