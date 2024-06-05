package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.controllers;

import com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto.TcConfigDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.TcConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TcConfigController {
    private final TcConfigService tcConfigService;

    @PostMapping("/api/tc-config")
    public TcConfigDto addConfig(@RequestParam String tcId,
                                 @Valid @RequestBody TcConfigDto config) {
        var result = tcConfigService.insert(tcId, config.getNumber());
        return new TcConfigDto(result);
    }

    @DeleteMapping("/api/tc-config/{id}")
    public void deleteConfig(@PathVariable("id") long id) {
        tcConfigService.deleteById(id);
    }
}
