package com.bercut.autotests.tools.jenkins_job_orchestrator.services;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Contour;

import java.util.List;

public interface ContourService {
    List<Contour> findAll();
}
