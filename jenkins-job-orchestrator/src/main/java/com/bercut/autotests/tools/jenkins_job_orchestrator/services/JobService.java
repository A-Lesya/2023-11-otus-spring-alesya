package com.bercut.autotests.tools.jenkins_job_orchestrator.services;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.JobDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface JobService {
    List<Job> findAll();

    List<Job> findAllByPath(String path);

    Optional<JobDto> findById(long id);

    JobDto insert(String path, String contourId, Set<String> testCasesId);

    JobDto update(long id, String path, String contourId, Set<String> testCasesId);

    void deleteById(long id);
}
