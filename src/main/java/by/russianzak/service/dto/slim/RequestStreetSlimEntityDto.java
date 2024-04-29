package by.russianzak.service.dto.slim;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class RequestStreetSlimEntityDto {
  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotNull(message = "Postal code cannot be null")
  @PositiveOrZero(message = "Postal code must be positive or zero")
  private Long postalCode;

  public RequestStreetSlimEntityDto(String name, Long postalCode) {
    this.name = name;
    this.postalCode = postalCode;
  }

  public RequestStreetSlimEntityDto() {
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
}
