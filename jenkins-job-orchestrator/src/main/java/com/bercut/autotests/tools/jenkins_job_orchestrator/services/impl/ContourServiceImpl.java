package com.bercut.autotests.tools.jenkins_job_orchestrator.services.impl;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Contour;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.ContourRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.ContourService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ContourServiceImpl implements ContourService {
    private final ContourRepository contourRepository;

    @Override
    public List<Contour> findAll() {
        return contourRepository.findAll();
    }
}
