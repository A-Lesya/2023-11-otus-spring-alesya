package com.bercut.autotests.tools.jenkins_job_orchestrator.validation;

import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.ConfigRunRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EntityPresenceValidator {
    private final JobRepository jobRepository;

    private final ConfigRunRepository configRunRepository;

    public void validateJobId(long id) {
        var isJobExists = jobRepository.existsById(id);

        if (!isJobExists) {
            throw new EntityNotFoundException("Job", id);
        }
    }

    public void validateConfigRunId(long id) {
        var isConfigRunExists = configRunRepository.existsById(id);

        if (!isConfigRunExists) {
            throw new EntityNotFoundException("Config run", id);
        }
    }
}
