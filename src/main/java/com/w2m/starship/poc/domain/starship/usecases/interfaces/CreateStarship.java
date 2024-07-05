package com.w2m.starship.poc.domain.starship.usecases.interfaces;

import com.w2m.starship.poc.domain.starship.models.StarshipDto;

/**
 * Interface for creating starships.
 */
public interface CreateStarship {

	/**
	 * Creates a new starship.
	 *
	 * @param starship the DTO containing the details of the starship to create
	 * @return the created starship DTO
	 */
	StarshipDto createSarship(StarshipDto starship);
}
