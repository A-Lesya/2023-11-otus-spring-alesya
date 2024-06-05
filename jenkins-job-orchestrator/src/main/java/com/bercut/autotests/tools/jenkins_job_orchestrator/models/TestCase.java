package com.bercut.autotests.tools.jenkins_job_orchestrator.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_case")
public class TestCase {
    @Id
    @Column
    private String id;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "testCase", targetEntity = TcConfig.class, fetch = FetchType.LAZY)
    private List<TcConfig> configs;
}
