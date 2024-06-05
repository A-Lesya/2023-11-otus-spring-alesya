package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.controllers;

import com.bercut.autotests.tools.jenkins_job_orchestrator.exceptions.EntityNotFoundException;
import com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto.TestCaseDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.TestCaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestCaseController {
    private final TestCaseService testCaseService;

    @GetMapping("/api/test-case")
    public List<TestCaseDto> getTestCases(@RequestParam("tcId")
                                          @NotBlank(message = "{tc-id-should-not-be-blank}")
                                          String tcId) {
        return testCaseService.findAllById(tcId)
                .stream()
                .map(TestCaseDto::new)
                .toList();
    }

    @GetMapping("/api/test-case/{id}")
    public TestCaseDto getTestCase(@PathVariable("id") String id) {
        return testCaseService.findById(id)
                .map(TestCaseDto::new)
                .orElseThrow(() -> new EntityNotFoundException("TestCase", id));
    }

    @PostMapping("/api/test-case")
    public TestCaseDto addTestCase(@Valid @RequestBody TestCaseDto testCase) {
        var result = testCaseService.insert(testCase.getId());
        return new TestCaseDto(result);
    }

    @DeleteMapping("/api/test-case/{id}")
    public void deleteTestCase(@PathVariable("id") String id) {
        testCaseService.deleteById(id);
    }
}
