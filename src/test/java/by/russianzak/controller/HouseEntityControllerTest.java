package by.russianzak.controller;

import by.russianzak.model.HouseEntity.TypeOfBuilding;
import by.russianzak.service.HouseEntityService;
import by.russianzak.service.dto.RequestHouseEntityDto;
import by.russianzak.service.dto.ResponseHouseEntityDto;
import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseStreetSlimEntityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class HouseEntityControllerTest {

  @Mock
  private HouseEntityService houseEntityService;

  @InjectMocks
  private HouseEntityController houseEntityController;

  private ResponseHouseEntityDto mockResponseHouse;
  private RequestHouseEntityDto mockRequestHouse;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    mockResponseHouse = new ResponseHouseEntityDto(1L, "123", new Date(), 2L, TypeOfBuilding.GARAGE.getValue(),
        new ResponseStreetSlimEntityDto(1L, "Main Street", 12345L));
    mockRequestHouse = new RequestHouseEntityDto("123", new Date(), 2L, TypeOfBuilding.GARAGE.getValue(),
        new RequestStreetSlimEntityDto("Main Street", 12345L));
  }

  @Test
  void testSaveHouse() {
    when(houseEntityService.save(any(RequestHouseEntityDto.class))).thenReturn(mockResponseHouse);

    ResponseEntity<ResponseHouseEntityDto> response = houseEntityController.saveHouse(mockRequestHouse);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(mockResponseHouse, response.getBody());
  }

  @Test
  void testGetHouseById() {
    Long id = 1L;
    when(houseEntityService.getById(anyLong())).thenReturn(mockResponseHouse);

    ResponseEntity<ResponseHouseEntityDto> response = houseEntityController.getHouseById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponseHouse, response.getBody());
  }

  @Test
  void testGetAllHouses() {
    List<ResponseHouseEntityDto> expectedResponse = new ArrayList<>();
    expectedResponse.add(mockResponseHouse);

    when(houseEntityService.findAll()).thenReturn(expectedResponse);

    ResponseEntity<List<ResponseHouseEntityDto>> response = houseEntityController.getAllHouses();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  void testUpdateHouse() {
    Long id = 1L;
    when(houseEntityService.update(any(RequestHouseEntityDto.class), anyLong())).thenReturn(mockResponseHouse);

    ResponseEntity<ResponseHouseEntityDto> response = houseEntityController.updateHouse(mockRequestHouse, id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponseHouse, response.getBody());
  }

  @Test
  void testDeleteHouseById() {
    Long id = 1L;
    ResponseEntity<Void> response = houseEntityController.deleteHouseById(id);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }
}
