package com.packt.hogwartsartifactsonline.artifact;

import com.packt.hogwartsartifactsonline.artifact.converter.ArtifactDtoToArtifactConverter;
import com.packt.hogwartsartifactsonline.artifact.converter.ArtifactToArtifactDtoConverter;
import com.packt.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import com.packt.hogwartsartifactsonline.system.Result;
import com.packt.hogwartsartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtifactController {
    private final ArtifactService artifactService;
    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;
    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }
    @GetMapping("/api/v1/artifacts/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {
        Artifact artifact = artifactService.findById(artifactId);
        ArtifactDto artifactDto = artifactToArtifactDtoConverter.convert(artifact);
        return new Result(true, StatusCode.SUCCESS,"Find One Success", artifactDto);
    }

    @GetMapping("/api/v1/artifacts")
    public Result findAllArtifacts() {
        List<Artifact> artifacts = artifactService.findAll();
        if (artifacts.isEmpty()) {
            return new Result(false,StatusCode.NOT_FOUND,"Could not find any artifacts");
        }
        //Convert a List<Artifact> to List<ArtifactDto>
        List<ArtifactDto> artifactsDto = artifacts.stream().map(artifactToArtifactDtoConverter::convert).collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS,"Find All Success", artifactsDto);
    }

    @PostMapping("/api/v1/artifacts")
    public Result createArtifact(@Valid @RequestBody ArtifactDto artifactDto) {
        Artifact artifact = artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact artifactSaved = artifactService.save(artifact);
        ArtifactDto artifactDtoSaved = artifactToArtifactDtoConverter.convert(artifactSaved);
        return new Result(true, StatusCode.SUCCESS,"Artifact Created successfully", artifactDtoSaved);
    }
    @PutMapping("/api/v1/artifacts/{artifactId}")
    public Result updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto artifactDto) {
        Artifact updateArtifact = artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact artifactUpdated = artifactService.update(artifactId, updateArtifact);
        ArtifactDto artifactDtoUpdated = artifactToArtifactDtoConverter.convert(artifactUpdated);
        return new Result(true, StatusCode.SUCCESS,"Artifact Updated successfully", artifactDtoUpdated);
    }
}
