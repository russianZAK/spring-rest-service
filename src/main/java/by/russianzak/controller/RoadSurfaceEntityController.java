package by.russianzak.controller;

import by.russianzak.service.RoadSurfaceEntityService;
import by.russianzak.service.dto.RequestRoadSurfaceEntityDto;
import by.russianzak.service.dto.ResponseRoadSurfaceEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/road-surfaces")
public class RoadSurfaceEntityController {

  private final RoadSurfaceEntityService roadSurfaceEntityService;

  public RoadSurfaceEntityController(RoadSurfaceEntityService roadSurfaceEntityService) {
    this.roadSurfaceEntityService = roadSurfaceEntityService;
  }

  @PostMapping()
  public ResponseEntity<ResponseRoadSurfaceEntityDto> saveRoadSurface(@Validated @RequestBody RequestRoadSurfaceEntityDto request) {
    ResponseRoadSurfaceEntityDto savedRoadSurface = roadSurfaceEntityService.save(request);
    return new ResponseEntity<>(savedRoadSurface, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseRoadSurfaceEntityDto> getRoadSurfaceById(@PathVariable("id") Long id) {
    ResponseRoadSurfaceEntityDto roadSurface = roadSurfaceEntityService.getById(id);
    return new ResponseEntity<>(roadSurface, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<List<ResponseRoadSurfaceEntityDto>> getAllRoadSurfaces() {
    List<ResponseRoadSurfaceEntityDto> roadSurfaces = roadSurfaceEntityService.findAll();
    return new ResponseEntity<>(roadSurfaces, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseRoadSurfaceEntityDto> updateRoadSurface(@Validated @RequestBody RequestRoadSurfaceEntityDto request, @PathVariable("id") Long id) {
    ResponseRoadSurfaceEntityDto updatedRoadSurface = roadSurfaceEntityService.update(request, id);
    return new ResponseEntity<>(updatedRoadSurface, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRoadSurfaceById(@PathVariable("id") Long id) {
    roadSurfaceEntityService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
