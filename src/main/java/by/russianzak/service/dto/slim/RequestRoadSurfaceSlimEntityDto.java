package by.russianzak.service.dto.slim;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RequestRoadSurfaceSlimEntityDto {
  @NotBlank(message = "Type cannot be blank")
  private String type;

  private String description;

  @NotNull(message = "Friction coefficient cannot be null")
  @Positive(message = "Friction coefficient must be positive")
  private Double frictionCoefficient;

  public RequestRoadSurfaceSlimEntityDto(String type, String description, Double frictionCoefficient) {
    this.type = type;
    this.description = description;
    this.frictionCoefficient = frictionCoefficient;
  }

  public RequestRoadSurfaceSlimEntityDto() {
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

  public Double getFrictionCoefficient() {
    return frictionCoefficient;
  }

  public void setFrictionCoefficient(Double frictionCoefficient) {
    this.frictionCoefficient = frictionCoefficient;
  }
}
