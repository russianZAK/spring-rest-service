package by.russianzak.repository.impl;

import by.russianzak.model.StreetEntity;
import by.russianzak.repository.jpa.StreetEntityRepositoryJpa;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class StreetEntityRepositoryImpl implements by.russianzak.repository.StreetEntityRepository {

  private final StreetEntityRepositoryJpa streetEntityRepository;

  public StreetEntityRepositoryImpl(StreetEntityRepositoryJpa streetEntityRepository) {
    this.streetEntityRepository = streetEntityRepository;
  }

  @Override
  public StreetEntity save(StreetEntity request) {
    return streetEntityRepository.save(request);
  }

  @Override
  public void deleteById(Long id) {
    streetEntityRepository.deleteById(id);
  }

  @Override
  public void delete(StreetEntity request) {
    streetEntityRepository.delete(request);
  }

  @Override
  public StreetEntity update(StreetEntity request) {
    return streetEntityRepository.save(request);
  }

  @Override
  public Optional<StreetEntity> findById(Long id) {
    return streetEntityRepository.findById(id);
  }

  @Override
  public List<StreetEntity> findAll() {
    return streetEntityRepository.findAll();
  }

  @Override
  public boolean existsByNameAndPostalCode(String name, Long postalCode) {
    return streetEntityRepository.existsByNameAndPostalCode(name, postalCode);
  }

  @Override
  public Optional<StreetEntity> findByPostalCode(Long postalCode) {
    return streetEntityRepository.findByPostalCode(postalCode);
  }

  @Override
  public Optional<StreetEntity> findByNameAndPostalCode(String name, Long postalCode) {
    return streetEntityRepository.findByNameAndPostalCode(name, postalCode);
  }
}
