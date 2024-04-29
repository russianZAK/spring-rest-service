package by.russianzak.service.dto.slim;

public class ResponseRoadSurfaceSlimEntityDto {
  private Long id;
  private String type;
  private String description;
  private Double frictionCoefficient;

  public ResponseRoadSurfaceSlimEntityDto(Long id, String type, String description, Double frictionCoefficient) {
    this.id = id;
    this.type = type;
    this.description = description;
    this.frictionCoefficient = frictionCoefficient;
  }

  public ResponseRoadSurfaceSlimEntityDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Double getFrictionCoefficient() {
    return frictionCoefficient;
  }

  public void setFrictionCoefficient(Double frictionCoefficient) {
    this.frictionCoefficient = frictionCoefficient;
  }
}
