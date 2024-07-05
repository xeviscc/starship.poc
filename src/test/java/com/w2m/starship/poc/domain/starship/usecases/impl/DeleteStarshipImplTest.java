package com.w2m.starship.poc.domain.starship.usecases.impl;

import com.w2m.starship.poc.infrastructure.starship.repositories.StarshipRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteStarshipImplTest {

	@Mock
	private StarshipRepository repository;

	@InjectMocks
	private DeleteStarshipImpl service;

	@Nested
	class DeleteStarshipTest {

		@Test
		void givenStarshipId_deletesIt() {
			//Given
			Long starshipId = 1L;

			//When
			service.deleteStarship(starshipId);

			//Then
			Mockito.verify(repository, Mockito.times(1)).deleteById(starshipId);
		}
	}
}
