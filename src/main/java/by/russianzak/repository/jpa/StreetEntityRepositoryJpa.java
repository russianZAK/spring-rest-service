package by.russianzak.repository.jpa;

import by.russianzak.model.StreetEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetEntityRepositoryJpa extends JpaRepository<StreetEntity, Long> {
  boolean existsByNameAndPostalCode(String name, Long postalCode);
  Optional<StreetEntity> findByPostalCode(Long postalCode);
  Optional<StreetEntity> findByNameAndPostalCode(String name, Long postalCode);
}
