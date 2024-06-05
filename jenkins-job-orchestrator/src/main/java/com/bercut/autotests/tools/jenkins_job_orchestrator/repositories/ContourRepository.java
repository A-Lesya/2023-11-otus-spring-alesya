package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Contour;
import org.springframework.data.repository.ListCrudRepository;

public interface ContourRepository extends ListCrudRepository<Contour, String> {
}
