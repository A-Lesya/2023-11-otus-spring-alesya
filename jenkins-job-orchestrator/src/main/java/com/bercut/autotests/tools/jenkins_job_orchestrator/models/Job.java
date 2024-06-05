package com.bercut.autotests.tools.jenkins_job_orchestrator.models;

import com.bercut.autotests.tools.jenkins_job_orchestrator.models.requirement.TimeLimit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "path")
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contour_id")
    private Contour contour;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = TestCase.class, fetch = FetchType.LAZY)
    @JoinTable(name = "tc_job", joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "tc_id"))
    private List<TestCase> testCases;

    @OneToMany(mappedBy = "job", targetEntity = TimeLimit.class, fetch = FetchType.LAZY)
    private List<TimeLimit> timeLimits;
}
