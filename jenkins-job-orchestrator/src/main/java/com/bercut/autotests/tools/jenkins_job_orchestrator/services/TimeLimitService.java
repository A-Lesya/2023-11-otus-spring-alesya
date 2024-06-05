package com.bercut.autotests.tools.jenkins_job_orchestrator.services;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.TimeLimitDto;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeLimitService {
    Optional<TimeLimitDto> findById(long id);

    List<TimeLimitDto> findAllByJobId(long id);

    List<TimeLimitDto> findAllByConfigRunId(long id);

    TimeLimitDto insert(TimeLimitDto timeLimit);

    TimeLimitDto update(long id, String description, LocalTime before, LocalTime after);

    void deleteById(long id);
}
