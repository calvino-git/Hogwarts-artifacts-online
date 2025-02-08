package com.packt.hogwartsartifactsonline.artifact.dto;

import com.packt.hogwartsartifactsonline.wizard.dto.WizardDto;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record ArtifactDto(String id,
                          @NotEmpty(message = "Name is required") String name,
                          @NotEmpty(message = "Description is required") @Length(max = 100) String description,
                          String imageUrl,
                          WizardDto owner) {
}
