package com.bercut.autotests.tools.jenkins_job_orchestrator.services.impl;

import com.bercut.autotests.tools.jenkins_job_orchestrator.builders.DefaultEntityBuilder;
import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.TcConfigDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityDeletingException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntitySavingException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement.TimeLimit;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.ConfigRunRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.TcConfigRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.TimeLimitRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.TcConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TcConfigServiceImpl implements TcConfigService {
    private final TcConfigRepository tcConfigRepository;

    private final ConfigRunRepository configRunRepository;

    private final TimeLimitRepository timeLimitRepository;

    private final DefaultEntityBuilder defaultEntityBuilder;

    @Transactional
    @Override
    public TcConfigDto insert(String tcId, int number) {
        if (tcConfigRepository.existsByTestCaseIdAndNumber(tcId, number)) {
            throw new EntitySavingException("Config for tc %s".formatted(tcId), String.valueOf(number), null);
        }

        var testCase = new TestCase().setId(tcId);
        var config = defaultEntityBuilder.buildTcConfig()
                .setNumber(number)
                .setTestCase(testCase);
        var savedConfig = tcConfigRepository.save(config);
        var run = defaultEntityBuilder.buildConfigRun();
        run.setTcConfig(savedConfig);
        configRunRepository.save(run);

        var actualConfigId = savedConfig.getId();
        var actualConfig = tcConfigRepository.findById(actualConfigId)
                .orElseThrow(() -> new EntityNotFoundException("Config", actualConfigId));
        return new TcConfigDto(actualConfig);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        var config = tcConfigRepository.findById(id);
        if (config.isEmpty()) {
            return;
        }

        var tcId = config.get().getTestCase().getId();
        var isLastConfigInTest = !tcConfigRepository.existsByTestCaseIdAndIdNot(tcId, id);
        if (isLastConfigInTest) {
            throw new EntityDeletingException("config", "test case");
        }

        List<Long> timeLimitsId = config.get().getRuns().stream()
                .flatMap((run -> run.getTimeLimits().stream()))
                .map(TimeLimit::getId)
                .toList();

        timeLimitRepository.deleteAllById(timeLimitsId);

        configRunRepository.deleteByTcConfigId(id);

        tcConfigRepository.deleteById(id);
    }
}
