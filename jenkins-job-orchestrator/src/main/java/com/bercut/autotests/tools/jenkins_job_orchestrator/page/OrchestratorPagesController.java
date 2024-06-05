package com.bercut.autotests.tools.jenkins_job_orchestrator.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrchestratorPagesController {
    @GetMapping("/orchestrator")
    public String orchestratorPage() {
        return "orchestrator";
    }
}
