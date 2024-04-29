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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "road_surface")
public class RoadSurfaceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", unique = true, nullable = false, length = 255)
  private TypeOfRoadSurface type;

  @Column(name = "description", nullable = false, length = 255)
  private String description;

  @Column(name = "friction_coefficient", nullable = false)
  private Double frictionCoefficient;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "road_surface_street",
      joinColumns = @JoinColumn(name = "road_surface_id"),
      inverseJoinColumns = @JoinColumn(name = "street_id"))
  private Set<StreetEntity> streets;

  public RoadSurfaceEntity() {}

  private RoadSurfaceEntity(Builder builder) {
    setId(builder.id);
    setType(builder.type);
    setDescription(builder.description);
    setFrictionCoefficient(builder.frictionCoefficient);
    setStreets(builder.streets);
  }

  public static Builder builder() {
    return new Builder();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setType(TypeOfRoadSurface type) {
    this.type = type;
  }

  public String getType() {
    return type.value;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setFrictionCoefficient(Double frictionCoefficient) {
    this.frictionCoefficient = frictionCoefficient;
  }

  public Double getFrictionCoefficient() {
    return frictionCoefficient;
  }

  public void setStreets(Set<StreetEntity> streets) {
    this.streets = streets;
  }

  public Set<StreetEntity> getStreets() {
    return streets;
  }

  public void addStreet(StreetEntity street) {
    this.streets.add(street);
    street.addRoadSurface(this);
  }

  @Override
  public String toString() {
    return "RoadSurfaceEntity{" +
        "id=" + id +
        ", type=" + type.value +
        ", description='" + description + '\'' +
        ", frictionCoefficient=" + frictionCoefficient +
        ", streetsId=" + streets.stream().map(StreetEntity::getId).collect(Collectors.toSet()) +
        '}';
  }

  public static class Builder {
    private long id;
    private TypeOfRoadSurface type;
    private String description;
    private Double frictionCoefficient;
    private Set<StreetEntity> streets;

    private Builder() {
      this.streets = new HashSet<>();
    }

    public Builder setId(long id) {
      this.id = id;
      return this;
    }

    public Builder setType(TypeOfRoadSurface type) {
      this.type = type;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setFrictionCoefficient(Double frictionCoefficient) {
      this.frictionCoefficient = frictionCoefficient;
      return this;
    }

    public Builder setStreets(Set<StreetEntity> streets) {
      this.streets = streets;
      return this;
    }

    public RoadSurfaceEntity build() {
      return new RoadSurfaceEntity(this);
    }
  }

  public enum TypeOfRoadSurface {
    ASPHALT("ASPHALT"),
    CONCRETE("CONCRETE"),
    GRAVEL("GRAVEL"),
    DIRT("DIRT");

    private final String value;

    TypeOfRoadSurface(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static TypeOfRoadSurface fromValue(String value) {
      for (TypeOfRoadSurface type : TypeOfRoadSurface.values()) {
        if (type.getValue().equalsIgnoreCase(value)) {
          return type;
        }
      }
      throw new IllegalArgumentException("Invalid road surface type value: " + value);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RoadSurfaceEntity that = (RoadSurfaceEntity) o;
    return type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }

  public void validateNotNullFields() {
    if (type == null || description == null || streets == null) {
      throw new IllegalArgumentException("All fields of RoadSurfaceEntity must not be null");
    }
  }
}
