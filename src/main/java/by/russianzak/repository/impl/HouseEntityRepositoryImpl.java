package by.russianzak.repository.impl;

import by.russianzak.model.HouseEntity;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.HouseEntityRepository;
import by.russianzak.repository.jpa.HouseEntityRepositoryJpa;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class HouseEntityRepositoryImpl implements HouseEntityRepository {

  private final HouseEntityRepositoryJpa houseEntityRepositoryJpa;

  public HouseEntityRepositoryImpl(HouseEntityRepositoryJpa houseEntityRepositoryJpa) {
    this.houseEntityRepositoryJpa = houseEntityRepositoryJpa;
  }

  @Override
  public HouseEntity save(HouseEntity request) {
    return houseEntityRepositoryJpa.save(request);
  }

  @Override
  public void deleteById(Long id) {
    houseEntityRepositoryJpa.deleteById(id);
  }

  @Override
  public void delete(HouseEntity request) {
    houseEntityRepositoryJpa.delete(request);
  }

  @Override
  public HouseEntity update(HouseEntity request) {
    return houseEntityRepositoryJpa.save(request);
  }

  @Override
  public Optional<HouseEntity> findById(Long id) {
    return houseEntityRepositoryJpa.findById(id);
  }

  @Override
  public List<HouseEntity> findAll() {
    return houseEntityRepositoryJpa.findAll();
  }

  @Override
  public boolean existsByHouseNumberAndStreet(String houseNumber, StreetEntity street) {
    return houseEntityRepositoryJpa.existsByHouseNumberAndStreet(houseNumber, street);
  }
}
