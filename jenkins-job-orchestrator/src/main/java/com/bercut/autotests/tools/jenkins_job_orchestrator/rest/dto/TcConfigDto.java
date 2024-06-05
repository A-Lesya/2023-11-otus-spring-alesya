package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class TcConfigDto {
    private Long id;

    @NotNull(message = "{config-num-should-not-be-blank}")
    private Integer number;

    private List<ConfigRunDto> runs;

    public TcConfigDto(com.bercut.autotests.tools.jenkins_job_orchestrator.dto.TcConfigDto tcConfig) {
        id = tcConfig.getId();
        number = tcConfig.getNumber();
        runs = tcConfig.getRuns() == null ? null :
                tcConfig.getRuns().stream().map(ConfigRunDto::new).toList();
    }
}
