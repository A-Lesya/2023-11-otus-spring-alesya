package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с джобами")
@DataJpaTest
class JobRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JobRepository repository;

    @DisplayName("должен находить job по id")
    @Test
    void find() {
        val optionalActualJob = repository.findById(1L);
        val expectedJob = entityManager.find(Job.class, 1L);

        assertThat(optionalActualJob)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedJob);
    }

    @DisplayName("должен сохранять job")
    @Test
    void save() {
        var job = entityManager.find(Job.class, 1L);

        var newPath = "new path";
        job.setPath(newPath);
        repository.save(job);

        var savedObject = entityManager.find(Job.class, job.getId());
        assertThat(savedObject)
                .isNotNull()
                .hasFieldOrPropertyWithValue("path", newPath);
    }
}