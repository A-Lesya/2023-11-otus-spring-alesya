package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TestCaseRepository extends ListCrudRepository<TestCase, String> {
    List<TestCase> findByIdContainingIgnoreCase(String id);
}
