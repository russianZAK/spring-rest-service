package by.russianzak.repository.jpa;

import by.russianzak.model.HouseEntity;
import by.russianzak.model.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseEntityRepositoryJpa extends JpaRepository<HouseEntity, Long> {
  boolean existsByHouseNumberAndStreet(String houseNumber, StreetEntity street);
}
