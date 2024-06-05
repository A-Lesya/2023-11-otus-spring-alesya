package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class JobPreviewDto {
    private Long id;

    private String path;

    private String contourId;

    public JobPreviewDto(Job job) {
        id = job.getId();
        path = job.getPath();
        contourId = job.getContour().getId();
    }
}
