package by.russianzak.service.dto.slim;

public class ResponseStreetSlimEntityDto {
  private Long id;
  private String name;
  private Long postalCode;

  public ResponseStreetSlimEntityDto(Long id, String name, Long postalCode) {
    this.id = id;
    this.name = name;
    this.postalCode = postalCode;
  }

  public ResponseStreetSlimEntityDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
}
