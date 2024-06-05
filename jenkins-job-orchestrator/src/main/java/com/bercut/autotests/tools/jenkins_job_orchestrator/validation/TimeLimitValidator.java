package com.bercut.autotests.tools.jenkins_job_orchestrator.validation;

import com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto.TimeLimitDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeLimitValidator implements
        ConstraintValidator<TimeLimitConstraint, TimeLimitDto> {

    @Override
    public boolean isValid(TimeLimitDto timeLimitDto,
                           ConstraintValidatorContext cxt) {
        if (timeLimitDto.getBefore() == null && timeLimitDto.getAfter() == null) {
            return false;
        }

        if (timeLimitDto.getBefore() != null && timeLimitDto.getAfter() != null) {
            return !timeLimitDto.getBefore().isBefore(timeLimitDto.getAfter());
        }
        return true;
    }
}
