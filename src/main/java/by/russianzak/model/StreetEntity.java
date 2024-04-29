package by.russianzak.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "street")
public class StreetEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "postal_code", nullable = false, unique = true)
  private Long postalCode;

  public StreetEntity(Long id, String name, Long postalCode, Set<HouseEntity> houses,
      Set<RoadSurfaceEntity> roadSurfaces) {
    this.id = id;
    this.name = name;
    this.postalCode = postalCode;
    this.houses = houses;
    this.roadSurfaces = roadSurfaces;
  }

  @OneToMany(mappedBy = "street", cascade = { CascadeType.ALL })
  private Set<HouseEntity> houses;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "road_surface_street",
      joinColumns = @JoinColumn(name = "street_id"),
      inverseJoinColumns = @JoinColumn(name = "road_surface_id"))
  private Set<RoadSurfaceEntity> roadSurfaces;

  public StreetEntity() {
    houses = new HashSet<>();
    roadSurfaces = new HashSet<>();
  }

  private StreetEntity(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setPostalCode(builder.postalCode);
    setHouses(builder.houses);
    setRoadSurfaces(builder.roadSurfaces);
  }

  public static Builder builder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getPostalCode() {
    return postalCode;
  }

  public Set<HouseEntity> getHouses() {
    return houses;
  }

  public Set<RoadSurfaceEntity> getRoadSurfaces() {
    return roadSurfaces;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPostalCode(Long postalCode) {
    this.postalCode = postalCode;
  }

  public void setHouses(Set<HouseEntity> houses) {
    this.houses = houses;
    houses.forEach(houseEntity -> houseEntity.setStreet(this));
  }

  public void addHouse(HouseEntity house) {
    this.houses.add(house);
    house.setStreet(this);
  }

  public void setRoadSurfaces(Set<RoadSurfaceEntity> roadSurfaces) {
    this.roadSurfaces = roadSurfaces;
  }

  public void addRoadSurface(RoadSurfaceEntity roadSurface) {
    this.roadSurfaces.add(roadSurface);
    roadSurface.addStreet(this);
  }

  @Override
  public String toString() {
    return "StreetEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", postalCode=" + postalCode +
        ", houseIds=" + houses.stream().map(HouseEntity::getId).collect(Collectors.toSet()) +
        ", roadSurfaceIds=" + roadSurfaces.stream().map(RoadSurfaceEntity::getId).collect(Collectors.toSet()) +
        '}';
  }

  public static class Builder {
    private Long id;
    private String name;
    private Long postalCode;
    private Set<HouseEntity> houses;
    private Set<RoadSurfaceEntity> roadSurfaces;

    private Builder() {
      this.houses = new HashSet<>();
      this.roadSurfaces = new HashSet<>();
    }

    public Builder setId(Long id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setPostalCode(Long postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    public Builder setHouses(Set<HouseEntity> houses) {
      this.houses = houses;
      return this;
    }

    public Builder setRoadSurfaces(Set<RoadSurfaceEntity> roadSurfaces) {
      this.roadSurfaces = roadSurfaces;
      return this;
    }

    public StreetEntity build() {
      return new StreetEntity(this);
    }

    @Override
    public String toString() {
      return "StreetEntity.Builder{" +
          ", name='" + name + '\'' +
          ", postalCode=" + postalCode +
          '}';
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StreetEntity that = (StreetEntity) o;
    return Objects.equals(postalCode, that.postalCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(postalCode);
  }

  public void validateNotNullFields() {
    if (name == null || postalCode == null || houses == null || roadSurfaces == null) {
      throw new IllegalArgumentException("All fields of StreetEntity must not be null");
    }
  }
}
