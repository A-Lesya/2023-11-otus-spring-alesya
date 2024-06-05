package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.exception_handles;

import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.CommonRequirementValidationException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityDeletingException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntitySavingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class EntityAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseErrorBody> findingError(EntityNotFoundException e) {
        String message = "%s with id %s not found".formatted(e.getEntity(), e.getIds());

        return ResponseEntity.badRequest().body(new ResponseErrorBody(message));
    }

    @ExceptionHandler(EntitySavingException.class)
    public ResponseEntity<ResponseErrorBody> savingError(EntitySavingException e) {
        log.info("entity saving error", e);
        String message = "Error occurred during saving %s. Perhaps %s with key %s already exists."
                .formatted(e.getEntity(), e.getEntity(), e.getKey());

        return ResponseEntity.badRequest().body(new ResponseErrorBody(message));
    }

    @ExceptionHandler(CommonRequirementValidationException.class)
    public ResponseEntity<ResponseErrorBody> requirementValidationError(CommonRequirementValidationException e) {
        String message = e.getMessage();

        return ResponseEntity.badRequest().body(new ResponseErrorBody(message));
    }

    @ExceptionHandler(EntityDeletingException.class)
    public ResponseEntity<ResponseErrorBody> deletionError(EntityDeletingException e) {
        String message = "It is forbidden to delete the last %s of %s. Try removing %s instead"
                .formatted(e.getEntity(), e.getOwnerEntity(), e.getOwnerEntity());

        return ResponseEntity.badRequest().body(new ResponseErrorBody(message));
    }
}
