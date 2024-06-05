package com.bercut.autotests.tools.jenkins_job_orchestrator.services;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.TcConfigDto;

public interface TcConfigService {
    TcConfigDto insert(String tcId, int number);

    void deleteById(long id);
}
