package com.bercut.autotests.tools.jenkins_job_orchestrator.repositories;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.TcConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий spring data для работы с конфигами")
@DataJpaTest
class TcConfigRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TcConfigRepository repository;

    @DisplayName("должен находить конфиг по id")
    @Test
    void find() {
        var optionalActualTcConfig = repository.findById(1L);
        var expectedTcConfig = entityManager.find(TcConfig.class, 1L);

        assertThat(optionalActualTcConfig)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedTcConfig);
    }

    @DisplayName("должен сохранять конфиг")
    @Test
    void save() {
        var config = entityManager.find(TcConfig.class, 2L);
        var editedNumber = config.getNumber() + 11;
        config.setNumber(editedNumber);

        var savedConfig = repository.save(config);

        assertThat(savedConfig).isNotNull();
        assertThat(savedConfig.getNumber())
                .isEqualTo(editedNumber);
    }
}