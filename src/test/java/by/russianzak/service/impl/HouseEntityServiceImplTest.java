package by.russianzak.service.impl;

import by.russianzak.model.HouseEntity;
import by.russianzak.model.HouseEntity.TypeOfBuilding;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.HouseEntityRepository;
import by.russianzak.repository.StreetEntityRepository;
import by.russianzak.service.dto.RequestHouseEntityDto;
import by.russianzak.service.dto.ResponseHouseEntityDto;
import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseStreetSlimEntityDto;
import by.russianzak.service.impl.HouseEntityServiceImpl;
import by.russianzak.service.mapper.HouseEntityDtoMapper;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HouseEntityServiceImplTest {

  @Mock
  private HouseEntityRepository houseEntityRepository;

  @Mock
  private HouseEntityDtoMapper houseEntityDtoMapper;

  @Mock
  private StreetEntityRepository streetEntityRepository;

  @InjectMocks
  private HouseEntityServiceImpl houseEntityService;

  private RequestHouseEntityDto requestHouseEntityDto;
  private ResponseHouseEntityDto responseHouseEntityDto;
  private HouseEntity houseEntity;
  private StreetEntity streetEntity;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    streetEntity = new StreetEntity();
    streetEntity.setId(1L);
    streetEntity.setName("Test Street");
    streetEntity.setPostalCode(12345L);

    houseEntity = new HouseEntity();
    houseEntity.setId(1L);
    houseEntity.setHouseNumber("123");
    houseEntity.setBuildDate(new Date());
    houseEntity.setNumFloors(2L);
    houseEntity.setType(TypeOfBuilding.COMMERCIAL);
    houseEntity.setStreet(streetEntity);

    requestHouseEntityDto = new RequestHouseEntityDto();
    requestHouseEntityDto.setHouseNumber("123");
    requestHouseEntityDto.setBuildDate(new Date());
    requestHouseEntityDto.setNumFloors(2L);
    requestHouseEntityDto.setType(TypeOfBuilding.COMMERCIAL.getValue());
    requestHouseEntityDto.setStreet(new RequestStreetSlimEntityDto("Test Street", 12345L));

    responseHouseEntityDto = new ResponseHouseEntityDto();
    responseHouseEntityDto.setId(1L);
    responseHouseEntityDto.setHouseNumber("123");
    responseHouseEntityDto.setBuildDate(new Date());
    responseHouseEntityDto.setNumFloors(2L);
    responseHouseEntityDto.setType(TypeOfBuilding.COMMERCIAL.getValue());
    responseHouseEntityDto.setStreet(new ResponseStreetSlimEntityDto(1L, "Test Street", 12345L));

    when(houseEntityDtoMapper.map(requestHouseEntityDto)).thenReturn(houseEntity);
    when(houseEntityDtoMapper.map(houseEntity)).thenReturn(responseHouseEntityDto);
    when(streetEntityRepository.findByPostalCode(12345L)).thenReturn(Optional.of(streetEntity));
    when(houseEntityRepository.findById(1L)).thenReturn(Optional.of(houseEntity));
  }

  @Test
  void save_NewHouse_Success() {
    when(houseEntityRepository.existsByHouseNumberAndStreet("123", streetEntity)).thenReturn(false);

    houseEntityService.save(requestHouseEntityDto);

    verify(houseEntityRepository, times(1)).save(houseEntity);
  }

  @Test
  void save_HouseAlreadyExists_ConflictException() {
    when(houseEntityRepository.existsByHouseNumberAndStreet("123", streetEntity)).thenReturn(true);

    assertThrows(ResponseStatusException.class, () -> houseEntityService.save(requestHouseEntityDto));
    verify(houseEntityRepository, never()).save(any());
  }

  @Test
  void getById_ExistingId_Success() {
    ResponseHouseEntityDto retrievedHouse = houseEntityService.getById(1L);

    assertEquals(responseHouseEntityDto, retrievedHouse);
  }

  @Test
  void getById_NonExistingId_BadRequestException() {
    assertThrows(ResponseStatusException.class, () -> houseEntityService.getById(2L));
  }

  @Test
  void deleteById_ExistingId_Success() {
    houseEntityService.deleteById(1L);
    verify(houseEntityRepository, times(1)).deleteById(1L);
  }

  @Test
  void delete_HouseExists_Success() {
    houseEntityService.delete(requestHouseEntityDto);
    verify(houseEntityRepository, times(1)).delete(houseEntity);
  }

  @Test
  void update_ExistingId_Success() {
    RequestHouseEntityDto updatedRequestDto = new RequestHouseEntityDto("456", new Date(), 3L, TypeOfBuilding.COMMERCIAL.getValue(), new RequestStreetSlimEntityDto("New Test Street", 54321L));
    HouseEntity updatedHouseEntity = new HouseEntity();
    updatedHouseEntity.setId(1L);
    updatedHouseEntity.setHouseNumber("456");
    updatedHouseEntity.setBuildDate(new Date());
    updatedHouseEntity.setNumFloors(3L);
    updatedHouseEntity.setType(TypeOfBuilding.COMMERCIAL);
    updatedHouseEntity.setStreet(new StreetEntity(1L,"New Test Street", 54321L, new HashSet<>(), new HashSet<>()));

    ResponseHouseEntityDto responseHouseEntityDto1 = new ResponseHouseEntityDto(updatedHouseEntity.getId(), updatedHouseEntity.getHouseNumber(),
        updatedHouseEntity.getBuildDate(), updatedHouseEntity.getNumFloors(), updatedHouseEntity.getType(), new ResponseStreetSlimEntityDto(updatedHouseEntity.getStreet().getId(),
        updatedHouseEntity.getStreet().getName(), updatedHouseEntity.getStreet().getPostalCode()));

    when(houseEntityRepository.findById(1L)).thenReturn(Optional.of(houseEntity));
    when(houseEntityRepository.save(any(HouseEntity.class))).thenReturn(updatedHouseEntity);
    when(houseEntityDtoMapper.map(updatedRequestDto)).thenReturn(updatedHouseEntity);
    when(houseEntityDtoMapper.map(updatedHouseEntity)).thenReturn(responseHouseEntityDto1);
    ResponseHouseEntityDto updatedHouseDto = houseEntityService.update(updatedRequestDto, 1L);

    assertEquals("456", updatedHouseDto.getHouseNumber());
    assertEquals("New Test Street", updatedHouseDto.getStreet().getName());
    assertEquals(54321L, updatedHouseDto.getStreet().getPostalCode());
  }

  @Test
  void update_NonExistingId_BadRequestException() {
    RequestHouseEntityDto updatedRequestDto = new RequestHouseEntityDto("456", new Date(), 3L, TypeOfBuilding.COMMERCIAL.getValue(), new RequestStreetSlimEntityDto("New Test Street", 54321L));
    assertThrows(ResponseStatusException.class, () -> houseEntityService.update(updatedRequestDto, 2L));
  }

  @Test
  void findById_ExistingId_Success() {
    Optional<ResponseHouseEntityDto> optionalHouseDto = houseEntityService.findById(1L);
    assertTrue(optionalHouseDto.isPresent());
    assertEquals(responseHouseEntityDto, optionalHouseDto.get());
  }

  @Test
  void findById_NonExistingId_Success() {
    Optional<ResponseHouseEntityDto> optionalHouseDto = houseEntityService.findById(2L);
    assertFalse(optionalHouseDto.isPresent());
  }

  @Test
  void findAll_HousesExist_Success() {
    when(houseEntityRepository.findAll()).thenReturn(List.of(houseEntity));
    List<ResponseHouseEntityDto> allHousesDto = houseEntityService.findAll();
    assertEquals(1, allHousesDto.size());
    assertEquals(responseHouseEntityDto, allHousesDto.get(0));
  }

  @Test
  void findAll_NoHouses_Success() {
    when(houseEntityRepository.findAll()).thenReturn(Collections.emptyList());
    List<ResponseHouseEntityDto> allHousesDto = houseEntityService.findAll();
    assertTrue(allHousesDto.isEmpty());
  }

}
