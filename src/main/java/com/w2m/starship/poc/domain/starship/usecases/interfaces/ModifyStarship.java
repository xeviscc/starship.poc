package com.w2m.starship.poc.domain.starship.usecases.interfaces;

import com.w2m.starship.poc.domain.starship.models.StarshipDto;

/**
 * Interface for modifying starships.
 */
public interface ModifyStarship {

	/**
	 * Modifies an existing starship.
	 *
	 * @param starship the DTO containing the details of the starship to modify
	 * @return the modified starship DTO
	 */
	StarshipDto modifySarship(StarshipDto starship);
}
