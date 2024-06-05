package com.bercut.autotests.tools.jenkins_job_orchestrator.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class JobPagesController {
    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/search-job")
    public String searchJobPage() {
        return "search_job";
    }

    @GetMapping("/job/{id}")
    public String jobPage(@PathVariable("id") long id) {
        return "job";
    }

    @GetMapping("/add-job")
    public String addPage() {
        return "job";
    }
}
