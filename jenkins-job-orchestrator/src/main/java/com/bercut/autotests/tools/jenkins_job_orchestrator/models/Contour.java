package com.bercut.autotests.tools.jenkins_job_orchestrator.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contour")
public class Contour {
    @Id
    @Column
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contour contour)) {
            return false;
        }
        return Objects.equals(id, contour.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
