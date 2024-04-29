package by.russianzak.service.dto.slim;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.sql.Date;

public class RequestHouseSlimEntityDto {
  @NotBlank(message = "House number cannot be blank")
  private String houseNumber;

  @NotNull(message = "Build date cannot be null")
  private Date buildDate;

  @NotNull(message = "Number of floors cannot be null")
  @PositiveOrZero(message = "Number of floors must be positive or zero")
  private Long numFloors;

  @NotBlank(message = "Type cannot be blank")
  private String type;

  public RequestHouseSlimEntityDto(String houseNumber, Date buildDate, Long numFloors, String type) {
    this.houseNumber = houseNumber;
    this.buildDate = buildDate;
    this.numFloors = numFloors;
    this.type = type;
  }

  public RequestHouseSlimEntityDto() {
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
}
