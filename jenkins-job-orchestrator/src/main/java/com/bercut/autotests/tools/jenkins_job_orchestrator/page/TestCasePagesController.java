package com.bercut.autotests.tools.jenkins_job_orchestrator.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestCasePagesController {
    @GetMapping("/search-test-case")
    public String searchTcPage() {
        return "search_tc";
    }

    @GetMapping("/test-case/{id}")
    public String tcPage(@PathVariable("id") String id) {
        return "test-case";
    }

    @GetMapping("/add-test-case")
    public String addPage() {
        return "test-case";
    }

    @GetMapping("/edit-config-run/{id}")
    public String editConfigRunPage(@PathVariable("id") String id) {
        return "config-run";
    }

    @GetMapping("/add-config-run")
    public String addConfigRunPage(@RequestParam String tcConfigId) {
        return "config-run";
    }
}
