package com.packt.hogwartsartifactsonline.artifact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.hogwartsartifactsonline.system.StatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {
    @MockitoBean
    private ArtifactService artifactService;
    @Autowired
    MockMvc mockMvc;
    List<Artifact> artifacts;

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
                  ]""", new TypeReference<List<Artifact>>() {
        });

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findArtifact() throws Exception {
        //Given
        given(artifactService.findById("1250808601744904191")).willReturn(artifacts.get(0));
        //When and then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data.name").value("Deluminator"));
    }

    @Test
    void findArtifactNotFound() throws Exception {
        String id = "1250808601744904191";
        //Given
        given(artifactService.findById(Mockito.anyString())).willThrow(new ArtifactNotFoundException(id));
        //When and then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifacts/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id " + id))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}