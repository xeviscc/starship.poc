package com.w2m.starship.poc.domain.starship.usecases.impl;

import com.w2m.starship.poc.domain.starship.mappers.StarshipDomainMapper;
import com.w2m.starship.poc.domain.starship.models.StarshipDto;
import com.w2m.starship.poc.domain.starship.usecases.interfaces.ModifyStarship;
import com.w2m.starship.poc.infrastructure.starship.entities.Starship;
import com.w2m.starship.poc.infrastructure.starship.repositories.StarshipRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the ModifyStarship interface for modifiyng starships.
 */
@Service
public class ModifyStarshipImpl implements ModifyStarship {

	private final StarshipRepository repository;

	/**
	 * Constructor for ModifyStarshipImpl.
	 *
	 * @param repository the repository to interact with starship data
	 */
	public ModifyStarshipImpl(final StarshipRepository repository) {
		this.repository = repository;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	@Transactional
	@CacheEvict(value = {"starship", "starships", "starshipsByName"}, allEntries = true)
	public StarshipDto modifySarship(StarshipDto starship) {
		Starship savedStarship = repository.save(StarshipDomainMapper.INSTANCE.starshipDtoToStarship(starship));
		return StarshipDomainMapper.INSTANCE.starshipToStarshipDto(savedStarship);
	}
}
