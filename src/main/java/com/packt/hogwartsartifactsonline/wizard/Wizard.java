package com.packt.hogwartsartifactsonline.wizard;

import com.packt.hogwartsartifactsonline.artifact.Artifact;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Wizard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Artifact> artifacts = new ArrayList<>();

    public Wizard() {
    }
    public Wizard(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wizard wizard = (Wizard) o;
        return Objects.equals(id, wizard.id) && Objects.equals(name, wizard.name) && Objects.equals(artifacts, wizard.artifacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artifacts);
    }

    public Integer getNumberOfArtifacts() {
        return artifacts.size();
    }
}
