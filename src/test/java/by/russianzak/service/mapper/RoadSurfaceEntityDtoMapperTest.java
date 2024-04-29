package by.russianzak.service.mapper;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.StreetEntity;
import by.russianzak.service.dto.RequestRoadSurfaceEntityDto;
import by.russianzak.service.dto.ResponseRoadSurfaceEntityDto;
import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseStreetSlimEntityDto;
import by.russianzak.service.mapper.RoadSurfaceEntityDtoMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
  void testMappingEntityToResponseDto() {
    RoadSurfaceEntity entity = RoadSurfaceEntity.builder()
        .setId(1L)
        .setType(RoadSurfaceEntity.TypeOfRoadSurface.ASPHALT)
        .setDescription("Smooth asphalt surface")
        .setFrictionCoefficient(0.9)
        .setStreets(new HashSet<>())
        .build();

    ResponseRoadSurfaceEntityDto responseDto = mapper.map(entity);

    assertNotNull(responseDto);
    assertEquals(1L, responseDto.getId());
    assertEquals("ASPHALT", responseDto.getType());
    assertEquals("Smooth asphalt surface", responseDto.getDescription());
    assertEquals(0.9, responseDto.getFrictionCoefficient());
    assertEquals(0, responseDto.getStreets().size());
  }
}
