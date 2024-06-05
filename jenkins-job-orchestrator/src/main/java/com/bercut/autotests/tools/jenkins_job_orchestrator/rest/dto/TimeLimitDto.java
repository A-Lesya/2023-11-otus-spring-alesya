package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto;

import com.bercut.autotests.tools.jenkins_job_orchestrator.validation.TimeLimitConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@TimeLimitConstraint
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeLimitDto {

    private Long id;

    private String description;

    private LocalTime after;

    private LocalTime before;

    private Long jobId;

    private Long configRunId;

    public TimeLimitDto(com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.TimeLimitDto dto) {
        id = dto.getId();
        description = dto.getDescription();
        before = dto.getBefore();
        after = dto.getAfter();
        jobId = dto.getJobId();
        configRunId = dto.getConfigRunId();
    }

    public com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.TimeLimitDto toBaseDto() {
        return new com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.TimeLimitDto()
                .setId(id)
                .setDescription(description)
                .setBefore(before)
                .setAfter(after)
                .setJobId(jobId)
                .setConfigRunId(configRunId);
    }
}
