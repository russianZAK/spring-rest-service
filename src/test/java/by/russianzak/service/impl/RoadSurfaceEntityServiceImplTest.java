package by.russianzak.service.impl;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.jpa.RoadSurfaceEntityRepositoryJpa;
import by.russianzak.repository.jpa.StreetEntityRepositoryJpa;
import by.russianzak.service.dto.RequestRoadSurfaceEntityDto;
import by.russianzak.service.dto.ResponseRoadSurfaceEntityDto;
import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseStreetSlimEntityDto;
import by.russianzak.service.impl.RoadSurfaceEntityServiceImpl;
import by.russianzak.service.mapper.RoadSurfaceEntityDtoMapper;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoadSurfaceEntityServiceImplTest {

  @Mock
  private RoadSurfaceEntityRepositoryJpa roadSurfaceRepository;

  @Mock
  private StreetEntityRepositoryJpa streetRepository;

  @Mock
  private RoadSurfaceEntityDtoMapper roadSurfaceDtoMapper;

  @InjectMocks
  private RoadSurfaceEntityServiceImpl roadSurfaceService;

  private RoadSurfaceEntity roadSurfaceEntity;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setId(1L);
    roadSurfaceEntity.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setFrictionCoefficient(0.8);

    when(roadSurfaceDtoMapper.map(any(RequestRoadSurfaceEntityDto.class))).thenReturn(roadSurfaceEntity);
  }

  @Test
  void save_NewRoadSurface_Success() {
    RequestRoadSurfaceEntityDto request = new RequestRoadSurfaceEntityDto("Gravel", "Rough surface", 0.6, Collections.emptySet());

    RoadSurfaceEntity savedRoadSurfaceEntity = new RoadSurfaceEntity();
    savedRoadSurfaceEntity.setId(2L);
    savedRoadSurfaceEntity.setType(TypeOfRoadSurface.GRAVEL);
    savedRoadSurfaceEntity.setFrictionCoefficient(0.6);
    savedRoadSurfaceEntity.setStreets(Collections.emptySet());
    when(roadSurfaceDtoMapper.map(request)).thenReturn(savedRoadSurfaceEntity);
    when(roadSurfaceDtoMapper.map(savedRoadSurfaceEntity)).thenReturn(new ResponseRoadSurfaceEntityDto(savedRoadSurfaceEntity.getId(), savedRoadSurfaceEntity.getType(), savedRoadSurfaceEntity.getDescription(),
        savedRoadSurfaceEntity.getFrictionCoefficient(), new HashSet<>()));
    when(roadSurfaceRepository.existsByType(RoadSurfaceEntity.TypeOfRoadSurface.GRAVEL)).thenReturn(false);
    when(roadSurfaceRepository.save(any(RoadSurfaceEntity.class))).thenReturn(savedRoadSurfaceEntity);

    ResponseRoadSurfaceEntityDto savedDto = roadSurfaceService.save(request);

    assertNotNull(savedDto);
    assertEquals(savedRoadSurfaceEntity.getId(), savedDto.getId());
    assertEquals(savedRoadSurfaceEntity.getType(), savedDto.getType());
    assertEquals(savedRoadSurfaceEntity.getFrictionCoefficient(), savedDto.getFrictionCoefficient());
  }

  @Test
  void save_ExistingRoadSurface_ConflictException() {
    RequestRoadSurfaceEntityDto request = new RequestRoadSurfaceEntityDto("ASPHALT", "Smooth surface", 0.8, Collections.emptySet());

    when(roadSurfaceRepository.existsByType(TypeOfRoadSurface.ASPHALT)).thenReturn(true);

    assertThrows(ResponseStatusException.class, () -> roadSurfaceService.save(request));

    verify(roadSurfaceRepository, never()).save(any());
  }

  @Test
  void save_StreetNotFound_ConflictException() {
    RequestRoadSurfaceEntityDto request = new RequestRoadSurfaceEntityDto("GRAVEL", "Rough surface", 0.6, Collections.singleton(new RequestStreetSlimEntityDto("Unknown", 12345L)));
    RoadSurfaceEntity savedRoadSurfaceEntity = new RoadSurfaceEntity();
    savedRoadSurfaceEntity.setId(2L);
    savedRoadSurfaceEntity.setType(TypeOfRoadSurface.GRAVEL);
    savedRoadSurfaceEntity.setFrictionCoefficient(0.6);
    savedRoadSurfaceEntity.setStreets(Collections.emptySet());
    when(roadSurfaceDtoMapper.map(request)).thenReturn(savedRoadSurfaceEntity);
    when(roadSurfaceDtoMapper.map(savedRoadSurfaceEntity)).thenReturn(new ResponseRoadSurfaceEntityDto(savedRoadSurfaceEntity.getId(), savedRoadSurfaceEntity.getType(), savedRoadSurfaceEntity.getDescription(),
        savedRoadSurfaceEntity.getFrictionCoefficient(), new HashSet<>()));
    when(roadSurfaceRepository.existsByType(RoadSurfaceEntity.TypeOfRoadSurface.GRAVEL)).thenReturn(false);
    when(roadSurfaceRepository.save(any(RoadSurfaceEntity.class))).thenReturn(savedRoadSurfaceEntity);
    when(roadSurfaceRepository.existsByType(TypeOfRoadSurface.GRAVEL)).thenReturn(true);

    assertThrows(ResponseStatusException.class, () -> roadSurfaceService.save(request));

    verify(roadSurfaceRepository, never()).save(any());
  }

  @Test
  void deleteById_ExistingId_Success() {
    when(roadSurfaceRepository.existsById(1L)).thenReturn(true);

    roadSurfaceService.deleteById(1L);

    verify(roadSurfaceRepository, times(1)).deleteById(1L);
  }

  @Test
  void delete_ValidRequest_Success() {
    RequestRoadSurfaceEntityDto requestDto = new RequestRoadSurfaceEntityDto();
    requestDto.setType("Asphalt");
    requestDto.setFrictionCoefficient(0.8);

    when(roadSurfaceDtoMapper.map(requestDto)).thenReturn(roadSurfaceEntity);

    roadSurfaceService.delete(requestDto);

    verify(roadSurfaceRepository, times(1)).delete(roadSurfaceEntity);
  }

  @Test
  void update_ExistingId_Success() {
    RequestRoadSurfaceEntityDto requestDto = new RequestRoadSurfaceEntityDto();
    requestDto.setType("CONCRETE");
    requestDto.setFrictionCoefficient(0.7);

    RoadSurfaceEntity updatedRoadSurface = new RoadSurfaceEntity();
    updatedRoadSurface.setId(1L);
    updatedRoadSurface.setType(TypeOfRoadSurface.CONCRETE);
    updatedRoadSurface.setFrictionCoefficient(0.7);
    updatedRoadSurface.setStreets(new HashSet<>());

    when(roadSurfaceRepository.findById(1L)).thenReturn(Optional.of(updatedRoadSurface));
    when(roadSurfaceDtoMapper.map(requestDto)).thenReturn(updatedRoadSurface);
    when(roadSurfaceRepository.save(updatedRoadSurface)).thenReturn(updatedRoadSurface);
    when(roadSurfaceDtoMapper.map(updatedRoadSurface)).thenReturn(new ResponseRoadSurfaceEntityDto(
        updatedRoadSurface.getId(), updatedRoadSurface.getType(), updatedRoadSurface.getDescription(),
        updatedRoadSurface.getFrictionCoefficient(), new HashSet<>()
    ));

    ResponseRoadSurfaceEntityDto responseDto = roadSurfaceService.update(requestDto, 1L);

    assertNotNull(responseDto);
    assertEquals("CONCRETE", responseDto.getType());
    assertEquals(0.7, responseDto.getFrictionCoefficient());
  }

  @Test
  void update_NonExistingId_NotFoundException() {
    RequestRoadSurfaceEntityDto requestDto = new RequestRoadSurfaceEntityDto();

    when(roadSurfaceRepository.findById(2L)).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> roadSurfaceService.update(requestDto, 2L));
  }

  @Test
  void findById_ExistingId_ReturnDto() {
    when(roadSurfaceRepository.findById(1L)).thenReturn(Optional.of(roadSurfaceEntity));

    ResponseRoadSurfaceEntityDto responseDto = new ResponseRoadSurfaceEntityDto();
    responseDto.setId(1L);
    responseDto.setType("ASPHALT");
    responseDto.setFrictionCoefficient(0.8);

    when(roadSurfaceDtoMapper.map(roadSurfaceEntity)).thenReturn(responseDto);

    Optional<ResponseRoadSurfaceEntityDto> foundDto = roadSurfaceService.findById(1L);

    assertTrue(foundDto.isPresent());
    assertEquals(responseDto, foundDto.get());
  }

  @Test
  void findById_NonExistingId_ReturnEmptyOptional() {
    when(roadSurfaceRepository.findById(2L)).thenReturn(Optional.empty());

    Optional<ResponseRoadSurfaceEntityDto> foundDto = roadSurfaceService.findById(2L);

    assertTrue(foundDto.isEmpty());
  }

  @Test
  void findAll_ReturnDtoList() {
    List<RoadSurfaceEntity> roadSurfaceEntities = Collections.singletonList(roadSurfaceEntity);
    when(roadSurfaceRepository.findAll()).thenReturn(roadSurfaceEntities);

    ResponseRoadSurfaceEntityDto responseDto = new ResponseRoadSurfaceEntityDto();
    responseDto.setId(1L);
    responseDto.setType("ASPHALT");
    responseDto.setFrictionCoefficient(0.8);

    when(roadSurfaceDtoMapper.map(roadSurfaceEntity)).thenReturn(responseDto);

    List<ResponseRoadSurfaceEntityDto> dtoList = roadSurfaceService.findAll();

    assertEquals(1, dtoList.size());
    assertEquals(responseDto, dtoList.get(0));
  }
}
