package com.packt.hogwartsartifactsonline.artifact;

import com.packt.hogwartsartifactsonline.artifact.converter.ArtifactToArtifactDtoConverter;
import com.packt.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import com.packt.hogwartsartifactsonline.system.Result;
import com.packt.hogwartsartifactsonline.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtifactController {
    private final ArtifactService artifactService;
    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
    }
    @GetMapping("/api/v1/artifacts/{artifactId}")
    public Result findArtifact(@PathVariable String artifactId) {
        Artifact artifact = artifactService.findById(artifactId);
        ArtifactDto artifactDto = artifactToArtifactDtoConverter.convert(artifact);
        return new Result(true, StatusCode.SUCCESS,"Find One Success", artifactDto);
    }
}
