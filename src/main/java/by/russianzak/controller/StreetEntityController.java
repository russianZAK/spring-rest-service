package by.russianzak.controller;

import by.russianzak.service.StreetEntityService;
import by.russianzak.service.dto.RequestStreetEntityDto;
import by.russianzak.service.dto.ResponseStreetEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/streets")
public class StreetEntityController {

  private final StreetEntityService streetEntityService;

  public StreetEntityController(StreetEntityService streetEntityService) {
    this.streetEntityService = streetEntityService;
  }

  @PostMapping()
  public ResponseEntity<ResponseStreetEntityDto> saveStreet(@Validated @RequestBody RequestStreetEntityDto request) {
    ResponseStreetEntityDto savedStreet = streetEntityService.save(request);
    return new ResponseEntity<>(savedStreet, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseStreetEntityDto> getStreetById(@PathVariable("id") Long id) {
    ResponseStreetEntityDto street = streetEntityService.getById(id);
    return new ResponseEntity<>(street, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<List<ResponseStreetEntityDto>> getAllStreets() {
    List<ResponseStreetEntityDto> streets = streetEntityService.findAll();
    return new ResponseEntity<>(streets, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseStreetEntityDto> updateStreet(@Validated @RequestBody RequestStreetEntityDto request, @PathVariable("id") Long id) {
    ResponseStreetEntityDto updatedStreet = streetEntityService.update(request, id);
    return new ResponseEntity<>(updatedStreet, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStreetById(@PathVariable("id") Long id) {
    streetEntityService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

  }
}
