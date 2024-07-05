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

@ExtendWith(MockitoExtension.class)
class ModifyStarshipImplTest {

	@Mock
	private StarshipRepository repository;

	@InjectMocks
	private ModifyStarshipImpl service;

	@Nested
	class ModifySarshipTest {

		@Test
		void givenStarship_returnCreatedStarship() {
			//Given
			StarshipDto mockStarshipDto = new StarshipDto(1L, "Modified Starship", "Modified Description");
			Starship mockStarship = new Starship(1L, "Modified Starship", "Modified Description");
			Mockito.when(repository.save(Mockito.any(Starship.class))).thenReturn(mockStarship);

			//When
			StarshipDto result = service.modifySarship(mockStarshipDto);

			//Then
			Assertions.assertEquals(mockStarshipDto, result);
			Mockito.verify(repository, Mockito.times(1)).save(mockStarship);
		}
	}
}
