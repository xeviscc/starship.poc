package com.w2m.starship.poc.domain.starship.mappers;

import com.w2m.starship.poc.domain.starship.models.StarshipDto;
import com.w2m.starship.poc.infrastructure.starship.entities.Starship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface StarshipDomainMapper {

	StarshipDomainMapper INSTANCE = Mappers.getMapper(StarshipDomainMapper.class);

	Starship starshipDtoToStarship(StarshipDto starshipDto);

	StarshipDto starshipToStarshipDto(Starship starship);

	default Page<StarshipDto> pageStarshipToStarshipDto(Page<Starship> starships) {
		return starships.map(this::starshipToStarshipDto);
	}

	List<StarshipDto> listStarshipToStarshipDto(List<Starship> starships);
}
