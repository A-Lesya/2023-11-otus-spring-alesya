package com.bercut.autotests.tools.jenkins_job_orchestrator.services;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.TestCaseDto;

import java.util.List;
import java.util.Optional;

public interface TestCaseService {
    List<TestCaseDto> findAllById(String id);

    Optional<TestCaseDto> findById(String id);

    TestCaseDto insert(String id);

    void deleteById(String id);
}
