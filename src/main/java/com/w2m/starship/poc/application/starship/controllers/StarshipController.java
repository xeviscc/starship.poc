package com.w2m.starship.poc.application.starship.controllers;

import com.w2m.starship.poc.application.starship.models.ErrorResponse;
import com.w2m.starship.poc.application.starship.models.StarshipResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/starship")
public class StarshipController {

    @GetMapping(value = "/{starshipId}")
    public ResponseEntity<?> getStarship(@PathVariable final String starshipId) {
        
        //TODO: Go to service
        if("1".equals(starshipId)) {
            return new ResponseEntity<>(new StarshipResponse("name", "description"), HttpStatus.OK);    
        } else {
            return new ResponseEntity<>(new ErrorResponse(String.format("Starship with ID %s not found", starshipId)), HttpStatus.NOT_FOUND);
        }
        
        
    }
}
