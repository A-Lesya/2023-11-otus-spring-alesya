package com.bercut.autotests.tools.jenkins_job_orchestrator.validation;

import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.CommonRequirement;
import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.ConfigRunRequirement;
import com.bercut.autotests.tools.jenkins_job_orchestrator.dto.requirement.JobRequirement;
import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.CommonRequirementValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RequirementValidator {
    private final EntityPresenceValidator entityPresenceValidator;

    public void validateCommonRequirement(CommonRequirement requirement) {
        checkExactlyOneOwnerIsSpecified(requirement);

        checkOwnerIdIsPresentInRepository(requirement);
    }

    public void checkExactlyOneOwnerIsSpecified(CommonRequirement requirement) {
        if (isConfigRunIdAbsent(requirement) && isJobIdAbsent(requirement)) {
            throw new CommonRequirementValidationException("Owner id must be specified");
        }

        if (!isConfigRunIdAbsent(requirement) && !isJobIdAbsent(requirement)) {
            throw new CommonRequirementValidationException("Only one owner id can be specified");
        }
    }

    private void checkOwnerIdIsPresentInRepository(CommonRequirement requirement) {
        if (!isConfigRunIdAbsent(requirement)) {
            validateConfigRunId(requirement);
        }

        if (!isJobIdAbsent(requirement)) {
            validateJobId(requirement);
        }
    }

    private boolean isConfigRunIdAbsent(CommonRequirement requirement) {
        return requirement.getConfigRunId() == null || requirement.getConfigRunId() == 0;
    }

    private boolean isJobIdAbsent(CommonRequirement requirement) {
        return requirement.getJobId() == null || requirement.getJobId() == 0;
    }

    private void validateJobId(JobRequirement requirement) {
        entityPresenceValidator.validateJobId(requirement.getJobId());
    }

    private void validateConfigRunId(ConfigRunRequirement requirement) {
        entityPresenceValidator.validateConfigRunId(requirement.getConfigRunId());
    }
}
