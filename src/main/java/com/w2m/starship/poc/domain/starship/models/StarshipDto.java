package com.w2m.starship.poc.domain.starship.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarshipDto {
	private Long id;
	private String name;
	private String description;
}