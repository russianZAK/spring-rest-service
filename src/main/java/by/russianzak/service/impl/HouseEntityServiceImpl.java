package by.russianzak.service.impl;

import by.russianzak.model.HouseEntity;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.HouseEntityRepository;
import by.russianzak.repository.StreetEntityRepository;
import by.russianzak.service.HouseEntityService;
import by.russianzak.service.dto.RequestHouseEntityDto;
import by.russianzak.service.dto.ResponseHouseEntityDto;
import by.russianzak.service.mapper.HouseEntityDtoMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class HouseEntityServiceImpl implements HouseEntityService {

  private final HouseEntityRepository houseEntityRepository;
  private final HouseEntityDtoMapper houseEntityDtoMapper;
  private final StreetEntityRepository streetEntityRepository;

  public HouseEntityServiceImpl(HouseEntityRepository houseEntityRepository, HouseEntityDtoMapper houseEntityDtoMapper,
      StreetEntityRepository streetEntityRepository) {
    this.houseEntityRepository = houseEntityRepository;
    this.houseEntityDtoMapper = houseEntityDtoMapper;
    this.streetEntityRepository = streetEntityRepository;
  }

  @Transactional
  @Override
  public ResponseHouseEntityDto save(RequestHouseEntityDto request) {
    HouseEntity house = houseEntityDtoMapper.map(request);
    StreetEntity streetEntity = house.getStreet();

    Optional<StreetEntity> existingStreetOpt = streetEntityRepository.findByPostalCode(streetEntity.getPostalCode());
    existingStreetOpt.ifPresent(existingStreet -> {
      if (houseEntityRepository.existsByHouseNumberAndStreet(house.getHouseNumber(), existingStreet)) {
        String errorMessage = String.format("House with number - %s and street - %s already exists", house.getHouseNumber(), existingStreet.getName());
        throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
      } else {
        house.setStreet(existingStreet);
      }
    });

    return houseEntityDtoMapper.map(houseEntityRepository.save(house));
  }

  @Transactional
  @Override
  public ResponseHouseEntityDto getById(Long id) {
    Optional<HouseEntity> house = houseEntityRepository.findById(id);
    if (house.isPresent()) {
      return houseEntityDtoMapper.map(house.get());
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("House with id - %d not found", id));
    }
  }

  @Transactional
  @Override
  public void deleteById(Long id) {
    houseEntityRepository.deleteById(id);
  }

  @Transactional
  @Override
  public void delete(RequestHouseEntityDto request) {
    houseEntityRepository.delete(houseEntityDtoMapper.map(request));
  }

  @Transactional
  @Override
  public ResponseHouseEntityDto update(RequestHouseEntityDto request, Long id) {
    Optional<HouseEntity> optionalHouseEntity = houseEntityRepository.findById(id);
    if (optionalHouseEntity.isPresent()) {
      HouseEntity house = optionalHouseEntity.get();
      HouseEntity newHouse = houseEntityDtoMapper.map(request);
      newHouse.setId(id);
      newHouse.setStreet(house.getStreet());
      return houseEntityDtoMapper.map(houseEntityRepository.save(newHouse));
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("House with id - %d not found", id));
    }
  }

  @Transactional
  @Override
  public Optional<ResponseHouseEntityDto> findById(Long id) {
    Optional<HouseEntity> optionalHouseEntity = houseEntityRepository.findById(id);
    return optionalHouseEntity.map(houseEntityDtoMapper::map);
  }

  @Transactional
  @Override
  public List<ResponseHouseEntityDto> findAll() {
    List<HouseEntity> houseEntities = houseEntityRepository.findAll();
    return houseEntities.stream().map(houseEntityDtoMapper::map).toList();
  }
}
