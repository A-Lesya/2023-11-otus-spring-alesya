package com.bercut.autotests.tools.jenkins_job_orchestrator.services.impl;

import com.bercut.autotests.tools.jenkins_job_orchestrator.builders.DefaultEntityBuilder;
import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.TestCaseDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntitySavingException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TcConfig;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement.TimeLimit;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.ConfigRunRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.TcConfigRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.TestCaseRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.TimeLimitRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TestCaseServiceImpl implements TestCaseService {
    private final TestCaseRepository testCaseRepository;

    private final TcConfigRepository tcConfigRepository;

    private final ConfigRunRepository configRunRepository;

    private final TimeLimitRepository timeLimitRepository;

    private final DefaultEntityBuilder defaultEntityBuilder;

    @Override
    public List<TestCaseDto> findAllById(String id) {
        return testCaseRepository.findByIdContainingIgnoreCase(id)
                .stream()
                .map(TestCaseDto::new)
                .toList();
    }

    @Override
    public Optional<TestCaseDto> findById(String id) {
        return testCaseRepository.findById(id).map(TestCaseDto::new);
    }

    @Transactional
    @Override
    public TestCaseDto insert(String id) {
        if (testCaseRepository.existsById(id)) {
            throw new EntitySavingException("Test case", id, null);
        }

        var testCase = defaultEntityBuilder.buildTestCase()
                .setId(id);

        var savedTestCase = testCaseRepository.save(testCase);
        var config = defaultEntityBuilder.buildTcConfig()
                .setTestCase(savedTestCase);
        var savedConfig = tcConfigRepository.save(config);
        var run = defaultEntityBuilder.buildConfigRun()
                .setTcConfig(savedConfig);
        configRunRepository.save(run);

        var result = testCaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Test case", id));

        return new TestCaseDto(result);
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    @Override
    public void deleteById(String id) {
        var testCase = testCaseRepository.findById(id);
        if (testCase.isEmpty()) {
            return;
        }

        List<Long> configsId = testCase.get().getConfigs()
                .stream()
                .map(TcConfig::getId)
                .toList();
        List<Long> configRunsId = testCase.get().getConfigs().stream()
                .flatMap(config -> config.getRuns().stream())
                .map(ConfigRun::getId)
                .toList();
        List<Long> timeLimitsId = testCase.get().getConfigs().stream()
                .flatMap(config -> config.getRuns().stream())
                .flatMap((run -> run.getTimeLimits().stream()))
                .map(TimeLimit::getId)
                .toList();

        timeLimitRepository.deleteAllById(timeLimitsId);
        configRunRepository.deleteAllById(configRunsId);
        tcConfigRepository.deleteAllById(configsId);
        testCaseRepository.deleteById(id);
    }
}
