package by.russianzak.repository.impl;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import by.russianzak.repository.jpa.RoadSurfaceEntityRepositoryJpa;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class RoadSurfaceEntityRepositoryImpl implements
    by.russianzak.repository.RoadSurfaceEntityRepository {

  private final RoadSurfaceEntityRepositoryJpa roadSurfaceEntityRepository;

  public RoadSurfaceEntityRepositoryImpl(
      RoadSurfaceEntityRepositoryJpa roadSurfaceEntityRepository) {
    this.roadSurfaceEntityRepository = roadSurfaceEntityRepository;
  }

  @Override
  public RoadSurfaceEntity save(RoadSurfaceEntity request) {
    return roadSurfaceEntityRepository.save(request);
  }

  @Override
  public void deleteById(Long id) {
    roadSurfaceEntityRepository.deleteById(id);
  }

  @Override
  public void delete(RoadSurfaceEntity request) {
    roadSurfaceEntityRepository.delete(request);
  }

  @Override
  public RoadSurfaceEntity update(RoadSurfaceEntity request) {
    return roadSurfaceEntityRepository.save(request);
  }

  @Override
  public Optional<RoadSurfaceEntity> findById(Long id) {
    return roadSurfaceEntityRepository.findById(id);
  }

  @Override
  public List<RoadSurfaceEntity> findAll() {
    return roadSurfaceEntityRepository.findAll();
  }

  @Override
  public boolean existsByType(TypeOfRoadSurface type) {
    return roadSurfaceEntityRepository.existsByType(type);
  }

  @Override
  public Optional<RoadSurfaceEntity> findByType(TypeOfRoadSurface type) {
    return roadSurfaceEntityRepository.findByType(type);
  }

  public void deleteAll() {
    roadSurfaceEntityRepository.deleteAll();
  }
}
