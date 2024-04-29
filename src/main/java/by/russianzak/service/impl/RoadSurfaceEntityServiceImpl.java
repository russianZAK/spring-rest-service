package by.russianzak.service.impl;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.jpa.RoadSurfaceEntityRepositoryJpa;
import by.russianzak.repository.jpa.StreetEntityRepositoryJpa;
import by.russianzak.service.RoadSurfaceEntityService;
import by.russianzak.service.dto.RequestRoadSurfaceEntityDto;
import by.russianzak.service.dto.ResponseRoadSurfaceEntityDto;
import by.russianzak.service.mapper.RoadSurfaceEntityDtoMapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoadSurfaceEntityServiceImpl implements RoadSurfaceEntityService {

  private final RoadSurfaceEntityRepositoryJpa roadSurfaceEntityRepository;
  private final RoadSurfaceEntityDtoMapper roadSurfaceEntityDtoMapper;
  private final StreetEntityRepositoryJpa streetEntityRepository;

  public RoadSurfaceEntityServiceImpl(RoadSurfaceEntityRepositoryJpa roadSurfaceEntityRepository,
      RoadSurfaceEntityDtoMapper roadSurfaceEntityDtoMapper,
      StreetEntityRepositoryJpa streetEntityRepository) {
    this.roadSurfaceEntityRepository = roadSurfaceEntityRepository;
    this.roadSurfaceEntityDtoMapper = roadSurfaceEntityDtoMapper;
    this.streetEntityRepository = streetEntityRepository;
  }

  @Transactional
  @Override
  public ResponseRoadSurfaceEntityDto save(RequestRoadSurfaceEntityDto request) {
    RoadSurfaceEntity roadSurface = roadSurfaceEntityDtoMapper.map(request);

    if (roadSurfaceEntityRepository.existsByType(TypeOfRoadSurface.valueOf(roadSurface.getType()))) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, String.format("Road surface with type - %s already exists", roadSurface.getType()));
    }

    Set<StreetEntity> streets = roadSurface.getStreets();

    for (StreetEntity street : streets) {
      String name = street.getName();
      Long postalCode = street.getPostalCode();

      Optional<StreetEntity> existingStreetOpt = streetEntityRepository.findByNameAndPostalCode(name, postalCode);

      if (existingStreetOpt.isPresent()) {
        StreetEntity existingStreet = existingStreetOpt.get();
        street.setId(existingStreet.getId());
      } else {
        street.setId(streetEntityRepository.save(street).getId());
      }
    }

    return roadSurfaceEntityDtoMapper.map(roadSurfaceEntityRepository.save(roadSurface));
  }

  @Transactional
  @Override
  public ResponseRoadSurfaceEntityDto getById(Long id) {
    Optional<RoadSurfaceEntity> roadSurfaceEntity = roadSurfaceEntityRepository.findById(id);
    if (roadSurfaceEntity.isPresent()) {
      return roadSurfaceEntityDtoMapper.map(roadSurfaceEntity.get());
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Road surface with id - %d not found", id));
    }
  }

  @Transactional
  @Override
  public void deleteById(Long id) {
    roadSurfaceEntityRepository.deleteById(id);
  }

  @Transactional
  @Override
  public void delete(RequestRoadSurfaceEntityDto request) {
    roadSurfaceEntityRepository.delete(roadSurfaceEntityDtoMapper.map(request));
  }

  @Transactional
  @Override
  public ResponseRoadSurfaceEntityDto update(RequestRoadSurfaceEntityDto request, Long id) {
    Optional<RoadSurfaceEntity> optionalRoadSurfaceEntity = roadSurfaceEntityRepository.findById(id);
    if (optionalRoadSurfaceEntity.isPresent()) {
      RoadSurfaceEntity newRoadSurface = roadSurfaceEntityDtoMapper.map(request);
      RoadSurfaceEntity roadSurface = optionalRoadSurfaceEntity.get();
      newRoadSurface.setId(id);
      newRoadSurface.setStreets(roadSurface.getStreets());
      return roadSurfaceEntityDtoMapper.map(roadSurfaceEntityRepository.save(newRoadSurface));
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Road surface with id - %d not found", id));
    }
  }

  @Transactional
  @Override
  public Optional<ResponseRoadSurfaceEntityDto> findById(Long id) {
    Optional<RoadSurfaceEntity> optionalRoadSurfaceEntity = roadSurfaceEntityRepository.findById(id);
    return optionalRoadSurfaceEntity.map(roadSurfaceEntityDtoMapper::map);
  }

  @Transactional
  @Override
  public List<ResponseRoadSurfaceEntityDto> findAll() {
    List<RoadSurfaceEntity> roadSurfaceEntities = roadSurfaceEntityRepository.findAll();
    return roadSurfaceEntities.stream().map(roadSurfaceEntityDtoMapper::map).toList();
  }
}
