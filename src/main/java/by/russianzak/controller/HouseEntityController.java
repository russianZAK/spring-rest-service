package by.russianzak.controller;

import by.russianzak.service.HouseEntityService;
import by.russianzak.service.dto.RequestHouseEntityDto;
import by.russianzak.service.dto.ResponseHouseEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseEntityController {

  private final HouseEntityService houseEntityService;

  public HouseEntityController(HouseEntityService houseEntityService) {
    this.houseEntityService = houseEntityService;
  }

  @PostMapping()
  public ResponseEntity<ResponseHouseEntityDto> saveHouse(@Validated @RequestBody RequestHouseEntityDto request) {
    ResponseHouseEntityDto savedHouse = houseEntityService.save(request);
    return new ResponseEntity<>(savedHouse, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseHouseEntityDto> getHouseById(@PathVariable("id") Long id) {
    ResponseHouseEntityDto house = houseEntityService.getById(id);
    return new ResponseEntity<>(house, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<List<ResponseHouseEntityDto>> getAllHouses() {
    List<ResponseHouseEntityDto> houses = houseEntityService.findAll();
    return new ResponseEntity<>(houses, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseHouseEntityDto> updateHouse(@Validated @RequestBody RequestHouseEntityDto request, @PathVariable("id") Long id) {
    ResponseHouseEntityDto updatedHouse = houseEntityService.update(request, id);
    return new ResponseEntity<>(updatedHouse, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHouseById(@PathVariable("id") Long id) {
    houseEntityService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
