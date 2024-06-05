package com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement.TimeLimit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class TimeLimitDto implements CommonRequirement {

    private Long id;

    private String description;

    private LocalTime after;

    private LocalTime before;

    private Long jobId;

    private Long configRunId;

    public TimeLimitDto(TimeLimit timeLimit, Long jobId, Long configRunId) {
        id = timeLimit.getId();
        description = timeLimit.getDescription();
        before = timeLimit.getBefore();
        after = timeLimit.getAfter();
        this.jobId = jobId;
        this.configRunId = configRunId;
    }

    public TimeLimit toDomainObject() {
        var timeLimit = new TimeLimit()
                .setDescription(description)
                .setBefore(before)
                .setAfter(after);

        if (jobId != null) {
            var job = new Job().setId(jobId);
            timeLimit.setJob(job);
        } else if (configRunId != null) {
            var configRun = new ConfigRun().setId(configRunId);
            timeLimit.setConfigRun(configRun);
        }
        return timeLimit;
    }
}
