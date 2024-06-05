package com.bercut.autotests.tools.jenkins_job_orchestrator.dto;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Contour;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement.TimeLimit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class JobDto {
    private long id;

    private String path;

    private Contour contour;

    private List<TestCase> testCases;

    private List<TimeLimit> timeLimits;
}
