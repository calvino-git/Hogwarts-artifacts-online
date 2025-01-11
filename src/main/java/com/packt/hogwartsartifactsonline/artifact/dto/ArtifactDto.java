package com.packt.hogwartsartifactsonline.artifact.dto;

import com.packt.hogwartsartifactsonline.wizard.dto.WizardDto;

public record ArtifactDto(String id, String name, String description, String imageUrl, WizardDto owner) {
}
