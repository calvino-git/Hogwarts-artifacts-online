package com.packt.hogwartsartifactsonline.wizard.converter;

import com.packt.hogwartsartifactsonline.wizard.Wizard;
import com.packt.hogwartsartifactsonline.wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, Wizard> {
    @Override
    public Wizard convert(WizardDto source) {
        return new Wizard(source.id(),source.name());
    }
}
