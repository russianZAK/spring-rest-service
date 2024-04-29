package by.russianzak.service.dto.slim;


import java.util.Date;

public class ResponseHouseSlimEntityDto {
  private Long id;
  private String houseNumber;
  private Date buildDate;
  private Long numFloors;
  private String type;

  public ResponseHouseSlimEntityDto(Long id, String houseNumber, Date buildDate, Long numFloors, String type) {
    this.id = id;
    this.houseNumber = houseNumber;
    this.buildDate = buildDate;
    this.numFloors = numFloors;
    this.type = type;
  }

  public ResponseHouseSlimEntityDto() {
  }

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
}
