package com.bercut.autotests.tools.jenkins_job_orchestrator.services.impl;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.ConfigRunDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityDeletingException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TcConfig;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.ConfigRunRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.ConfigRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConfigRunServiceImpl implements ConfigRunService {
    private final ConfigRunRepository configRunRepository;

    @Override
    public Optional<ConfigRunDto> findById(long id) {
        return configRunRepository.findById(id)
                .map(ConfigRunDto::new);
    }

    @Transactional
    @Override
    public ConfigRunDto insert(long tcConfigId, String description) {
        var config = new TcConfig().setId(tcConfigId);

        var run = new ConfigRun()
                .setDescription(description)
                .setTcConfig(config);
        var savedRun = configRunRepository.save(run);

        return new ConfigRunDto(savedRun);
    }

    @Transactional
    @Override
    public ConfigRunDto update(long id, String description) {
        var run = configRunRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Config run", id));

        run.setDescription(description);
        var savedRun = configRunRepository.save(run);

        return new ConfigRunDto(savedRun);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        var run = configRunRepository.findById(id);
        if (run.isEmpty()) {
            return;
        }

        var tcConfigId = run.get().getTcConfig().getId();
        var isLastRunInConfig = !configRunRepository.existsByTcConfigIdAndIdNot(tcConfigId, id);
        if (isLastRunInConfig) {
            throw new EntityDeletingException("run", "config");
        }

        configRunRepository.deleteById(id);
    }
}
