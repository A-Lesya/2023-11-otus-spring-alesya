package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TcConfig;
import org.springframework.data.repository.ListCrudRepository;

public interface TcConfigRepository extends ListCrudRepository<TcConfig, Long> {
    boolean existsByTestCaseIdAndNumber(String tcId, int number);

    boolean existsByTestCaseIdAndIdNot(String testCaseId, long id);

    void deleteByTestCaseId(String testCaseId);
}
