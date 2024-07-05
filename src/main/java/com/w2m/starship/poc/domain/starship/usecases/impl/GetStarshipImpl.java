package com.w2m.starship.poc.domain.starship.usecases.impl;

import com.w2m.starship.poc.domain.starship.mappers.StarshipDomainMapper;
import com.w2m.starship.poc.domain.starship.models.StarshipDto;
import com.w2m.starship.poc.domain.starship.usecases.interfaces.GetStarship;
import com.w2m.starship.poc.infrastructure.starship.entities.Starship;
import com.w2m.starship.poc.infrastructure.starship.repositories.StarshipRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the GetStarship interface for retrieving starships.
 */
@Service
public class GetStarshipImpl implements GetStarship {

	private final StarshipRepository repository;

	/**
	 * Constructor for GetStarshipImpl.
	 *
	 * @param repository the repository to interact with starship data
	 */
	public GetStarshipImpl(final StarshipRepository repository) {
		this.repository = repository;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "starship", key = "#id")
	public StarshipDto getStarship(Long id) throws Exception {
		Starship starship = repository.findById(id).orElseThrow(() -> new Exception("Starship not found"));
		return StarshipDomainMapper.INSTANCE.starshipToStarshipDto(starship);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "starships")
	public Page<StarshipDto> getAllStarships(Pageable pageable) {
		Page<Starship> starships = repository.findAll(pageable);
		return StarshipDomainMapper.INSTANCE.pageStarshipToStarshipDto(starships);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "starshipsByName", key = "#name")
	public List<StarshipDto> getStarshipsByNameContaining(String name) {
		List<Starship> starships = repository.findByNameContainingIgnoreCase(name);
		return StarshipDomainMapper.INSTANCE.listStarshipToStarshipDto(starships);
	}
}
