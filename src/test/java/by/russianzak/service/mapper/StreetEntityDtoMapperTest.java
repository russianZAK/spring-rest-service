package by.russianzak.service.mapper;

import by.russianzak.model.HouseEntity;
import by.russianzak.model.HouseEntity.TypeOfBuilding;
import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import by.russianzak.model.StreetEntity;
import by.russianzak.service.dto.RequestStreetEntityDto;
import by.russianzak.service.dto.ResponseStreetEntityDto;
import by.russianzak.service.dto.slim.RequestHouseSlimEntityDto;
import by.russianzak.service.dto.slim.RequestRoadSurfaceSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseHouseSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseRoadSurfaceSlimEntityDto;
import by.russianzak.service.mapper.StreetEntityDtoMapper;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class StreetEntityDtoMapperTest {

  private final StreetEntityDtoMapper mapper = Mappers.getMapper(StreetEntityDtoMapper.class);

  @Test
  void testMapRequestDtoToEntity() {
    RequestStreetEntityDto requestDto = new RequestStreetEntityDto();
    requestDto.setName("Sample Street");
    requestDto.setPostalCode(12345L);

    RequestHouseSlimEntityDto houseDto = new RequestHouseSlimEntityDto("123", Date.valueOf("2000-01-01"), 2L, "RESIDENTIAL");
    Set<RequestHouseSlimEntityDto> houses = new HashSet<>(Collections.singletonList(houseDto));
    requestDto.setHouses(houses);

    RequestRoadSurfaceSlimEntityDto roadSurfaceDto = new RequestRoadSurfaceSlimEntityDto("ASPHALT", "Smooth surface", 0.7);
    Set<RequestRoadSurfaceSlimEntityDto> roadSurfaces = new HashSet<>(Collections.singletonList(roadSurfaceDto));
    requestDto.setRoadSurfaces(roadSurfaces);

    StreetEntity streetEntity = mapper.map(requestDto);

    assertNotNull(streetEntity);
    assertEquals(requestDto.getName(), streetEntity.getName());
    assertEquals(requestDto.getPostalCode(), streetEntity.getPostalCode());

    assertEquals(1, streetEntity.getHouses().size());
    HouseEntity houseEntity = streetEntity.getHouses().iterator().next();
    assertEquals(houseDto.getHouseNumber(), houseEntity.getHouseNumber());
    assertEquals(houseDto.getBuildDate(), houseEntity.getBuildDate());
    assertEquals(houseDto.getNumFloors(), houseEntity.getNumFloors());
    assertEquals(houseDto.getType(), houseEntity.getType());

    assertEquals(1, streetEntity.getRoadSurfaces().size());
    RoadSurfaceEntity roadSurfaceEntity = streetEntity.getRoadSurfaces().iterator().next();
    assertEquals(roadSurfaceDto.getType(), roadSurfaceEntity.getType());
    assertEquals(roadSurfaceDto.getDescription(), roadSurfaceEntity.getDescription());
    assertEquals(roadSurfaceDto.getFrictionCoefficient(), roadSurfaceEntity.getFrictionCoefficient());
  }

  @Test
  void testMapNullRequestDtoToEntity() {
    StreetEntity streetEntity = mapper.map((RequestStreetEntityDto) null);

    assertNull(streetEntity);
  }

  @Test
  void testMapEntityToResponseDto() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setId(1L);
    streetEntity.setName("Sample Street");
    streetEntity.setPostalCode(12345L);

    HouseEntity houseEntity = new HouseEntity();
    houseEntity.setHouseNumber("123");
    houseEntity.setBuildDate(Date.valueOf("2000-01-01"));
    houseEntity.setNumFloors(2L);
    houseEntity.setType(TypeOfBuilding.UTILITY);
    streetEntity.setHouses(Collections.singleton(houseEntity));

    RoadSurfaceEntity roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setDescription("Smooth surface");
    roadSurfaceEntity.setFrictionCoefficient(0.7);
    streetEntity.setRoadSurfaces(Collections.singleton(roadSurfaceEntity));

    ResponseStreetEntityDto responseDto = mapper.map(streetEntity);

    assertNotNull(responseDto);
    assertEquals(streetEntity.getId(), responseDto.getId());
    assertEquals(streetEntity.getName(), responseDto.getName());
    assertEquals(streetEntity.getPostalCode(), responseDto.getPostalCode());

    assertEquals(1, responseDto.getHouses().size());
    ResponseHouseSlimEntityDto houseDto = responseDto.getHouses().iterator().next();
    assertEquals(houseEntity.getId(), houseDto.getId());
    assertEquals(houseEntity.getHouseNumber(), houseDto.getHouseNumber());
    assertEquals(houseEntity.getBuildDate(), houseDto.getBuildDate());
    assertEquals(houseEntity.getNumFloors(), houseDto.getNumFloors());
    assertEquals(houseEntity.getType(), houseDto.getType());

    assertEquals(1, responseDto.getRoadSurfaces().size());
    ResponseRoadSurfaceSlimEntityDto roadSurfaceDto = responseDto.getRoadSurfaces().iterator().next();
    assertEquals(roadSurfaceEntity.getId(), roadSurfaceDto.getId());
    assertEquals(roadSurfaceEntity.getType(), roadSurfaceDto.getType());
    assertEquals(roadSurfaceEntity.getDescription(), roadSurfaceDto.getDescription());
    assertEquals(roadSurfaceEntity.getFrictionCoefficient(), roadSurfaceDto.getFrictionCoefficient());
  }

  @Test
  void testMapNullEntityToResponseDto() {
    ResponseStreetEntityDto responseDto = mapper.map((StreetEntity) null);

    assertNull(responseDto);
  }
}
