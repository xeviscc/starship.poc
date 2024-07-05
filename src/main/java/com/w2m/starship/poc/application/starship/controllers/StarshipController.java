package com.w2m.starship.poc.application.starship.controllers;

import com.w2m.starship.poc.application.starship.mappers.StarshipAppMapper;
import com.w2m.starship.poc.application.starship.models.ErrorResponse;
import com.w2m.starship.poc.application.starship.models.StarshipRequest;
import com.w2m.starship.poc.application.starship.models.StarshipResponse;
import com.w2m.starship.poc.domain.starship.models.StarshipDto;
import com.w2m.starship.poc.domain.starship.usecases.interfaces.CreateStarship;
import com.w2m.starship.poc.domain.starship.usecases.interfaces.DeleteStarship;
import com.w2m.starship.poc.domain.starship.usecases.interfaces.GetStarship;
import com.w2m.starship.poc.domain.starship.usecases.interfaces.ModifyStarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing starships. It contains all CRUD operations.
 */
@RestController
@RequestMapping(value = "/api/starships")
public class StarshipController {

	private final GetStarship getStarship;
	private final CreateStarship createStarship;
	private final DeleteStarship deleteStarship;
	private final ModifyStarship modifyStarship;

	/**
	 * Constructor for StarshipController.
	 *
	 * @param getStarship    the service to get starships
	 * @param createStarship the service to create starships
	 * @param deleteStarship the service to delete starships
	 * @param modifyStarship the service to modify starships
	 */
	public StarshipController(final GetStarship getStarship, final CreateStarship createStarship,
	                          final DeleteStarship deleteStarship, final ModifyStarship modifyStarship) {
		this.getStarship = getStarship;
		this.createStarship = createStarship;
		this.deleteStarship = deleteStarship;
		this.modifyStarship = modifyStarship;
	}

	/**
	 * GET /{starshipId} : Get a starship by its ID.
	 *
	 * @param starshipId the ID of the starship to retrieve
	 * @return the ResponseEntity with status 200 (OK) and the starship, or with status 404 (Not Found)
	 */
	@GetMapping(value = "/{starshipId}")
	public ResponseEntity<?> getStarshipById(@PathVariable final Long starshipId) {
		try {
			StarshipDto starship = getStarship.getStarship(starshipId);
			return ResponseEntity.ok(StarshipAppMapper.INSTANCE.starshipDtoToStarshipResponse(starship));
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(String.format("Starship with ID %s not found", starshipId)), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * GET / : Get all starships with pagination.
	 *
	 * @param page the page number to retrieve
	 * @param size the number of records per page
	 * @return the ResponseEntity with status 200 (OK) and the list of starships
	 */
	@GetMapping
	public ResponseEntity<Page<StarshipResponse>> getAllStarshipsPaginated(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<StarshipDto> starships = getStarship.getAllStarships(PageRequest.of(page, size));
		return ResponseEntity.ok(StarshipAppMapper.INSTANCE.pageStarshipDtoToStarshipResponse(starships));
	}

	/**
	 * GET /search : Get all starships whose names contain a given string.
	 *
	 * @param name the string to search for in starship names
	 * @return the ResponseEntity with status 200 (OK) and the list of matching starships
	 */
	@GetMapping("/search")
	public ResponseEntity<List<StarshipResponse>> getStarshipsByNameContaining(@RequestParam String name) {
		List<StarshipDto> starships = getStarship.getStarshipsByNameContaining(name);
		return ResponseEntity.ok(StarshipAppMapper.INSTANCE.listStarshipDtoToStarshipResponse(starships));
	}

	/**
	 * POST / : Create a new starship.
	 *
	 * @param starshipRequest the request body containing the starship details
	 * @return the ResponseEntity with status 200 (OK) and the created starship
	 */
	@PostMapping(value = "")
	public ResponseEntity<?> createStarship(@RequestBody final StarshipRequest starshipRequest) {
		StarshipDto starship = createStarship.createSarship(StarshipAppMapper.INSTANCE.starshipRequestToStarshipDto(starshipRequest));
		return ResponseEntity.ok(StarshipAppMapper.INSTANCE.starshipDtoToStarshipResponse(starship));
	}

	/**
	 * DELETE /{starshipId} : Delete a starship by its ID.
	 *
	 * @param starshipId the ID of the starship to delete
	 * @return the ResponseEntity with status 200 (OK) if the deletion was successful, or with status 404 (Not Found)
	 */
	@DeleteMapping(value = "/{starshipId}")
	public ResponseEntity<?> deleteStarship(@PathVariable final Long starshipId) {
		try {
			deleteStarship.deleteStarship(starshipId);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(String.format("Starship with ID %s not found", starshipId)), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * PUT /{starshipId} : Modify an existing starship.
	 *
	 * @param starshipRequest the request body containing the updated starship details
	 * @param starshipId      the ID of the starship to modify
	 * @return the ResponseEntity with status 200 (OK) and the modified starship
	 */
	@PutMapping(value = "/{starshipId}")
	public ResponseEntity<?> modifyStarship(@RequestBody final StarshipRequest starshipRequest, @PathVariable final Long starshipId) {
		StarshipDto starshipToModify = StarshipAppMapper.INSTANCE.starshipRequestToStarshipDto(starshipRequest);
		starshipToModify.setId(starshipId);
		starshipToModify.setName(starshipToModify.getName());
		starshipToModify.setDescription(starshipToModify.getDescription());
		StarshipDto result = modifyStarship.modifySarship(starshipToModify);
		return ResponseEntity.ok(StarshipAppMapper.INSTANCE.starshipDtoToStarshipResponse(result));
	}
}
