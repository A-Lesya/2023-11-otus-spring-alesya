package com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.ConfigRun;
import com.bercut.autotests.tools.jenkins_job_orchestrator.models.Job;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "time_limit")
public class TimeLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "after")
    private LocalTime after;

    @Column(name = "before")
    private LocalTime before;

    @ManyToOne(fetch = FetchType.LAZY)
    private ConfigRun configRun;

    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;
}
