package com.packt.hogwartsartifactsonline.artifact;

import com.packt.hogwartsartifactsonline.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArtifactService {
    private final ArtifactRepository artifactRepository;
    private final IdWorker idWorker;
    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    public Artifact findById(String id) {
        return this.artifactRepository.findById(id)
                .orElseThrow(() -> new ArtifactNotFoundException(id));
    }
    public List<Artifact> findAll() {
        return this.artifactRepository.findAll();
    }
    public Artifact save(Artifact artifact) {
        artifact.setId(idWorker.nextId()+"");
        return this.artifactRepository.save(artifact);
    }
    public Artifact update(String artifactId, Artifact artifact) {
        /*
        Artifact oldArtifact = this.findById(artifactId);
        oldArtifact.setName(artifact.getName());
        oldArtifact.setDescription(artifact.getDescription());
        oldArtifact.setImageUrl(artifact.getImageUrl());
        oldArtifact.setOwner(artifact.getOwner());
        return this.artifactRepository.save(oldArtifact);
        */
        return this.artifactRepository.findById(artifactId)
                .map(foundArtifact -> {
                    foundArtifact.setName(artifact.getName());
                    foundArtifact.setDescription(artifact.getDescription());
                    foundArtifact.setImageUrl(artifact.getImageUrl());
                    foundArtifact.setOwner(artifact.getOwner());
                    return artifactRepository.save(foundArtifact);
                }).orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }
}
