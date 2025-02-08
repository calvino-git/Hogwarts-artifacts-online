package com.packt.hogwartsartifactsonline.artifact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import com.packt.hogwartsartifactsonline.system.StatusCode;
import org.hamcrest.Matchers;
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
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {
    @MockitoBean
    private ArtifactService artifactService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    List<Artifact> artifacts;
    @Autowired
    private ArtifactRepository artifactRepository;

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
    void testFindArtifactByIdSuccess() throws Exception {
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
    void testFindArtifactByIdNotFound() throws Exception {
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

    @Test
    void testFindAllArtifactsSuccess() throws Exception {
        given(artifactService.findAll()).willReturn(artifacts);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data[0].name").value("Deluminator"))
                .andExpect(jsonPath("$.data[1].id").value("1250808601744904192"))
                .andExpect(jsonPath("$.data[1].name").value("Invisibility Cloak"));

    }

    @Test
    void testFindAllArtifactsEmpty() throws Exception {
        given(artifactService.findAll()).willReturn(List.of());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find any artifacts"));
    }

    @Test
    void testCreateArtifactSuccess() throws Exception {
        //Given
        ArtifactDto artifactDto = new ArtifactDto(null, "Calvin ILOKI", "Calvin ILOKI NGAKOSSO", "ImageUrl", null);
        String jsonArtifact = objectMapper.writeValueAsString(artifactDto);

        Artifact expectedArtifact = new Artifact();
        expectedArtifact.setId("1250808601744904197");
        expectedArtifact.setName("Calvin ILOKI");
        expectedArtifact.setDescription("Calvin ILOKI NGAKOSSO");
        expectedArtifact.setImageUrl("ImageUrl");

        given(artifactService.save(Mockito.any(Artifact.class))).willReturn(expectedArtifact);
        //When and then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/artifacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonArtifact))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Artifact Created successfully"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904197"))
                .andExpect(jsonPath("$.data.name").value("Calvin ILOKI"))
                .andExpect(jsonPath("$.data.description").value("Calvin ILOKI NGAKOSSO"))
                .andExpect(jsonPath("$.data.imageUrl").value("ImageUrl"));
        verify(artifactService, times(1)).save(Mockito.any(Artifact.class));

    }

    @Test
    void testCreateArtifactFailure() throws Exception {
        //Given
        ArtifactDto artifactDto = new ArtifactDto(null, "Calvin ILOKI", "Calvin ILOKI NGAKOSSO", "ImageUrl", null);
        String jsonArtifact = objectMapper.writeValueAsString(artifactDto);

        Artifact expectedArtifact = new Artifact();
        expectedArtifact.setId("1250808601744904197");
        expectedArtifact.setName("Calvin ILOKI");
        expectedArtifact.setDescription("Calvin ILOKI NGAKOSSO");
        expectedArtifact.setImageUrl("ImageUrl");

        given(artifactService.save(Mockito.any(Artifact.class))).willReturn(expectedArtifact);
        //When and then
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/artifacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonArtifact))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Artifact Created successfully"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904197"))
                .andExpect(jsonPath("$.data.name").value("Calvin ILOKI"))
                .andExpect(jsonPath("$.data.description").value("Calvin ILOKI NGAKOSSO"))
                .andExpect(jsonPath("$.data.imageUrl").value("ImageUrl"));
        verify(artifactService, times(1)).save(Mockito.any(Artifact.class));

    }

    @Test
    void testUpdateArtifactSuccess() throws Exception {
        //Given
        ArtifactDto expectedArtifactDto = new ArtifactDto("1250808601744904197", "Calvin ILOKI", "Java Backend Developer", "ImageUrl", null);
        String jsonArtifact = objectMapper.writeValueAsString(expectedArtifactDto);

        Artifact expectedArtifact = new Artifact();
        expectedArtifact.setId("1250808601744904197");
        expectedArtifact.setName("Calvin ILOKI");
        expectedArtifact.setDescription("Java Backend Developer");
        expectedArtifact.setImageUrl("ImageUrl");

        given(artifactService.update(Mockito.eq(expectedArtifactDto.id()), Mockito.any(Artifact.class)))
                .willReturn(expectedArtifact);
        //When and then
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/artifacts/" + expectedArtifactDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonArtifact))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Artifact Updated successfully"))
                .andExpect(jsonPath("$.data.id").value(expectedArtifactDto.id()))
                .andExpect(jsonPath("$.data.name").value(expectedArtifactDto.name()))
                .andExpect(jsonPath("$.data.description").value(expectedArtifactDto.description()))
                .andExpect(jsonPath("$.data.imageUrl").value(expectedArtifactDto.imageUrl()));
        verify(artifactService, times(1)).update(Mockito.eq(expectedArtifactDto.id()), Mockito.any(Artifact.class));

    }

    @Test
    void testUpdateArtifactErrorWithNonExistentId() throws Exception {
        //Given
        ArtifactDto expectedArtifactDto = new ArtifactDto("1250808601744904197", "Calvin ILOKI", "Java Backend Developer", "ImageUrl", null);
        String jsonArtifact = objectMapper.writeValueAsString(expectedArtifactDto);

        given(artifactService.update(Mockito.eq(expectedArtifactDto.id()), Mockito.any(Artifact.class)))
                .willThrow(new ArtifactNotFoundException(expectedArtifactDto.id()));
        //When and then
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/artifacts/" + expectedArtifactDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonArtifact))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id " + expectedArtifactDto.id()))
                .andExpect(jsonPath("$.data").isEmpty());
        verify(artifactService, times(1)).update(Mockito.eq(expectedArtifactDto.id()), Mockito.any(Artifact.class));

    }
}