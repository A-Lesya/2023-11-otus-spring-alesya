package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement.TimeLimit;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TimeLimitRepository extends ListCrudRepository<TimeLimit, Long> {
    List<TimeLimit> findByJobId(long jobId);

    List<TimeLimit> findByConfigRunId(long jobId);

    void deleteByJobId(long id);
}
