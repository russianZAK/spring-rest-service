package by.russianzak.service.mapper;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.StreetEntity;
import by.russianzak.service.dto.RequestRoadSurfaceEntityDto;
import by.russianzak.service.dto.ResponseRoadSurfaceEntityDto;
import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseStreetSlimEntityDto;
import by.russianzak.service.mapper.RoadSurfaceEntityDtoMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoadSurfaceEntityDtoMapperTest {

  private final RoadSurfaceEntityDtoMapper mapper = Mappers.getMapper(RoadSurfaceEntityDtoMapper.class);

  @Test
  void testMappingRequestDtoToEntity() {
    RequestRoadSurfaceEntityDto requestDto = new RequestRoadSurfaceEntityDto();
    requestDto.setType("ASPHALT");
    requestDto.setDescription("Smooth asphalt surface");
    requestDto.setFrictionCoefficient(0.9);
    Set<RequestStreetSlimEntityDto> streetDtos = new HashSet<>();
    streetDtos.add(new RequestStreetSlimEntityDto("Main Street", 12345L));
    requestDto.setStreets(streetDtos);

    RoadSurfaceEntity entity = mapper.map(requestDto);

    assertNotNull(entity);
    assertEquals("ASPHALT", entity.getType());
    assertEquals("Smooth asphalt surface", entity.getDescription());
    assertEquals(0.9, entity.getFrictionCoefficient());
    assertEquals(1, entity.getStreets().size());
  }

  @Test
  void testMappingNullRequestDtoToEntity() {
    RoadSurfaceEntity entity = mapper.map((RequestRoadSurfaceEntityDto) null);

    assertNull(entity);
  }

  @Test
  void testMappingEntityToResponseDto() {
    RoadSurfaceEntity entity = RoadSurfaceEntity.builder()
        .setId(1L)
        .setType(RoadSurfaceEntity.TypeOfRoadSurface.ASPHALT)
        .setDescription("Smooth asphalt surface")
        .setFrictionCoefficient(0.9)
        .build();
    StreetEntity streetEntity = StreetEntity.builder().setName("First").setPostalCode(777L).setId(2L).setRoadSurfaces(Collections.singleton(entity)).build();
    entity.setStreets(Collections.singleton(streetEntity));
    ResponseRoadSurfaceEntityDto responseDto = mapper.map(entity);

    assertNotNull(responseDto);
    assertEquals(1L, responseDto.getId());
    assertEquals("ASPHALT", responseDto.getType());
    assertEquals("Smooth asphalt surface", responseDto.getDescription());
    assertEquals(0.9, responseDto.getFrictionCoefficient());
    assertEquals(1, responseDto.getStreets().size());
  }

  @Test
  void testMappingNullEntityToResponseDto() {
    ResponseRoadSurfaceEntityDto responseDto = mapper.map((RoadSurfaceEntity) null);

    assertNull(responseDto);
  }
}
