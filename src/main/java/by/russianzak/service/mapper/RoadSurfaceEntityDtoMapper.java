package by.russianzak.service.mapper;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.StreetEntity;
import by.russianzak.service.dto.RequestRoadSurfaceEntityDto;
import by.russianzak.service.dto.ResponseRoadSurfaceEntityDto;
import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseStreetSlimEntityDto;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface RoadSurfaceEntityDtoMapper {

  RoadSurfaceEntityDtoMapper INSTANCE = Mappers.getMapper(RoadSurfaceEntityDtoMapper.class);

  @Mapping(source = "incomingDto.type", target = "type")
  @Mapping(source = "incomingDto.description", target = "description")
  @Mapping(source = "incomingDto.frictionCoefficient", target = "frictionCoefficient")
  @Mapping(target = "streets", expression = "java(mapStreetDtosToEntities(incomingDto.getStreets()))")
  RoadSurfaceEntity map(RequestRoadSurfaceEntityDto incomingDto);

  default Set<StreetEntity> mapStreetDtosToEntities(Set<RequestStreetSlimEntityDto> streetDtos) {

    return streetDtos.stream()
            .map(streetDto -> StreetEntity.builder()
                .setName(streetDto.getName())
                .setPostalCode(streetDto.getPostalCode())
                .build())
            .collect(Collectors.toSet());
  }

  @Mapping(source = "entity.id", target = "id")
  @Mapping(source = "entity.type", target = "type")
  @Mapping(source = "entity.description", target = "description")
  @Mapping(source = "entity.frictionCoefficient", target = "frictionCoefficient")
  @Mapping(target = "streets", expression = "java(mapStreetEntitiesToDtos(entity.getStreets()))")
  ResponseRoadSurfaceEntityDto map(RoadSurfaceEntity entity);

  default Set<ResponseStreetSlimEntityDto> mapStreetEntitiesToDtos(
      Set<StreetEntity> streetEntities) {
    return streetEntities.stream()
        .map(streetEntity -> new ResponseStreetSlimEntityDto(streetEntity.getId(),
            streetEntity.getName(), streetEntity.getPostalCode()))
        .collect(Collectors.toSet());
  }
}
