package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с config run")
@DataJpaTest
class ConfigRunRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConfigRunRepository repository;

    @DisplayName("должен находить config run по id")
    @Test
    void find() {
        var optionalActualConfigRun = repository.findById(1L);
        var expectedConfigRun = entityManager.find(ConfigRun.class, 1L);

        assertThat(optionalActualConfigRun)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedConfigRun);
    }

    @DisplayName("должен сохранять config run")
    @Test
    void save() {
        var run = entityManager.find(ConfigRun.class, 2L);
        var editedDescription = run.getDescription() + " new tail";
        run.setDescription(editedDescription);

        var savedRun = repository.save(run);

        assertThat(savedRun).isNotNull();
        assertThat(savedRun.getDescription())
                .isEqualTo(editedDescription);
    }
}