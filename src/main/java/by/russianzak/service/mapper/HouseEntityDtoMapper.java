package by.russianzak.service.mapper;

import by.russianzak.model.HouseEntity;
import by.russianzak.service.dto.RequestHouseEntityDto;
import by.russianzak.service.dto.ResponseHouseEntityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HouseEntityDtoMapper {
  HouseEntityDtoMapper INSTANCE = Mappers.getMapper(HouseEntityDtoMapper.class);
      @Mapping(source = "incomingDto.houseNumber", target = "houseNumber")
      @Mapping(source = "incomingDto.buildDate", target = "buildDate")
      @Mapping(source = "incomingDto.numFloors", target = "numFloors")
      @Mapping(source = "incomingDto.type", target = "type")
      @Mapping(source = "incomingDto.street.name", target = "street.name")
      @Mapping(source = "incomingDto.street.postalCode", target = "street.postalCode")
  HouseEntity map(RequestHouseEntityDto incomingDto);

      @Mapping(source = "entity.id", target = "id")
      @Mapping(source = "entity.houseNumber", target = "houseNumber")
      @Mapping(source = "entity.buildDate", target = "buildDate")
      @Mapping(source = "entity.numFloors", target = "numFloors")
      @Mapping(source = "entity.type", target = "type")
      @Mapping(source = "entity.street.id", target = "street.id")
      @Mapping(source = "entity.street.name", target = "street.name")
      @Mapping(source = "entity.street.postalCode", target = "street.postalCode")
  ResponseHouseEntityDto map(HouseEntity entity);
}
