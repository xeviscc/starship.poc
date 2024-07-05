package com.w2m.starship.poc.domain.starship.usecases.interfaces;

/**
 * Interface for deleting starships.
 */
public interface DeleteStarship {

	/**
	 * Deletes a starship by its ID.
	 *
	 * @param id the ID of the starship to delete
	 */
	void deleteStarship(Long id);
} 
 