package com.w2m.starship.poc.infrastructure.starship.repositories;

import com.w2m.starship.poc.infrastructure.starship.entities.Starship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing starship data in the database.
 */
public interface StarshipRepository extends JpaRepository<Starship, Long> {

	/**
	 * Finds starships by name, ignoring case.
	 *
	 * @param name the name to search for (case insensitive)
	 * @return a list of starships that contain the given name
	 */
	List<Starship> findByNameContainingIgnoreCase(String name);

}