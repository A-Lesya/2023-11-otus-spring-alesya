package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)

public class ConfigRunDto {
    private Long id;

    private String description;

    private List<TimeLimitDto> timeLimits;

    public ConfigRunDto(com.bercut.autotests.tools.jenkins_job_orchestrator.dto.ConfigRunDto configRun) {
        id = configRun.getId();
        description = configRun.getDescription();
        timeLimits = configRun.getTimeLimits() == null ? null :
                configRun.getTimeLimits()
                        .stream()
                        .map(TimeLimitDto::new)
                        .toList();
    }
}
