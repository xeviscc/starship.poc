package com.w2m.starship.poc.domain.starship.usecases.impl;

import com.w2m.starship.poc.domain.starship.mappers.StarshipDomainMapper;
import com.w2m.starship.poc.domain.starship.models.StarshipDto;
import com.w2m.starship.poc.domain.starship.usecases.interfaces.CreateStarship;
import com.w2m.starship.poc.infrastructure.starship.entities.Starship;
import com.w2m.starship.poc.infrastructure.starship.repositories.StarshipRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the CreateStarship interface for creating starships.
 */
@Service
public class CreateStarshipImpl implements CreateStarship {

	private final StarshipRepository repository;

	/**
	 * Constructor for CreateStarshipImpl.
	 *
	 * @param repository the repository to interact with starship data
	 */
	public CreateStarshipImpl(final StarshipRepository repository) {
		this.repository = repository;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	@Transactional
	@CacheEvict(value = {"starship", "starships", "starshipsByName"}, allEntries = true)
	public StarshipDto createSarship(StarshipDto starship) {
		Starship savedStarship = repository.save(StarshipDomainMapper.INSTANCE.starshipDtoToStarship(starship));
		return StarshipDomainMapper.INSTANCE.starshipToStarshipDto(savedStarship);
	}
}
