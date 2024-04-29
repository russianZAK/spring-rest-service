package by.russianzak.repository;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import java.util.List;
import java.util.Optional;

public interface RoadSurfaceEntityRepository {

  RoadSurfaceEntity save(RoadSurfaceEntity request);

  void deleteById(Long id);

  void delete(RoadSurfaceEntity request);

  RoadSurfaceEntity update(RoadSurfaceEntity request);

  Optional<RoadSurfaceEntity> findById(Long id);

  List<RoadSurfaceEntity> findAll();

  boolean existsByType(TypeOfRoadSurface type);

  Optional<RoadSurfaceEntity> findByType(TypeOfRoadSurface type);
}
