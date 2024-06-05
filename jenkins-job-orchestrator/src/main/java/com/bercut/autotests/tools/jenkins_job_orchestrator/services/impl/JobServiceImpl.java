package com.bercut.autotests.tools.jenkins_job_orchestrator.services.impl;

import com.bercut.autotests.tools.jenkins_job_orchestrator.converters.JobConverter;
import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.JobDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntitySavingException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.ContourRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.JobRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.TestCaseRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.TimeLimitRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.JobService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    private final ContourRepository contourRepository;

    private final TestCaseRepository testCaseRepository;

    private final TimeLimitRepository timeLimitRepository;

    private final JobConverter jobConverter;

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> findAllByPath(String path) {
        return jobRepository.findByPathContainingIgnoreCase(path);
    }

    @Override
    public Optional<JobDto> findById(long id) {
        return jobRepository.findById(id)
                .map(jobConverter::toDto);
    }

    @Transactional
    @Override
    public JobDto insert(String path, String contourId, Set<String> testCasesId) {
        var job = save(0, path, contourId, testCasesId);
        return jobConverter.toDto(job);
    }

    @Transactional
    @Override
    public JobDto update(long id, String path, String contourId, Set<String> testCasesId) {
        var job = save(id, path, contourId, testCasesId);
        return jobConverter.toDto(job);
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    @Override
    public void deleteById(long id) {
        timeLimitRepository.deleteByJobId(id);
        jobRepository.deleteById(id);
    }

    private Job save(long id, String path, String contourId, Set<String> testCasesId) {
        if (isEmpty(testCasesId)) {
            throw new IllegalArgumentException("testCases ids must not be null");
        }

        var contour = contourRepository.findById(contourId)
                .orElseThrow(() -> new EntityNotFoundException("Contour", contourId));

        var testCases = testCaseRepository.findAllById(testCasesId);
        if (isEmpty(testCases) || testCasesId.size() != testCases.size()) {
            List<String> foundIds = testCases.stream().map(TestCase::getId).toList();
            List<String> absentIds = testCasesId.stream()
                    .filter(genreId -> !foundIds.contains(genreId))
                    .toList();
            throw new EntityNotFoundException("TestCase", absentIds);
        }

        var job = jobConverter.toDomainObject(id, path, contour, testCases);
        try {
            return jobRepository.save(job);
        } catch (DataIntegrityViolationException e) {
            throw new EntitySavingException("Job", path, e);
        }
    }
}
