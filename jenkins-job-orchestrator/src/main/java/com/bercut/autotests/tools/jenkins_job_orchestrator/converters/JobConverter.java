package com.bercut.autotests.tools.jenkins_job_orchestrator.converters;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.JobDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Contour;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobConverter {
    public JobDto toDto(Job job) {
        var contour = new Contour().setId(job.getContour().getId());
        var testCases = new ArrayList<>(job.getTestCases());
        var timeLimits = job.getTimeLimits() == null ? null : new ArrayList<>(job.getTimeLimits());

        return new JobDto()
                .setId(job.getId())
                .setPath(job.getPath())
                .setContour(contour)
                .setTestCases(testCases)
                .setTimeLimits(timeLimits);
    }

    public Job toDomainObject(long id, String path, Contour contour, List<TestCase> testCases) {
        return new Job()
                .setId(id)
                .setPath(path)
                .setContour(contour)
                .setTestCases(testCases);
    }
}
