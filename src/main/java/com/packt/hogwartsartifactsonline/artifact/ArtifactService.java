package com.packt.hogwartsartifactsonline.artifact;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArtifactService {
    private final ArtifactRepository artifactRepository;
    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    public Artifact findById(String id) {
        return this.artifactRepository.findById(id)
                .orElseThrow(() -> new ArtifactNotFoundException(id));
    }
}
