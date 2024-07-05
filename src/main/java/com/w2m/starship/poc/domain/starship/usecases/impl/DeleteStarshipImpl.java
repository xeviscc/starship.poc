package com.w2m.starship.poc.domain.starship.usecases.impl;

import com.w2m.starship.poc.domain.starship.usecases.interfaces.DeleteStarship;
import com.w2m.starship.poc.infrastructure.starship.repositories.StarshipRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the DeleteStarship interface for deleting starships.
 */
@Service
public class DeleteStarshipImpl implements DeleteStarship {

	private final StarshipRepository repository;

	/**
	 * Constructor for DeleteStarshipImpl.
	 *
	 * @param repository the repository to interact with starship data
	 */
	public DeleteStarshipImpl(final StarshipRepository repository) {
		this.repository = repository;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	@Transactional
	@CacheEvict(value = {"starship", "starships", "starshipsByName"}, allEntries = true)
	public void deleteStarship(Long id) {
		repository.deleteById(id);
	}
}
