package com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement.todo;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;

//todo BeforeRunningTc
// Запуск возможен строго после теста с настройками
public class AfterPassingTc {
    private Long id;

    private String description;

    // тест, после успешного выполнения которого можно выполнять запуск
    private TestCase setupTestCase;

    private ConfigRun configRun;

    private Job job;
}
