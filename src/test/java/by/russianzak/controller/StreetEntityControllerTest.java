package by.russianzak.controller;

import by.russianzak.service.StreetEntityService;
import by.russianzak.service.dto.RequestStreetEntityDto;
import by.russianzak.service.dto.ResponseStreetEntityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class StreetEntityControllerTest {

  @Mock
  private StreetEntityService streetEntityService;

  @InjectMocks
  private StreetEntityController streetEntityController;

  private ResponseStreetEntityDto mockResponseStreet;
  private RequestStreetEntityDto mockRequestStreet;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    mockResponseStreet = new ResponseStreetEntityDto(1L, "Street A", 12345L,
        new HashSet<>(), new HashSet<>());
    mockRequestStreet = new RequestStreetEntityDto("Street A", 12345L,
        new HashSet<>(), new HashSet<>());
  }

  @Test
  void testSaveStreet() {
    when(streetEntityService.save(any(RequestStreetEntityDto.class))).thenReturn(mockResponseStreet);

    ResponseEntity<ResponseStreetEntityDto> response = streetEntityController.saveStreet(mockRequestStreet);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(mockResponseStreet, response.getBody());
  }

  @Test
  void testGetStreetById() {
    Long id = 1L;
    when(streetEntityService.getById(anyLong())).thenReturn(mockResponseStreet);

    ResponseEntity<ResponseStreetEntityDto> response = streetEntityController.getStreetById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponseStreet, response.getBody());
  }

  @Test
  void testGetAllStreets() {
    List<ResponseStreetEntityDto> expectedResponse = new ArrayList<>();
    expectedResponse.add(mockResponseStreet);

    when(streetEntityService.findAll()).thenReturn(expectedResponse);

    ResponseEntity<List<ResponseStreetEntityDto>> response = streetEntityController.getAllStreets();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  void testUpdateStreet() {
    Long id = 1L;
    when(streetEntityService.update(any(RequestStreetEntityDto.class), anyLong())).thenReturn(mockResponseStreet);

    ResponseEntity<ResponseStreetEntityDto> response = streetEntityController.updateStreet(mockRequestStreet, id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponseStreet, response.getBody());
  }

  @Test
  void testDeleteStreetById() {
    Long id = 1L;
    ResponseEntity<Void> response = streetEntityController.deleteStreetById(id);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }
}
