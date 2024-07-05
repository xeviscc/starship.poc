package com.w2m.starship.poc.domain.starship.usecases.impl;

import com.w2m.starship.poc.domain.starship.models.StarshipDto;
import com.w2m.starship.poc.infrastructure.starship.entities.Starship;
import com.w2m.starship.poc.infrastructure.starship.repositories.StarshipRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GetStarshipImplTest {

	@Mock
	private StarshipRepository repository;

	@InjectMocks
	private GetStarshipImpl service;

	@Nested
	class GetStarshipTest {

		@Test
		void givenStarshipId_returnStarship() throws Exception {
			//Given
			StarshipDto mockStarshipDto = new StarshipDto(1L, "Starship 1", "Description 1");
			Starship mockStarship = new Starship(1L, "Starship 1", "Description 1");
			Mockito.when(repository.findById(1L)).thenReturn(Optional.of(mockStarship));

			//When
			StarshipDto result = service.getStarship(1L);

			//Then
			Assertions.assertEquals(mockStarshipDto, result);
			Mockito.verify(repository, Mockito.times(1)).findById(1L);
		}

		@Test
		void givenStarshipId_returnException() {
			//Given
			Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

			//When / Then
			Exception exception = Assertions.assertThrows(Exception.class, () -> service.getStarship(1L));
			Assertions.assertEquals("Starship not found", exception.getMessage());
			Mockito.verify(repository, Mockito.times(1)).findById(1L);
		}
	}

	@Nested
	class GetAllStarshipsTest {

		@Test
		void givenPageable_returnPageOfStarships() {
			//Given
			List<StarshipDto> mockStarshipsDto = Arrays.asList(
					new StarshipDto(1L, "Starship 1", "Description 1"),
					new StarshipDto(2L, "Starship 2", "Description 2"));
			List<Starship> mockStarships = Arrays.asList(
					new Starship(1L, "Starship 1", "Description 1"),
					new Starship(2L, "Starship 2", "Description 2"));

			Pageable pageable = PageRequest.of(0, 2);
			Page<Starship> page = new PageImpl<>(mockStarships, pageable, mockStarships.size());
			Mockito.when(repository.findAll(pageable)).thenReturn(page);

			//When
			Page<StarshipDto> result = service.getAllStarships(pageable);

			//Then
			Assertions.assertEquals(2, result.getContent().size());
			Assertions.assertEquals(mockStarshipsDto, result.getContent());
			Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
		}
	}

	@Nested
	class GetStarshipsByNameContainingTest {

		@Test
		void givenWordToFilter_returnMatchingStarships() {
			//Given
			List<StarshipDto> mockStarshipsDto = Arrays.asList(
					new StarshipDto(1L, "Starship Alpha", "Description Alpha"),
					new StarshipDto(2L, "Alpha Beta Starship", "Description Beta"));
			List<Starship> mockStarships = Arrays.asList(
					new Starship(1L, "Starship Alpha", "Description Alpha"),
					new Starship(2L, "Alpha Beta Starship", "Description Beta"));

			Mockito.when(repository.findByNameContainingIgnoreCase("Alpha")).thenReturn(mockStarships);

			//When
			List<StarshipDto> result = service.getStarshipsByNameContaining("Alpha");

			//Then
			Assertions.assertEquals(2, result.size());
			Assertions.assertEquals(mockStarshipsDto, result);
			Mockito.verify(repository, Mockito.times(1)).findByNameContainingIgnoreCase("Alpha");
		}
	}
}
