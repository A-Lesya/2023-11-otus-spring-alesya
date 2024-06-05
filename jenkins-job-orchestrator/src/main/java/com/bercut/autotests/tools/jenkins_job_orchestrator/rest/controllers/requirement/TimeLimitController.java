package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.controllers.requirement;

import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto.TimeLimitDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.TimeLimitService;
import com.bercut.autotests.tools.jenkins_job_orchestrator.validation.RequirementValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class TimeLimitController {
    private final TimeLimitService timeLimitService;

    private final RequirementValidator requirementValidator;

    @GetMapping("/api/requirement/time-limit")
    public List<TimeLimitDto> getAllTimeLimitsByOwnerId(@RequestParam(required = false) Long jobId,
                                                        @RequestParam(required = false) Long configRunId) {
        var timeLimitDto = new com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.TimeLimitDto()
                .setJobId(jobId)
                .setConfigRunId(configRunId);
        requirementValidator.checkExactlyOneOwnerIsSpecified(timeLimitDto);

        if (jobId != null) {
            return timeLimitService.findAllByJobId(jobId).stream().map(TimeLimitDto::new).toList();
        }

        return timeLimitService.findAllByConfigRunId(configRunId).stream().map(TimeLimitDto::new).toList();
    }

    @GetMapping("/api/requirement/time-limit/{id}")
    public TimeLimitDto getTimeLimit(@PathVariable long id) {
        return timeLimitService.findById(id)
                .map(TimeLimitDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Time limit", id));
    }

    @PostMapping("/api/requirement/time-limit")
    public TimeLimitDto addTimeLimit(@RequestBody @Valid TimeLimitDto timeLimit) {
        var result = timeLimitService.insert(timeLimit.toBaseDto());
        return new TimeLimitDto(result);
    }

    @PatchMapping("/api/requirement/time-limit/{id}")
    public TimeLimitDto updateTimeLimit(@PathVariable long id, @RequestBody @Valid TimeLimitDto timeLimit) {
        var result = timeLimitService.update(
                id,
                timeLimit.getDescription(),
                timeLimit.getBefore(),
                timeLimit.getAfter()
        );
        return new TimeLimitDto(result);
    }

    @DeleteMapping("/api/requirement/time-limit/{id}")
    public void deleteTimeLimit(@PathVariable long id) {
        timeLimitService.deleteById(id);
    }
}
