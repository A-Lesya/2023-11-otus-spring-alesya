package com.bercut.autotests.tools.jenkins_job_orchestrator.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequirementsPagesController {
    @GetMapping("/edit-requirement/time-limit/{id}")
    public String editTimeLimitPage(@PathVariable long id) {
        return "time-limit";
    }

    @GetMapping("/add-requirement/time-limit")
    public String addTimeLimitPage(@RequestParam(required = false) Long jobId,
                                   @RequestParam(required = false) Long configRunId) {
        return "time-limit";
    }
}
