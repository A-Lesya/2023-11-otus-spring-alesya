package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto;

import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^C(\\d+(_\\d+)?)_(\\d+(_\\d+)?)_(TC|MT)\\d+(_\\d+)?$")
    private String id;

    private List<TcConfigDto> configs;

    public TestCaseDto(com.bercut.autotests.tools.jenkins_job_orchestrator.dto.TestCaseDto testCase) {
        id = testCase.getId();
        configs = testCase.getConfigs().stream().map(TcConfigDto::new).toList();
    }
}
