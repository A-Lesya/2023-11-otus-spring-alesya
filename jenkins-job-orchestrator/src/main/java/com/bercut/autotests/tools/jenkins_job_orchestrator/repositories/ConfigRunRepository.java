package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
import org.springframework.data.repository.ListCrudRepository;

public interface ConfigRunRepository extends ListCrudRepository<ConfigRun, Long> {
    void deleteByTcConfigTestCaseId(String testCaseId);

    void deleteByTcConfigId(long id);

    boolean existsByTcConfigIdAndIdNot(long tcConfigId, long id);
}
