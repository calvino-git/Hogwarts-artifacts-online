package com.packt.hogwartsartifactsonline.artifact.converter;

import com.packt.hogwartsartifactsonline.artifact.Artifact;
import com.packt.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import com.packt.hogwartsartifactsonline.wizard.converter.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;
    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }
    @Override
    public ArtifactDto convert(Artifact source) {
        return new ArtifactDto(source.getId(), source.getName(), source.getDescription(), source.getImageUrl(),
                source.getOwner()!=null?wizardToWizardDtoConverter.convert(source.getOwner()):null);
    }
}
