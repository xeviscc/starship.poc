package com.w2m.starship.poc.application.starship.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class StarshipControllerTest {

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
	                  RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)).build();
	}

	@Nested
	class GetStarshipByIdTest {

		@Test
		@WithMockUser
		void givenExistingStarshipId_returnsStarship() throws Exception {

			// @formatter:off
            mockMvc.perform(RestDocumentationRequestBuilders.get("/api/starships/{starshipId}", 1)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("USS Enterprise"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("The flagship of Starfleet across several generations, known for its exploration and defense missions."))
		            .andDo(MockMvcRestDocumentation.document("crud-get-example",
				            RequestDocumentation.pathParameters(
						            RequestDocumentation.parameterWithName("starshipId").description("The id of the starship to retrieve")
				            )))
                    ;
            // @formatter:on

		}

		@Test
		@WithMockUser
		void givenNonExistingStarshipId_returnsStarship() throws Exception {

			// @formatter:off
            mockMvc.perform(RestDocumentationRequestBuilders.get("/api/starships/{starshipId}", 999)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Starship with ID 999 not found"))
		            .andDo(MockMvcRestDocumentation.document("crud-get-example",
				            RequestDocumentation.pathParameters(
						            RequestDocumentation.parameterWithName("starshipId").description("The id of the starship to retrieve")
				            )))
            ;
            // @formatter:on

		}
	}

	@Nested
	class GetAllStarshipsPaginatedTest {

		@Test
		@WithMockUser
		void givenPageAndSize_returnsStarships() throws Exception {

			// @formatter:off
            mockMvc.perform(RestDocumentationRequestBuilders.get("/api/starships?page={page}&size={size}", 0, 2)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("USS Enterprise"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description").value("The flagship of Starfleet across several generations, known for its exploration and defense missions."))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("USS Voyager"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].description").value("A ship lost in the Delta Quadrant attempting to return to Earth, commanded by Captain Janeway."))
		            .andDo(MockMvcRestDocumentation.document("crud-get-paginated-example",
				            RequestDocumentation.queryParameters(
						            RequestDocumentation.parameterWithName("page").description("The number of the page to start the list of starships"),
						            RequestDocumentation.parameterWithName("size").description("The size of the page for the list of starships")
				            )))
            ;
            // @formatter:on

		}
	}

	@Nested
	class GetStarshipsByNameContainingTest {

		@Test
		@WithMockUser
		void givenName_returnsMatchingStarships() throws Exception {

			// @formatter:off
            mockMvc.perform(RestDocumentationRequestBuilders.get("/api/starships/search?name={name}", "USS")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("USS Enterprise"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("The flagship of Starfleet across several generations, known for its exploration and defense missions."))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("USS Voyager"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("A ship lost in the Delta Quadrant attempting to return to Earth, commanded by Captain Janeway."))
		            .andDo(MockMvcRestDocumentation.document("crud-get-matching-name-example",
				            RequestDocumentation.queryParameters(
						            RequestDocumentation.parameterWithName("name").description("The string contained in the name of the starships to retrive")
				            )))
            ;
            // @formatter:on

		}
	}

	@Nested
	class CreateStarshipTest {

		@Test
		@WithMockUser
		void givenStarship_createsIt() throws Exception {

			// @formatter:off
            mockMvc.perform(RestDocumentationRequestBuilders.post("/api/starships")
                            .content("{\"name\":\"hola\",\"description\":\"adeu\"}")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("hola"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("adeu"))
		            .andDo(MockMvcRestDocumentation.document("crud-create-example"))
            ;
            // @formatter:on

		}
	}

	@Nested
	class DeleteStarshipTest {

		@Test
		@WithMockUser
		void givenStarshipId_deletesIt() throws Exception {

			// @formatter:off
            mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/starships/{starshipId}", 40)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
		            .andDo(MockMvcRestDocumentation.document("crud-delete-example",
				            RequestDocumentation.pathParameters(
						            RequestDocumentation.parameterWithName("starshipId").description("The id of the starship to delete")
				            )))
            ;
            // @formatter:on

		}
	}

	@Nested
	class ModifyStarshipTest {

		@Test
		@WithMockUser
		void givenStarshipToModify_modifiesIt() throws Exception {

			// @formatter:off
            mockMvc.perform(RestDocumentationRequestBuilders.put("/api/starships/{starshipId}", 40)
                            .content("{\"name\":\"hola\",\"description\":\"adeu\"}")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("hola"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("adeu"))
		            .andDo(MockMvcRestDocumentation.document("crud-modify-example",
				            RequestDocumentation.pathParameters(
						            RequestDocumentation.parameterWithName("starshipId").description("The id of the starship to modify")
				            )))
            ;
            // @formatter:on

		}
	}
}
