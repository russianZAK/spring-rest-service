package by.russianzak.service.dto;

import by.russianzak.service.dto.slim.RequestHouseSlimEntityDto;
import by.russianzak.service.dto.slim.RequestRoadSurfaceSlimEntityDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Set;

public class RequestStreetEntityDto {
  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotNull(message = "Postal code cannot be null")
  @PositiveOrZero(message = "Postal code must be positive or zero")
  private Long postalCode;

  @Valid
  private Set<RequestHouseSlimEntityDto> houses;

  @Valid
  private Set<RequestRoadSurfaceSlimEntityDto> roadSurfaces;

  public RequestStreetEntityDto() {}

  public RequestStreetEntityDto(String name, Long postalCode, Set<RequestHouseSlimEntityDto> houses, Set<RequestRoadSurfaceSlimEntityDto> roadSurfaces) {
    this.name = name;
    this.postalCode = postalCode;
    this.houses = houses;
    this.roadSurfaces = roadSurfaces;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(Long postalCode) {
    this.postalCode = postalCode;
  }

  public Set<RequestHouseSlimEntityDto> getHouses() {
    return houses;
  }

  public void setHouses(Set<RequestHouseSlimEntityDto> houses) {
    this.houses = houses;
  }

  public Set<RequestRoadSurfaceSlimEntityDto> getRoadSurfaces() {
    return roadSurfaces;
  }

  public void setRoadSurfaces(
      Set<RequestRoadSurfaceSlimEntityDto> roadSurfaces) {
    this.roadSurfaces = roadSurfaces;
  }
}
