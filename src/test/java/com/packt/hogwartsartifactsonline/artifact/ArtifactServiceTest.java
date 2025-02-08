package com.packt.hogwartsartifactsonline.artifact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.hogwartsartifactsonline.artifact.utils.IdWorker;
import com.packt.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {
    @Mock
    ArtifactRepository artifactRepository;
    @Mock
    IdWorker idWorker;
    @InjectMocks
    ArtifactService artifactService;
    private ArrayList<Artifact> artifacts;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        artifacts = new ArrayList<>();
        artifacts = objectMapper.readValue("""
                [
                    {
                      "id": "1250808601744904191",
                      "name": "Deluminator",
                      "description": "A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.",
                      "imageUrl": "ImageUrl",
                      "owner": {
                        "id": 1,
                        "name": "Albus Dumbledore"
                      }
                    },
                    {
                      "id": "1250808601744904192",
                      "name": "Invisibility Cloak",
                      "description": "An invisibility cloak is used to make the wearer invisible.",
                      "imageUrl": "ImageUrl",
                      "owner": {
                        "id": 2,
                        "name": "Harry Potter"
                      }
                    },
                    {
                      "id": "1250808601744904193",
                      "name": "Elder Wand",
                      "description": "The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.",
                      "imageUrl": "ImageUrl",
                      "owner": {
                        "id": 1,
                        "name": "Albus Dumbledore"
                      }
                    },
                    {
                      "id": "1250808601744904194",
                      "name": "The Marauder's Map",
                      "description": "A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.",
                      "imageUrl": "ImageUrl",
                      "owner": {
                        "id": 2,
                        "name": "Harry Potter"
                      }
                    },
                    {
                      "id": "1250808601744904195",
                      "name": "The Sword Of Gryffindor",
                      "description": "A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.",
                      "imageUrl": "ImageUrl",
                      "owner": {
                        "id": 3,
                        "name": "Neville Longbottom"
                      }
                    },
                    {
                      "id": "1250808601744904196",
                      "name": "Resurrection Stone",
                      "description": "The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.",
                      "imageUrl": "ImageUrl",
                      "owner": null
                    }
                  ]""", new TypeReference<ArrayList<Artifact>>() {
        });
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //Given
        /*
            "id": "1250808601744904192",
            "name": "Invisibility Cloak",
            "description": "An invisibility cloak is used to make the wearer invisible.",
            "imageUrl": "ImageUrl",
            "owner": {
              "id": 2,
              "name": "Harry Potter",
              "numberOfArtifacts": 2
            }
         */
        Wizard wizard = new Wizard(2,"Harry Potter");
        Artifact artifact = new Artifact("1250808601744904192","Invisibility Cloak",
                "An invisibility cloak is used to make the wearer invisible.","ImageUrl",
                wizard);
        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(artifact));
        //When
        Artifact artifact1 = artifactService.findById("1250808601744904192");
        //Then
        assertThat(artifact1).isEqualTo(artifact);
        verify(artifactRepository,times(1)).findById("1250808601744904192");
    }
    @Test
    void testFindByIdFailure() {
        //Given
        given(artifactRepository.findById(Mockito.anyString())).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(() -> {
            artifactService.findById("1250808601744904192");
        });
        //Then
        assertThat(throwable).isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact with id 1250808601744904192");
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }
    @Test
    void testFindAllSuccess() {
        //Given
        given(artifactRepository.findAll()).willReturn(artifacts);
        //When
        List<Artifact> artifactsFound = artifactService.findAll();
        //Then
        assertThat(artifactsFound).isNotEmpty();
        assertThat(artifactsFound.size()).isEqualTo(artifacts.size());
        verify(artifactRepository, times(1)).findAll();
    }

    @Test
    void testSaveArtifactSuccess(){
        //Given
        Artifact expectedArtifact = new Artifact();
        expectedArtifact.setName("Calvin ILOKI");
        expectedArtifact.setDescription("Calvin ILOKI NGAKOSSO");
        expectedArtifact.setImageUrl("ImageUrl");

        given(artifactRepository.save(expectedArtifact)).willReturn(expectedArtifact);
        given(idWorker.nextId()).willReturn(123456789L);

        //When
        Artifact returnedArtifact = artifactService.save(expectedArtifact);

        //Then
        assertThat(returnedArtifact.getId()).isEqualTo("123456789");
        assertThat(returnedArtifact.getName()).isEqualTo("Calvin ILOKI");
        assertThat(returnedArtifact.getDescription()).isEqualTo("Calvin ILOKI NGAKOSSO");
        assertThat(returnedArtifact.getImageUrl()).isEqualTo("ImageUrl");
        verify(artifactRepository, times(1)).save(expectedArtifact);
    }

    @Test
    void testUpdateArtifactSuccess() {
        //Given
        Artifact expectedArtifact = new Artifact();
        expectedArtifact.setId("12508086017449041");
        expectedArtifact.setName("Calvin ILOKI");
        expectedArtifact.setDescription("Java Software Engineer");
        expectedArtifact.setImageUrl("ImageUrl");

        Artifact artifact = new Artifact();
        artifact.setId("12508086017449041");
        artifact.setName("Calvin ILOKI");
        artifact.setDescription("Java Software Developper");
        artifact.setImageUrl("ImageUrl");

        given(artifactRepository.findById("12508086017449041")).willReturn(Optional.of(artifact));
        given(artifactRepository.save(expectedArtifact)).willReturn(expectedArtifact);

        //When
        Artifact artifact1 = artifactService.update("12508086017449041", expectedArtifact);
        //Then
        assertThat(artifact1.getId()).isEqualTo(expectedArtifact.getId());
        assertThat(artifact1.getName()).isEqualTo(expectedArtifact.getName());
        assertThat(artifact1.getDescription()).isEqualTo(expectedArtifact.getDescription());
        assertThat(artifact1.getImageUrl()).isEqualTo(expectedArtifact.getImageUrl());

        verify(artifactRepository, times(1)).findById("12508086017449041");
        verify(artifactRepository, times(1)).save(artifact);

    }

    @Test
    void testUpdateArtifactNotFound() {
        //Given
        Artifact artifact = new Artifact();
        artifact.setId("12508086017449041");
        artifact.setName("Calvin ILOKI");
        artifact.setDescription("Java Software Developper");
        artifact.setImageUrl("ImageUrl");

        given(artifactRepository.findById("12508086017449041")).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> artifactService.update("12508086017449041", artifact))
                .hasMessage("Could not find artifact with id " + artifact.getId());
        //Then
        verify(artifactRepository, times(1)).findById("12508086017449041");
        verify(artifactRepository, times(0)).save(artifact);
    }
}