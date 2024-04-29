package by.russianzak.service.dto;

import by.russianzak.service.dto.slim.ResponseHouseSlimEntityDto;
import by.russianzak.service.dto.slim.ResponseRoadSurfaceSlimEntityDto;
import java.util.List;
import java.util.Set;

public class ResponseStreetEntityDto {
  private long id;
  private String name;
  private Long postalCode;
  private Set<ResponseHouseSlimEntityDto> houses;
  private Set<ResponseRoadSurfaceSlimEntityDto> roadSurfaces;

  public ResponseStreetEntityDto(long id, String name, Long postalCode, Set<ResponseHouseSlimEntityDto> houses,
      Set<ResponseRoadSurfaceSlimEntityDto> roadSurfaces) {
    this.id = id;
    this.name = name;
    this.postalCode = postalCode;
    this.houses = houses;
    this.roadSurfaces = roadSurfaces;
  }

  public ResponseStreetEntityDto() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public Set<ResponseHouseSlimEntityDto> getHouses() {
    return houses;
  }

  public void setHouses(Set<ResponseHouseSlimEntityDto> houses) {
    this.houses = houses;
  }

  public Set<ResponseRoadSurfaceSlimEntityDto> getRoadSurfaces() {
    return roadSurfaces;
  }

  public void setRoadSurfaces(
      Set<ResponseRoadSurfaceSlimEntityDto> roadSurfaces) {
    this.roadSurfaces = roadSurfaces;
  }
}
