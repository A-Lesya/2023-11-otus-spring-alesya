package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.controllers;

import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto.ConfigRunDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.ConfigRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ConfigRunController {
    private final ConfigRunService configRunService;

    @GetMapping("/api/config-run/{id}")
    public ConfigRunDto getConfigRun(@PathVariable("id") long id) {
        return configRunService.findById(id)
                .map(ConfigRunDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Config run", id));
    }

    @PostMapping("/api/config-run")
    public ConfigRunDto addConfigRun(@RequestParam long tcConfigId,
                                     @RequestBody ConfigRunDto configRun) {
        var result = configRunService.insert(tcConfigId, configRun.getDescription());
        return new ConfigRunDto(result);
    }

    @PatchMapping("/api/config-run/{id}")
    public ConfigRunDto updateConfigRun(@PathVariable long id,
                                        @RequestBody ConfigRunDto configRun) {
        var result = configRunService.update(id, configRun.getDescription());
        return new ConfigRunDto(result);
    }

    @DeleteMapping("/api/config-run/{id}")
    public void deleteConfigRun(@PathVariable("id") long id) {
        configRunService.deleteById(id);
    }
}
