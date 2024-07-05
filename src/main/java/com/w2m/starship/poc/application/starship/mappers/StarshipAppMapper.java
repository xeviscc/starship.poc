package com.w2m.starship.poc.application.starship.mappers;

import com.w2m.starship.poc.application.starship.models.StarshipRequest;
import com.w2m.starship.poc.application.starship.models.StarshipResponse;
import com.w2m.starship.poc.domain.starship.models.StarshipDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface StarshipAppMapper {

	StarshipAppMapper INSTANCE = Mappers.getMapper(StarshipAppMapper.class);

	StarshipResponse starshipDtoToStarshipResponse(StarshipDto starshipDto);

	@Mapping(target = "id", ignore = true)
	StarshipDto starshipRequestToStarshipDto(StarshipRequest starshipRequest);

	default Page<StarshipResponse> pageStarshipDtoToStarshipResponse(Page<StarshipDto> starshipDto) {
		return starshipDto.map(this::starshipDtoToStarshipResponse);
	}

	List<StarshipResponse> listStarshipDtoToStarshipResponse(List<StarshipDto> starshipDto);
}
