package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.controllers;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Contour;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.ContourService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContourController {
    private final ContourService contourService;

    @GetMapping("/api/contour")
    public List<Contour> getAllContours() {
        return contourService.findAll();
    }
}
