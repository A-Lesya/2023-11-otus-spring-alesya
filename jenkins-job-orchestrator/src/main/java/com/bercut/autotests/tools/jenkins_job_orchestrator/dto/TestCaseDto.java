package com.bercut.autotests.tools.jenkins_job_orchestrator.dto;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
    private String id;

    private List<TcConfigDto> configs;

    public TestCaseDto(TestCase testCase) {
        id = testCase.getId();
        configs = testCase.getConfigs().stream().map(TcConfigDto::new).toList();
    }
}
