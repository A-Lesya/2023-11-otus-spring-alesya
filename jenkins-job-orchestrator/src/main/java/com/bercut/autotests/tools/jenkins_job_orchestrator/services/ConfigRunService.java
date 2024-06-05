package com.bercut.autotests.tools.jenkins_job_orchestrator.services;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.ConfigRunDto;

import java.util.Optional;

public interface ConfigRunService {
    Optional<ConfigRunDto> findById(long id);

    ConfigRunDto insert(long tcConfigId, String description);

    ConfigRunDto update(long id, String description);

    void deleteById(long id);
}
