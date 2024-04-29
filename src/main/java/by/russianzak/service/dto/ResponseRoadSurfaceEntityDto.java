package by.russianzak.service.dto;

import by.russianzak.service.dto.slim.ResponseStreetSlimEntityDto;
import java.util.Set;

public class ResponseRoadSurfaceEntityDto {
  private long id;
  private String type;
  private String description;
  private double frictionCoefficient;
  private Set<ResponseStreetSlimEntityDto> streets;

  public ResponseRoadSurfaceEntityDto() {}

  public ResponseRoadSurfaceEntityDto(long id, String type, String description,
      double frictionCoefficient, Set<ResponseStreetSlimEntityDto> streets) {
    this.id = id;
    this.type = type;
    this.description = description;
    this.frictionCoefficient = frictionCoefficient;
    this.streets = streets;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getFrictionCoefficient() {
    return frictionCoefficient;
  }

  public void setFrictionCoefficient(double frictionCoefficient) {
    this.frictionCoefficient = frictionCoefficient;
  }

  public Set<ResponseStreetSlimEntityDto> getStreets() {
    return streets;
  }

  public void setStreets(
      Set<ResponseStreetSlimEntityDto> streets) {
    this.streets = streets;
  }
}
