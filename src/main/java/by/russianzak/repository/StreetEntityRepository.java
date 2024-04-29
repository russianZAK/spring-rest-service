package by.russianzak.repository;

import by.russianzak.model.StreetEntity;
import java.util.List;
import java.util.Optional;

public interface StreetEntityRepository {

  StreetEntity save(StreetEntity request);

  void deleteById(Long id);

  void delete(StreetEntity request);

  StreetEntity update(StreetEntity request);

  Optional<StreetEntity> findById(Long id);

  List<StreetEntity> findAll();

  boolean existsByNameAndPostalCode(String name, Long postalCode);

  Optional<StreetEntity> findByPostalCode(Long postalCode);

  Optional<StreetEntity> findByNameAndPostalCode(String name, Long postalCode);
}
