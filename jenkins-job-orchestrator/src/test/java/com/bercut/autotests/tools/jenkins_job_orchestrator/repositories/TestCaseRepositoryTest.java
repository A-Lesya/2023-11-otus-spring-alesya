package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с тестами")
@DataJpaTest
class TestCaseRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TestCaseRepository repository;

    @DisplayName("должен находить тест по id")
    @Test
    void find() {
        var optionalActualTc = repository.findById("C1_1_TC1");
        var expectedTc = entityManager.find(TestCase.class, "C1_1_TC1");

        assertThat(optionalActualTc)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedTc);
    }

    @DisplayName("должен добавлять тест")
    @Test
    void save() {
        var newTestCase = new TestCase().setId("tc_new");
        repository.save(newTestCase);

        var savedObject = entityManager.find(TestCase.class, newTestCase.getId());
        assertThat(savedObject)
                .isNotNull();

        // странно проверять id после получения по id
        assertThat(savedObject.getId())
                .isEqualTo(newTestCase.getId());
    }
}