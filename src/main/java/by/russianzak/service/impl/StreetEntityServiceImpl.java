package by.russianzak.service.impl;

import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.jpa.RoadSurfaceEntityRepositoryJpa;
import by.russianzak.repository.jpa.StreetEntityRepositoryJpa;
import by.russianzak.service.StreetEntityService;
import by.russianzak.service.dto.RequestStreetEntityDto;
import by.russianzak.service.dto.ResponseStreetEntityDto;
import by.russianzak.service.mapper.StreetEntityDtoMapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StreetEntityServiceImpl implements StreetEntityService {

  private final StreetEntityRepositoryJpa streetRepository;
  private final StreetEntityDtoMapper streetEntityDtoMapper;
  private final RoadSurfaceEntityRepositoryJpa roadSurfaceEntityRepository;

  public StreetEntityServiceImpl(StreetEntityRepositoryJpa streetRepository,
      StreetEntityDtoMapper streetEntityDtoMapper,
      RoadSurfaceEntityRepositoryJpa roadSurfaceEntityRepository) {
    this.streetRepository = streetRepository;
    this.streetEntityDtoMapper = streetEntityDtoMapper;
    this.roadSurfaceEntityRepository = roadSurfaceEntityRepository;
  }

  @Transactional
  @Override
  public ResponseStreetEntityDto save(RequestStreetEntityDto request) {
    StreetEntity street = streetEntityDtoMapper.map(request);
    if (streetRepository.existsByNameAndPostalCode(street.getName(), street.getPostalCode())) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, String.format("Street with name - %s and postal code - %d already exists", request.getName(), request.getPostalCode()));
    }

    Set<RoadSurfaceEntity> roadSurfaces = street.getRoadSurfaces();

    for (RoadSurfaceEntity roadSurface : roadSurfaces) {
      Optional<RoadSurfaceEntity> existingRoadSurfaceOpt = roadSurfaceEntityRepository.findByType(
          TypeOfRoadSurface.valueOf(roadSurface.getType()));

      if (existingRoadSurfaceOpt.isPresent()) {
        RoadSurfaceEntity existingRoadSurface = existingRoadSurfaceOpt.get();
        roadSurface.setId(existingRoadSurface.getId());
      } else {
        roadSurface.setId(roadSurfaceEntityRepository.save(roadSurface).getId());
      }
    }

    return streetEntityDtoMapper.map(streetRepository.save(street));
  }

  @Transactional
  @Override
  public ResponseStreetEntityDto getById(Long id) {
    Optional<StreetEntity> street = streetRepository.findById(id);
    if (street.isPresent()) {
      return streetEntityDtoMapper.map(street.get());
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Street with id - %d not found", id));
    }
  }

  @Transactional
  @Override
  public void deleteById(Long id) {
    streetRepository.deleteById(id);
  }

  @Transactional
  @Override
  public void delete(RequestStreetEntityDto request) {
    streetRepository.delete(streetEntityDtoMapper.map(request));
  }

  @Transactional
  @Override
  public ResponseStreetEntityDto update(RequestStreetEntityDto request, Long id) {
    Optional<StreetEntity> optionalStreetEntity = streetRepository.findById(id);
    if (optionalStreetEntity.isPresent()) {
      StreetEntity newStreet = streetEntityDtoMapper.map(request);
      StreetEntity street = optionalStreetEntity.get();
      newStreet.setId(id);
      newStreet.setHouses(street.getHouses());
      newStreet.setRoadSurfaces(street.getRoadSurfaces());
      return streetEntityDtoMapper.map(streetRepository.save(newStreet));
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Street with id - %d not found", id));
    }
  }

  @Transactional
  @Override
  public Optional<ResponseStreetEntityDto> findById(Long id) {
    Optional<StreetEntity> optionalStreetEntity = streetRepository.findById(id);
    return optionalStreetEntity.map(streetEntityDtoMapper::map);
  }

  @Transactional
  @Override
  public List<ResponseStreetEntityDto> findAll() {
    List<StreetEntity> streetEntities = streetRepository.findAll();
    return streetEntities.stream().map(streetEntityDtoMapper::map).toList();
  }
}
