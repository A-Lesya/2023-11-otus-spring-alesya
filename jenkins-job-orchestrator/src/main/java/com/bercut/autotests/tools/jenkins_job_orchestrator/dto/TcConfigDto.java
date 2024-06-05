package com.bercut.autotests.tools.jenkins_job_orchestrator.dto;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TcConfig;
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

    private Integer number;

    private List<ConfigRunDto> runs;

    public TcConfigDto(TcConfig tcConfig) {
        id = tcConfig.getId();
        number = tcConfig.getNumber();
        runs = tcConfig.getRuns() == null ? null :
                tcConfig.getRuns().stream().map(ConfigRunDto::new).toList();
    }
}
