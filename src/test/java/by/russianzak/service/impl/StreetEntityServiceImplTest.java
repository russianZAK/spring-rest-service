package by.russianzak.service.impl;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.jpa.RoadSurfaceEntityRepositoryJpa;
import by.russianzak.repository.jpa.StreetEntityRepositoryJpa;
import by.russianzak.service.dto.RequestStreetEntityDto;
import by.russianzak.service.dto.ResponseStreetEntityDto;
import by.russianzak.service.dto.slim.RequestRoadSurfaceSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseRoadSurfaceSlimEntityDto;
import by.russianzak.service.impl.StreetEntityServiceImpl;
import by.russianzak.service.mapper.StreetEntityDtoMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StreetEntityServiceImplTest {

  @Mock
  private StreetEntityRepositoryJpa streetRepository;

  @Mock
  private RoadSurfaceEntityRepositoryJpa roadSurfaceEntityRepository;

  @Mock
  private StreetEntityDtoMapper streetEntityDtoMapper;

  @InjectMocks
  private StreetEntityServiceImpl streetEntityService;

  private RequestStreetEntityDto requestStreetEntityDto;
  private ResponseStreetEntityDto responseStreetEntityDto;
  private StreetEntity streetEntity;
  private RoadSurfaceEntity roadSurfaceEntity;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    streetEntity = new StreetEntity();
    streetEntity.setId(1L);
    streetEntity.setName("Test Street");
    streetEntity.setPostalCode(12345L);


    roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setId(1L);
    roadSurfaceEntity.setType(RoadSurfaceEntity.TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setDescription("Smooth asphalt road");
    roadSurfaceEntity.setFrictionCoefficient(0.8);

    Set<RoadSurfaceEntity> roadSurfaces = new HashSet<>();
    roadSurfaces.add(roadSurfaceEntity);

    streetEntity.setRoadSurfaces(roadSurfaces);

    requestStreetEntityDto = new RequestStreetEntityDto();
    requestStreetEntityDto.setName("Test Street");
    requestStreetEntityDto.setPostalCode(12345L);
    requestStreetEntityDto.setRoadSurfaces(Set.of(new RequestRoadSurfaceSlimEntityDto("ASPHALT", "Smooth asphalt road", 0.8)));
    requestStreetEntityDto.setHouses(new HashSet<>());

    responseStreetEntityDto = new ResponseStreetEntityDto();
    responseStreetEntityDto.setId(1L);
    responseStreetEntityDto.setName("Test Street");
    responseStreetEntityDto.setPostalCode(12345L);
    responseStreetEntityDto.setRoadSurfaces(Set.of(new ResponseRoadSurfaceSlimEntityDto(1L, "ASPHALT", "Smooth asphalt road", 0.8)));
    responseStreetEntityDto.setHouses(new HashSet<>());


    when(streetEntityDtoMapper.map(requestStreetEntityDto)).thenReturn(streetEntity);
    when(streetEntityDtoMapper.map(streetEntity)).thenReturn(responseStreetEntityDto);
    when(streetRepository.existsByNameAndPostalCode("Test Street", 12345L)).thenReturn(false);
    when(roadSurfaceEntityRepository.findByType(RoadSurfaceEntity.TypeOfRoadSurface.ASPHALT)).thenReturn(Optional.of(roadSurfaceEntity));
  }

  @Test
  void save_NewStreet_Success() {
    when(streetRepository.save(streetEntity)).thenReturn(streetEntity);

    ResponseStreetEntityDto savedStreet = streetEntityService.save(requestStreetEntityDto);

    verify(streetRepository, times(1)).save(streetEntity);

    assertEquals(streetEntity.getId(), savedStreet.getId());
    assertEquals(streetEntity.getName(), savedStreet.getName());
    assertEquals(streetEntity.getPostalCode(), savedStreet.getPostalCode());
    assertNotNull(savedStreet.getRoadSurfaces());
    assertEquals(1, savedStreet.getRoadSurfaces().size());
  }

  @Test
  void save_StreetAlreadyExists_ConflictException() {
    when(streetRepository.existsByNameAndPostalCode("Test Street", 12345L)).thenReturn(true);
    assertThrows(ResponseStatusException.class, () -> streetEntityService.save(requestStreetEntityDto));
    verify(streetRepository, never()).save(any());
  }

  @Test
  void getById_ExistingId_Success() {
    when(streetRepository.findById(1L)).thenReturn(Optional.of(streetEntity));
    ResponseStreetEntityDto retrievedStreet = streetEntityService.getById(1L);
    assertEquals(responseStreetEntityDto, retrievedStreet);
  }

  @Test
  void getById_NonExistingId_BadRequestException() {
    assertThrows(ResponseStatusException.class, () -> streetEntityService.getById(2L));
  }

  @Test
  void deleteById_ExistingStreet_Success() {
    streetEntityService.deleteById(1L);

    verify(streetRepository, times(1)).deleteById(1L);
  }

  @Test
  void delete_RequestStreetEntityDto_Success() {
    streetEntityService.delete(requestStreetEntityDto);
    verify(streetRepository, times(1)).delete(streetEntity);
  }

  @Test
  void update_ExistingStreet_Success() {
    when(streetEntityDtoMapper.map(requestStreetEntityDto)).thenReturn(streetEntity);
    when(streetRepository.findById(1L)).thenReturn(Optional.of(streetEntity));
    when(streetEntityDtoMapper.map(streetEntity)).thenReturn(responseStreetEntityDto);
    streetEntityService.update(requestStreetEntityDto, 1L);

    verify(streetRepository, times(1)).findById(1L);
    verify(streetRepository, times(1)).save(streetEntity);
  }

  @Test
  void update_NonExistingStreet_NotFoundException() {
    when(streetRepository.findById(2L)).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> streetEntityService.update(requestStreetEntityDto, 2L));

    verify(streetRepository, never()).save(any());
  }

  @Test
  void findById_ExistingStreet_Success() {
    when(streetRepository.findById(1L)).thenReturn(Optional.of(streetEntity));
    Optional<ResponseStreetEntityDto> foundStreet = streetEntityService.findById(1L);

    verify(streetRepository, times(1)).findById(1L);

    assertTrue(foundStreet.isPresent());
    assertEquals(streetEntity.getId(), foundStreet.get().getId());
    assertEquals(streetEntity.getName(), foundStreet.get().getName());
    assertEquals(streetEntity.getPostalCode(), foundStreet.get().getPostalCode());
  }

  @Test
  void findById_NonExistingStreet_EmptyOptional() {
    Optional<ResponseStreetEntityDto> foundStreet = streetEntityService.findById(2L);

    verify(streetRepository, times(1)).findById(2L);

    assertFalse(foundStreet.isPresent());
  }

  @Test
  void findAll_FoundStreets_Success() {

    when(streetRepository.findAll()).thenReturn(Collections.singletonList(streetEntity));
    List<ResponseStreetEntityDto> allStreets = streetEntityService.findAll();

    verify(streetRepository, times(1)).findAll();

    assertEquals(1, allStreets.size());

    ResponseStreetEntityDto responseStreetEntityDto = allStreets.get(0);
    assertEquals(streetEntity.getId(), responseStreetEntityDto.getId());
    assertEquals(streetEntity.getName(), responseStreetEntityDto.getName());
    assertEquals(streetEntity.getPostalCode(), responseStreetEntityDto.getPostalCode());
  }
}
