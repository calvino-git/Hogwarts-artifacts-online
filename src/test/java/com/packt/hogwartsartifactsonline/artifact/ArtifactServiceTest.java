package com.packt.hogwartsartifactsonline.artifact;

import com.packt.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {
    @Mock
    ArtifactRepository artifactRepository;
    @InjectMocks
    ArtifactService artifactService;
    @BeforeEach
    void setUp() {
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
}