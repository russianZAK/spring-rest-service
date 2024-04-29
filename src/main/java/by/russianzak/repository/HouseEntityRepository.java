package by.russianzak.repository;

import by.russianzak.model.HouseEntity;
import by.russianzak.model.StreetEntity;
import java.util.List;
import java.util.Optional;

public interface HouseEntityRepository {

  HouseEntity save(HouseEntity request);

  void deleteById(Long id);

  void delete(HouseEntity request);

  HouseEntity update(HouseEntity request);

  Optional<HouseEntity> findById(Long id);

  List<HouseEntity> findAll();

  boolean existsByHouseNumberAndStreet(String houseNumber, StreetEntity street);
}
