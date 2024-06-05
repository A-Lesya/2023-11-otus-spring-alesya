package com.bercut.autotests.tools.jenkins_job_orchestrator.rest.controllers;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Contour;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import com.bercut.autotests.tools.jenkins_job_orchestrator.rest.dto.JobDto;
import com.bercut.autotests.tools.jenkins_job_orchestrator.services.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobController.class)
@AutoConfigureMockMvc(addFilters = false)
class JobControllerTest {
    private final Locale locale = new Locale("en");

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private JobService service;

    @Test
    void shouldCorrectSaveNewJob() throws Exception {
        var path = "new path";
        var contourId = "v1";
        var testCasesId = List.of("C1_1_TC1", "C1_1_TC2");

        long id = 3L;

        var job = new com.bercut.autotests.tools.jenkins_job_orchestrator.dto.JobDto()
                .setId(id)
                .setPath(path)
                .setContour(new Contour().setId(contourId))
                .setTestCases(testCasesId.stream().map(tcId -> new TestCase().setId(tcId)).toList());


        var jobDto = new JobDto()
                .setPath(path)
                .setContourId(contourId)
                .setTestCasesId(testCasesId);

        String inputJson = mapper.writeValueAsString(jobDto);

        given(service.insert(eq(path), eq(contourId), eq(new HashSet<>(testCasesId)))).willReturn(job);

        jobDto.setId(id);
        String expectedResult = mapper.writeValueAsString(jobDto);
        mvc.perform(post("/api/job").contentType(APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }
}