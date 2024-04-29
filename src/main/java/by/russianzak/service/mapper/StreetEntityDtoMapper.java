package by.russianzak.service.mapper;

import by.russianzak.model.HouseEntity.TypeOfBuilding;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import by.russianzak.service.dto.RequestStreetEntityDto;
import by.russianzak.service.dto.ResponseStreetEntityDto;
import by.russianzak.service.dto.slim.RequestHouseSlimEntityDto;
import by.russianzak.service.dto.slim.RequestRoadSurfaceSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseHouseSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseRoadSurfaceSlimEntityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import by.russianzak.model.HouseEntity;
import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.StreetEntity;
import org.springframework.stereotype.Component;

@Mapper
public interface StreetEntityDtoMapper {

  StreetEntityDtoMapper INSTANCE = Mappers.getMapper(StreetEntityDtoMapper.class);

  @Mapping(source = "incomingDto.name", target = "name")
  @Mapping(source = "incomingDto.postalCode", target = "postalCode")
  @Mapping(target = "houses", expression = "java(mapHouseDtosToEntities(incomingDto.getHouses()))")
  @Mapping(target = "roadSurfaces", expression = "java(mapRoadSurfaceDtosToEntities(incomingDto.getRoadSurfaces()))")
  StreetEntity map(RequestStreetEntityDto incomingDto);

  default Set<HouseEntity> mapHouseDtosToEntities(Set<RequestHouseSlimEntityDto> houseDtos) {
    return Optional.ofNullable(houseDtos)
        .map(houses -> houses.stream()
            .map(houseDto -> HouseEntity.builder()
                .setHouseNumber(houseDto.getHouseNumber())
                .setNumFloors(houseDto.getNumFloors())
                .setBuildDate(houseDto.getBuildDate())
                .setType(TypeOfBuilding.fromValue(houseDto.getType()))
                .build())
            .collect(Collectors.toSet()))
        .orElse(new HashSet<>());
  }

  default Set<RoadSurfaceEntity> mapRoadSurfaceDtosToEntities(
      Set<RequestRoadSurfaceSlimEntityDto> roadSurfaceDtos) {
    return Optional.ofNullable(roadSurfaceDtos)
        .map(surfaces -> surfaces.stream()
            .map(surfaceDto -> RoadSurfaceEntity.builder()
                .setType(TypeOfRoadSurface.fromValue(surfaceDto.getType()))
                .setDescription(surfaceDto.getDescription())
                .setFrictionCoefficient(surfaceDto.getFrictionCoefficient())
                .build())
            .collect(Collectors.toSet()))
        .orElse(new HashSet<>());
  }

  @Mapping(source = "entity.id", target = "id")
  @Mapping(source = "entity.name", target = "name")
  @Mapping(source = "entity.postalCode", target = "postalCode")
  @Mapping(target = "houses", expression = "java(mapHouseEntitiesToDtos(entity.getHouses()))")
  @Mapping(target = "roadSurfaces", expression = "java(mapRoadSurfaceEntitiesToDtos(entity.getRoadSurfaces()))")
  ResponseStreetEntityDto map(StreetEntity entity);

  default Set<ResponseHouseSlimEntityDto> mapHouseEntitiesToDtos(Set<HouseEntity> houseEntities) {
    return houseEntities.stream()
        .map(houseEntity -> new ResponseHouseSlimEntityDto(
            houseEntity.getId(),
            houseEntity.getHouseNumber(),
            houseEntity.getBuildDate(),
            houseEntity.getNumFloors(),
            houseEntity.getType()))
        .collect(Collectors.toSet());
  }

  default Set<ResponseRoadSurfaceSlimEntityDto> mapRoadSurfaceEntitiesToDtos(
      Set<RoadSurfaceEntity> roadSurfaceEntities) {
    return roadSurfaceEntities.stream()
        .map(roadSurfaceEntity -> new ResponseRoadSurfaceSlimEntityDto(
            roadSurfaceEntity.getId(),
            roadSurfaceEntity.getType(),
            roadSurfaceEntity.getDescription(),
            roadSurfaceEntity.getFrictionCoefficient()))
        .collect(Collectors.toSet());
  }
}
