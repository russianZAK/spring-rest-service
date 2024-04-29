package by.russianzak.service.dto;

import by.russianzak.service.dto.slim.ResponseStreetSlimEntityDto;
import java.util.Date;

public class ResponseHouseEntityDto {
  private Long id;
  private String houseNumber;
  private Date buildDate;
  private Long numFloors;
  private String type;
  private ResponseStreetSlimEntityDto street;

  public ResponseHouseEntityDto(Long id, String houseNumber, Date buildDate, Long numFloors, String type,
      ResponseStreetSlimEntityDto streetDto) {
    this.id = id;
    this.houseNumber = houseNumber;
    this.buildDate = buildDate;
    this.numFloors = numFloors;
    this.type = type;
    this.street = streetDto;
  }

  public ResponseHouseEntityDto() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public ResponseStreetSlimEntityDto getStreet() {
    return street;
  }

  public void setStreet(ResponseStreetSlimEntityDto streetId) {
    this.street = streetId;
  }
}
