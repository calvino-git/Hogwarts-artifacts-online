package com.packt.hogwartsartifactsonline.artifact;

import com.packt.hogwartsartifactsonline.wizard.Wizard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Artifact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    @ManyToOne
    private Wizard owner;

    public Artifact() {
    }
    public Artifact(String id, String name, String description, String imageUrl, Wizard owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Wizard getOwner() {
        return owner;
    }

    public void setOwner(Wizard owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return Objects.equals(id, artifact.id) && Objects.equals(name, artifact.name) && Objects.equals(description, artifact.description) && Objects.equals(imageUrl, artifact.imageUrl) && Objects.equals(owner, artifact.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, owner);
    }
}
