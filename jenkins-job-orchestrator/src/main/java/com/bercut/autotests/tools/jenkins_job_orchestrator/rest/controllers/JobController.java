package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.controllers;

import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto.JobDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto.JobPreviewDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class JobController {
    private final JobService jobService;

    @GetMapping("/api/job")
    public List<JobPreviewDto> getJobs(@RequestParam String path) {
        List<Job> result;
        if (path == null || path.isEmpty()) {
            result = jobService.findAll();
        } else {
            result = jobService.findAllByPath(path);
        }

        return result
                .stream()
                .map(JobPreviewDto::new)
                .toList();
    }

    @GetMapping("/api/job/{id}")
    public JobDto getJob(@PathVariable("id") long id) {
        return jobService.findById(id)
                .map(JobDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Job", id));
    }

    @PostMapping("/api/job")
    public JobDto addJob(@Valid @RequestBody JobDto job) {
        var path = job.getPath();
        var contourId = job.getContourId();
        var testCasesId = new HashSet<>(job.getTestCasesId());

        var insertedJob = jobService.insert(path, contourId, testCasesId);

        return new JobDto(insertedJob);
    }


    @PatchMapping("/api/job/{id}")
    public JobDto editJob(@PathVariable long id, @Valid @RequestBody JobDto job) {
        var path = job.getPath();
        var contourId = job.getContourId();
        var testCasesId = new HashSet<>(job.getTestCasesId());

        var editedJob = jobService.update(id, path, contourId, testCasesId);
        return new JobDto(editedJob);
    }

    @DeleteMapping("/api/job/{id}")
    public void deleteJob(@PathVariable("id") long id) {
        jobService.deleteById(id);
    }
}
