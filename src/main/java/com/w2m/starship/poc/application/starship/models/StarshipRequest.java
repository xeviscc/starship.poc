package com.w2m.starship.poc.application.starship.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarshipRequest {
	private String name;
	private String description;
}