package com.bercut.autotests.tools.jenkins_job_orchestrator.services.impl;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.TimeLimitDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.repositories.TimeLimitRepository;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.TimeLimitService;
import com.bercut.autotests.tools.jenkins_job_orchestrator.validation.EntityPresenceValidator;
import com.bercut.autotests.tools.jenkins_job_orchestrator.validation.RequirementValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TimeLimitServiceImpl implements TimeLimitService {
    private final TimeLimitRepository timeLimitRepository;

    private final RequirementValidator requirementValidator;

    private final EntityPresenceValidator entityPresenceValidator;

    @Override
    public Optional<TimeLimitDto> findById(long id) {
        return timeLimitRepository.findById(id)
                .map(timeLimit -> new TimeLimitDto(timeLimit, null, null));
    }

    @Override
    public List<TimeLimitDto> findAllByJobId(long id) {
        entityPresenceValidator.validateJobId(id);
        return timeLimitRepository.findByJobId(id)
                .stream()
                .map(timeLimit -> new TimeLimitDto(timeLimit, id, null))
                .toList();
    }

    @Override
    public List<TimeLimitDto> findAllByConfigRunId(long id) {
        entityPresenceValidator.validateConfigRunId(id);
        return timeLimitRepository.findByConfigRunId(id)
                .stream()
                .map(timeLimit -> new TimeLimitDto(timeLimit, null, id))
                .toList();
    }

    @Override
    public TimeLimitDto insert(TimeLimitDto timeLimitDto) {
        requirementValidator.validateCommonRequirement(timeLimitDto);

        var timeLimit = timeLimitDto.toDomainObject();

        var savedTimeLimit = timeLimitRepository.save(timeLimit);
        return new TimeLimitDto(savedTimeLimit, timeLimitDto.getJobId(), timeLimitDto.getConfigRunId());
    }

    @Override
    public TimeLimitDto update(long id, String description, LocalTime before, LocalTime after) {
        var timeLimit = timeLimitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TimeLimit", id));

        timeLimit.setDescription(description)
                .setBefore(before)
                .setAfter(after);


        var savedTimeLimit = timeLimitRepository.save(timeLimit);

        return new TimeLimitDto(savedTimeLimit, null, null);
    }

    @Override
    public void deleteById(long id) {
        timeLimitRepository.deleteById(id);
    }


}
