package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.TimeLimitDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
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
    private Long id;

    @NotBlank(message = "{job-path-field-should-not-be-blank}")
    private String path;

    @NotBlank(message = "{job-contour-field-should-not-be-blank}")
    private String contourId;

    @NotEmpty(message = "{job-testcases-field-should-not-be-blank}")
    private List<@NotBlank(message = "{job-testcases-field-should-not-be-blank}") String> testCasesId;

    @Null
    private List<TimeLimitDto> timeLimits;

    public JobDto(com.bercut.autotests.tools.jenkins_job_orchestrator.dto.JobDto job) {
        id = job.getId();
        path = job.getPath();
        contourId = job.getContour().getId();
        testCasesId = job.getTestCases()
                .stream()
                .map(TestCase::getId)
                .toList();
        if (job.getTimeLimits() != null) {
            timeLimits = job.getTimeLimits().stream()
                    .map(timeLimit -> new TimeLimitDto(timeLimit, job.getId(), null))
                    .toList();
        }
    }
}
