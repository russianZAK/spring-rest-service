package by.russianzak.service.dto;

import by.russianzak.service.dto.slim.RequestStreetSlimEntityDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Date;

public class RequestHouseEntityDto {
  @NotBlank(message = "House number cannot be blank")
  private String houseNumber;

  @NotNull(message = "Build date cannot be null")
  private Date buildDate;

  @NotNull(message = "Number of floors cannot be null")
  @PositiveOrZero(message = "Number of floors must be positive or zero")
  private Long numFloors;

  @NotBlank(message = "Type cannot be blank")
  private String type;

  @Valid
  private RequestStreetSlimEntityDto street;

  public RequestHouseEntityDto(String houseNumber, Date buildDate, Long numFloors, String type, RequestStreetSlimEntityDto street) {
    this.houseNumber = houseNumber;
    this.buildDate = buildDate;
    this.numFloors = numFloors;
    this.type = type;
    this.street = street;
  }

  public RequestHouseEntityDto() {
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public Date getBuildDate() {
    return buildDate;
  }

  public void setBuildDate(Date buildDate) {
    this.buildDate = buildDate;
  }

  public Long getNumFloors() {
    return numFloors;
  }

  public void setNumFloors(Long numFloors) {
    this.numFloors = numFloors;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public RequestStreetSlimEntityDto getStreet() {
    return street;
  }

  public void setStreet(
      RequestStreetSlimEntityDto street) {
    this.street = street;
  }
}
