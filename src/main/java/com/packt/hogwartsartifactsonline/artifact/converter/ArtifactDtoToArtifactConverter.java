package com.packt.hogwartsartifactsonline.artifact.converter;

import com.packt.hogwartsartifactsonline.artifact.Artifact;
import com.packt.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import com.packt.hogwartsartifactsonline.wizard.converter.WizardDtoToWizardConverter;
import com.packt.hogwartsartifactsonline.wizard.converter.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto, Artifact> {
    private final WizardDtoToWizardConverter wizardDtoToWizardConverter;

    public ArtifactDtoToArtifactConverter( WizardDtoToWizardConverter wizardDtoToWizardConverter) {
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
    }
    @Override
    public Artifact convert(ArtifactDto source) {
        return new Artifact(
                source.id(),
                source.name(),
                source.description(),
                source.imageUrl(),
                source.owner()!=null?wizardDtoToWizardConverter.convert(source.owner()):null);
    }
}
