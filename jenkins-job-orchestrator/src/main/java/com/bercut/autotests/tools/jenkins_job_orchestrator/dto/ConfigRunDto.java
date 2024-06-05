package com.bercut.autotests.tools.jenkins_job_orchestrator.dto;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.TimeLimitDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
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

    public ConfigRunDto(ConfigRun configRun) {
        id = configRun.getId();
        description = configRun.getDescription();
        timeLimits = configRun.getTimeLimits() == null ? null : configRun.getTimeLimits().stream()
                .map(timeLimit -> new TimeLimitDto(timeLimit, null, id))
                .toList();
    }
}
