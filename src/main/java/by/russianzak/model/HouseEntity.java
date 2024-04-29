package by.russianzak.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "house",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"house_number", "street_id"})})
public class HouseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "house_number", nullable = false, length = 255)
  private String houseNumber;

  @Column(name = "build_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date buildDate;

  @Column(name = "num_floors", nullable = false)
  private Long numFloors;

  @Column(name = "type", nullable = false, length = 255)
  @Enumerated(EnumType.STRING)
  private TypeOfBuilding type;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "street_id", nullable = false)
  private StreetEntity street;

  public HouseEntity() {}

  private HouseEntity(Builder builder) {
    setId(builder.id);
    setHouseNumber(builder.houseNumber);
    setBuildDate(builder.buildDate);
    setNumFloors(builder.numFloors);
    setType(builder.type);
    setStreet(builder.street);
  }

  public static Builder builder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public Date getBuildDate() {
    return buildDate;
  }

  public Long getNumFloors() {
    return numFloors;
  }

  public String getType() {
    return type.value;
  }

  public StreetEntity getStreet() {
    return street;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public void setBuildDate(Date buildDate) {
    this.buildDate = buildDate;
  }

  public void setNumFloors(Long numFloors) {
    this.numFloors = numFloors;
  }

  public void setType(TypeOfBuilding type) {
    this.type = type;
  }

  public void setStreet(StreetEntity street) {
    this.street = street;
  }

  @Override
  public String toString() {
    return "HouseEntity{" +
        "id=" + id +
        ", houseNumber='" + houseNumber + '\'' +
        ", buildDate=" + buildDate +
        ", numFloors=" + numFloors +
        ", type=" + type.value +
        ", streetId=" + street.getId()+
        '}';
  }

  public static class Builder {
    private Long id;
    private String houseNumber;
    private Date buildDate;
    private Long numFloors;
    private TypeOfBuilding type;
    private StreetEntity street;

    public Builder setId(Long id) {
      this.id = id;
      return this;
    }

    public Builder setHouseNumber(String houseNumber) {
      this.houseNumber = houseNumber;
      return this;
    }

    public Builder setBuildDate(Date buildDate) {
      this.buildDate = buildDate;
      return this;
    }

    public Builder setNumFloors(Long numFloors) {
      this.numFloors = numFloors;
      return this;
    }

    public Builder setType(TypeOfBuilding type) {
      this.type = type;
      return this;
    }

    public Builder setStreet(StreetEntity street) {
      this.street = street;
      return this;
    }

    public HouseEntity build() {
      return new HouseEntity(this);
    }
  }

  public enum TypeOfBuilding {
    RESIDENTIAL("RESIDENTIAL"),
    COMMERCIAL("COMMERCIAL"),
    GARAGE("GARAGE"),
    UTILITY("UTILITY");

    private final String value;

    TypeOfBuilding(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static TypeOfBuilding fromValue(String value) {
      for (TypeOfBuilding type : TypeOfBuilding.values()) {
        if (type.getValue().equalsIgnoreCase(value)) {
          return type;
        }
      }
      throw new IllegalArgumentException("Invalid building type value: " + value);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HouseEntity that = (HouseEntity) o;
    return Objects.equals(houseNumber, that.houseNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(houseNumber);
  }

  public void validateNotNullFields() {
    if (houseNumber == null || buildDate == null || type == null || street == null) {
      throw new IllegalArgumentException("All fields of HouseEntity must not be null");
    }
  }
}
