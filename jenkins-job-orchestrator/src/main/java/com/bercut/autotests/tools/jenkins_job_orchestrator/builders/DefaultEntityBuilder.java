package com.bercut.autotests.tools.jenkins_job_orchestrator.builders;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TcConfig;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultEntityBuilder {
    public ConfigRun buildConfigRun() {
        return new ConfigRun().setDescription("all");
    }

    public TcConfig buildTcConfig() {
        var run = buildConfigRun();
        var config = new TcConfig()
                .setNumber(1)
                .setRuns(List.of(run));
        run.setTcConfig(config);

        return config;
    }

    public TestCase buildTestCase() {
        var config = buildTcConfig();

        var testCase = new TestCase().setConfigs(List.of(config));

        config.setTestCase(testCase);

        return testCase;
    }
}
