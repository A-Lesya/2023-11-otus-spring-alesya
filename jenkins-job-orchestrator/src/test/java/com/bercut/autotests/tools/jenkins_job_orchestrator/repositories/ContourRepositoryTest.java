package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Contour;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с контурами")
@DataJpaTest
class ContourRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContourRepository repository;

    @DisplayName("должен находить контур по id")
    @Test
    void find() {
        val optionalActualJob = repository.findById("v1");
        val expectedContour = entityManager.find(Contour.class, "v1");

        assertThat(optionalActualJob)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedContour);
    }

    @DisplayName("должен добавлять контур")
    @Test
    void save() {
        var newContour = new Contour("v_new");
        repository.save(newContour);

        var savedObject = entityManager.find(Contour.class, newContour.getId());
        assertThat(savedObject)
                .isNotNull()
                .isEqualTo(newContour);
    }
}