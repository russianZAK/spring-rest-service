package by.russianzak.service.dto;

import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

public class RequestRoadSurfaceEntityDto {
  @NotBlank(message = "Type cannot be blank")
  private String type;

  private String description;

  @NotNull(message = "Friction coefficient cannot be null")
  @Positive(message = "Friction coefficient must be positive")
  private Double frictionCoefficient;

  @Valid
  private Set<RequestStreetSlimEntityDto> streets;

  public RequestRoadSurfaceEntityDto(){}

  public RequestRoadSurfaceEntityDto(String type, String description, Double frictionCoefficient,
      Set<RequestStreetSlimEntityDto> streets) {
    this.type = type;
    this.description = description;
    this.frictionCoefficient = frictionCoefficient;
    this.streets = streets;
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

  public Set<RequestStreetSlimEntityDto> getStreets() {
    return streets;
  }

  public void setStreets(
      Set<RequestStreetSlimEntityDto> streets) {
    this.streets = streets;
  }
}
