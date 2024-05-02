package by.russianzak.service.mapper;

import by.russianzak.model.HouseEntity;
import by.russianzak.model.StreetEntity;
import by.russianzak.service.dto.RequestHouseEntityDto;
import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import by.russianzak.service.dto.ResponseHouseEntityDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class HouseEntityDtoMapperTest {

  private final HouseEntityDtoMapper mapper = Mappers.getMapper(HouseEntityDtoMapper.class);

  @Test
  void map_RequestHouseEntityDtoToHouseEntity() {
    RequestStreetSlimEntityDto streetDto = new RequestStreetSlimEntityDto("Main St", 12345L);
    RequestHouseEntityDto requestDto = new RequestHouseEntityDto("123", new Date(), 2L, "RESIDENTIAL", streetDto);

    HouseEntity houseEntity = mapper.map(requestDto);

    assertNotNull(houseEntity);
    assertEquals("123", houseEntity.getHouseNumber());
    assertEquals(2L, houseEntity.getNumFloors());
    assertEquals("RESIDENTIAL", houseEntity.getType());
    assertNotNull(houseEntity.getStreet());
    assertEquals("Main St", houseEntity.getStreet().getName());
    assertEquals(12345L, houseEntity.getStreet().getPostalCode());
  }

  @Test
  void map_RequestHouseEntityDtoToHouseEntityWithNullStreet() {
    RequestStreetSlimEntityDto streetDto = null;
    RequestHouseEntityDto requestDto = new RequestHouseEntityDto("123", new Date(), 2L, "RESIDENTIAL", streetDto);

    HouseEntity houseEntity = mapper.map(requestDto);

    assertNotNull(houseEntity);
    assertEquals("123", houseEntity.getHouseNumber());
    assertEquals(2L, houseEntity.getNumFloors());
    assertEquals("RESIDENTIAL", houseEntity.getType());
    assertNull(houseEntity.getStreet());
  }

  @Test
  void map_NullRequestHouseEntityDtoToHouseEntity() {
    HouseEntity houseEntity = mapper.map((RequestHouseEntityDto) null);
    assertNull(houseEntity);
  }

  @Test
  void map_HouseEntityToResponseHouseEntityDto() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setId(1L);
    streetEntity.setName("Main St");
    streetEntity.setPostalCode(12345L);

    HouseEntity houseEntity = new HouseEntity.Builder()
        .setId(1L)
        .setHouseNumber("123")
        .setBuildDate(new Date())
        .setNumFloors(2L)
        .setType(HouseEntity.TypeOfBuilding.RESIDENTIAL)
        .setStreet(streetEntity)
        .build();

    ResponseHouseEntityDto responseDto = mapper.map(houseEntity);

    assertNotNull(responseDto);
    assertEquals(1L, responseDto.getId());
    assertEquals("123", responseDto.getHouseNumber());
    assertEquals(2L, responseDto.getNumFloors());
    assertEquals("RESIDENTIAL", responseDto.getType());
    assertNotNull(responseDto.getStreet());
    assertEquals(1L, responseDto.getStreet().getId());
    assertEquals("Main St", responseDto.getStreet().getName());
    assertEquals(12345L, responseDto.getStreet().getPostalCode());
  }

  @Test
  void map_HouseEntityToResponseHouseEntityDtoWithNullStreet() {

    HouseEntity houseEntity = new HouseEntity.Builder()
        .setId(1L)
        .setHouseNumber("123")
        .setBuildDate(new Date())
        .setNumFloors(2L)
        .setType(HouseEntity.TypeOfBuilding.RESIDENTIAL)
        .setStreet(null)
        .build();

    ResponseHouseEntityDto responseDto = mapper.map(houseEntity);

    assertNotNull(responseDto);
    assertEquals(1L, responseDto.getId());
    assertEquals("123", responseDto.getHouseNumber());
    assertEquals(2L, responseDto.getNumFloors());
    assertEquals("RESIDENTIAL", responseDto.getType());
    assertNull(responseDto.getStreet());
  }

  @Test
  void map_NullHouseEntityToResponseHouseEntityDto() {
    ResponseHouseEntityDto houseEntity = mapper.map((HouseEntity) null);
    assertNull(houseEntity);
  }

}
