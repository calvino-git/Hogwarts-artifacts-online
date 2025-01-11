package com.packt.hogwartsartifactsonline.system;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.hogwartsartifactsonline.artifact.Artifact;
import com.packt.hogwartsartifactsonline.artifact.ArtifactRepository;
import com.packt.hogwartsartifactsonline.wizard.WizardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DBDataInitializer implements CommandLineRunner {
    private final ArtifactRepository artifactRepository;
    private final WizardRepository wizardRepository;

    public DBDataInitializer(ArtifactRepository artifactRepository, WizardRepository wizardRepository) {
        this.artifactRepository = artifactRepository;
        this.wizardRepository = wizardRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Artifact> artifacts = objectMapper.readValue("[\n" +
                "    {\n" +
                "      \"id\": \"1250808601744904191\",\n" +
                "      \"name\": \"Deluminator\",\n" +
                "      \"description\": \"A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.\",\n" +
                "      \"imageUrl\": \"ImageUrl\",\n" +
                "      \"owner\": {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Albus Dumbledore\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"1250808601744904192\",\n" +
                "      \"name\": \"Invisibility Cloak\",\n" +
                "      \"description\": \"An invisibility cloak is used to make the wearer invisible.\",\n" +
                "      \"imageUrl\": \"ImageUrl\",\n" +
                "      \"owner\": {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Harry Potter\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"1250808601744904193\",\n" +
                "      \"name\": \"Elder Wand\",\n" +
                "      \"description\": \"The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.\",\n" +
                "      \"imageUrl\": \"ImageUrl\",\n" +
                "      \"owner\": {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Albus Dumbledore\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"1250808601744904194\",\n" +
                "      \"name\": \"The Marauder's Map\",\n" +
                "      \"description\": \"A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.\",\n" +
                "      \"imageUrl\": \"ImageUrl\",\n" +
                "      \"owner\": {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Harry Potter\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"1250808601744904195\",\n" +
                "      \"name\": \"The Sword Of Gryffindor\",\n" +
                "      \"description\": \"A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.\",\n" +
                "      \"imageUrl\": \"ImageUrl\",\n" +
                "      \"owner\": {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"Neville Longbottom\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"1250808601744904196\",\n" +
                "      \"name\": \"Resurrection Stone\",\n" +
                "      \"description\": \"The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.\",\n" +
                "      \"imageUrl\": \"ImageUrl\",\n" +
                "      \"owner\": null\n" +
                "    }\n" +
                "  ]", new TypeReference<List<Artifact>>() {
        });
        artifacts.forEach(artifact -> {
            if(artifact.getOwner() != null){
                wizardRepository.save(artifact.getOwner());
            }
            artifactRepository.save(artifact);
        });
    }
}
