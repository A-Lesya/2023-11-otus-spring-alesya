package com.bercut.autotests.tools.jenkins_job_orchestrator.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "config")
public class TcConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "number")
    private Integer number;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "tcConfig", targetEntity = ConfigRun.class, fetch = FetchType.LAZY)
    private List<ConfigRun> runs;

    @ManyToOne(fetch = FetchType.LAZY)
    private TestCase testCase;
}
