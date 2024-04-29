package by.russianzak.controller;

import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import by.russianzak.service.RoadSurfaceEntityService;
import by.russianzak.service.dto.RequestRoadSurfaceEntityDto;
import by.russianzak.service.dto.ResponseRoadSurfaceEntityDto;
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

class RoadSurfaceEntityControllerTest {

  @Mock
  private RoadSurfaceEntityService roadSurfaceEntityService;

  @InjectMocks
  private RoadSurfaceEntityController roadSurfaceEntityController;

  private ResponseRoadSurfaceEntityDto mockResponseRoadSurface;
  private RequestRoadSurfaceEntityDto mockRequestRoadSurface;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockResponseRoadSurface = new ResponseRoadSurfaceEntityDto(1L, TypeOfRoadSurface.DIRT.getValue(), "Description A", 0.8,
        new HashSet<>());
    mockRequestRoadSurface = new RequestRoadSurfaceEntityDto(TypeOfRoadSurface.DIRT.getValue(), "Description A", 0.8,
        new HashSet<>());
  }

  @Test
  void testSaveRoadSurface() {
    when(roadSurfaceEntityService.save(any(RequestRoadSurfaceEntityDto.class))).thenReturn(mockResponseRoadSurface);

    ResponseEntity<ResponseRoadSurfaceEntityDto> response = roadSurfaceEntityController.saveRoadSurface(mockRequestRoadSurface);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(mockResponseRoadSurface, response.getBody());
  }

  @Test
  void testGetRoadSurfaceById() {
    Long id = 1L;
    when(roadSurfaceEntityService.getById(anyLong())).thenReturn(mockResponseRoadSurface);

    ResponseEntity<ResponseRoadSurfaceEntityDto> response = roadSurfaceEntityController.getRoadSurfaceById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponseRoadSurface, response.getBody());
  }

  @Test
  void testGetAllRoadSurfaces() {
    List<ResponseRoadSurfaceEntityDto> expectedResponse = new ArrayList<>();
    expectedResponse.add(mockResponseRoadSurface);

    when(roadSurfaceEntityService.findAll()).thenReturn(expectedResponse);

    ResponseEntity<List<ResponseRoadSurfaceEntityDto>> response = roadSurfaceEntityController.getAllRoadSurfaces();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
  }

  @Test
  void testUpdateRoadSurface() {
    Long id = 1L;
    when(roadSurfaceEntityService.update(any(RequestRoadSurfaceEntityDto.class), anyLong())).thenReturn(mockResponseRoadSurface);

    ResponseEntity<ResponseRoadSurfaceEntityDto> response = roadSurfaceEntityController.updateRoadSurface(mockRequestRoadSurface, id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponseRoadSurface, response.getBody());
  }

  @Test
  void testDeleteRoadSurfaceById() {
    Long id = 1L;
    ResponseEntity<Void> response = roadSurfaceEntityController.deleteRoadSurfaceById(id);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }
}
