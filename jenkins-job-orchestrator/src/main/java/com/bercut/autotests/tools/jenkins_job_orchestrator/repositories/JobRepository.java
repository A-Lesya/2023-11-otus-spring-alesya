package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface JobRepository extends ListCrudRepository<Job, Long> {
    @Nonnull
    @EntityGraph(attributePaths = "contour")
    List<Job> findAll();

    @Nonnull
    @EntityGraph(attributePaths = "contour")
    List<Job> findByPathContainingIgnoreCase(String path);
}
