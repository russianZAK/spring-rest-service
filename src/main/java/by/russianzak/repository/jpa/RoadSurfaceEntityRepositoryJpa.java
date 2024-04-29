package by.russianzak.repository.jpa;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadSurfaceEntityRepositoryJpa extends JpaRepository<RoadSurfaceEntity, Long> {
  boolean existsByType(TypeOfRoadSurface type);
  Optional<RoadSurfaceEntity> findByType(TypeOfRoadSurface type);
}
