package com.bercut.autotests.tools.jenkins_job_orchestrator.models;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement.TimeLimit;
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

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "config_run")
public class ConfigRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private TcConfig tcConfig;

    @OneToMany(mappedBy = "configRun", targetEntity = TimeLimit.class, fetch = FetchType.LAZY)
    private List<TimeLimit> timeLimits;

}
