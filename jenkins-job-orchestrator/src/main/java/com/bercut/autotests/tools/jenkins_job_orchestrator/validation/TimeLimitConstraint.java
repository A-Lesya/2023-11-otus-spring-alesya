package com.bercut.autotests.tools.jenkins_job_orchestrator.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = TimeLimitValidator.class)
@Documented
public @interface TimeLimitConstraint {
    String message() default "Invalid time limit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
