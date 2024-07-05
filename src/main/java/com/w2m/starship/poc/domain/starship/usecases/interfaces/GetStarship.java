package com.w2m.starship.poc.domain.starship.usecases.interfaces;

import com.w2m.starship.poc.domain.starship.models.StarshipDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface for retrieving starships.
 */
public interface GetStarship {

	/**
	 * Retrieves a starship by its ID.
	 *
	 * @param id the ID of the starship to retrieve
	 * @return the starship DTO
	 *
	 * @throws Exception if the starship is not found
	 */
	StarshipDto getStarship(Long id) throws Exception;

	/**
	 * Retrieves a paginated list of all starships.
	 *
	 * @param pageable the pagination information
	 * @return a paginated list of starship DTOs
	 */
	Page<StarshipDto> getAllStarships(Pageable pageable);

	/**
	 * Retrieves a list of starships that contain the given name.
	 *
	 * @param name the name to search for
	 * @return a list of starship DTOs that contain the given name
	 */
	List<StarshipDto> getStarshipsByNameContaining(String name);
}
