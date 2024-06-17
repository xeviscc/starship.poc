package com.w2m.starship.poc.application.starship.controllers;

import jdk.jfr.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class StarshipControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class GetStarshipTest {

        @Test
        @WithMockUser
        void givenExistingStarshipId_returnsStarship() throws Exception {

            //// @formatter:off
            mockMvc.perform(MockMvcRequestBuilders.get("/api/starship/{starshipId}", 1)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
                    ;
            // @formatter:on

        }

        @Test
        @WithMockUser
        void givenNonExistingStarshipId_returnsStarship() throws Exception {

            //// @formatter:off
            mockMvc.perform(MockMvcRequestBuilders.get("/api/starship/{starshipId}", 999)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Starship with ID 999 not found"))
            ;
            // @formatter:on

        }
    }
}
