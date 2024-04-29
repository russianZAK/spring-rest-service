package by.russianzak.repository;

import by.russianzak.config.DatabaseConfig;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.impl.StreetEntityRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {DatabaseConfig.class})
@Testcontainers
@Transactional
public class StreetEntityRepositoryImplTest {

  @Autowired
  private StreetEntityRepositoryImpl streetEntityRepository;

  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

  @BeforeEach
  public void setUp() {
    streetEntityRepository.deleteAll();
  }

  @DynamicPropertySource
  static void setDataSourceProperties(DynamicPropertyRegistry registry) {
    registry.add("datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("datasource.username", postgreSQLContainer::getUsername);
    registry.add("datasource.password", postgreSQLContainer::getPassword);
    registry.add("datasource.driver-class-name", () -> "org.postgresql.Driver");
  }

  @Test
  void testSave() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setName("Main Street");
    streetEntity.setPostalCode(12345L);

    StreetEntity savedStreetEntity = streetEntityRepository.save(streetEntity);

    assertEquals("Main Street", savedStreetEntity.getName());
    assertEquals(12345L, savedStreetEntity.getPostalCode());
  }

  @Test
  void testDeleteById() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setName("Main Street");
    streetEntity.setPostalCode(12345L);
    streetEntityRepository.save(streetEntity);

    streetEntityRepository.deleteById(streetEntity.getId());
    assertFalse(streetEntityRepository.findById(streetEntity.getId()).isPresent());
  }

  @Test
  void testUpdate() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setName("Main Street");
    streetEntity.setPostalCode(12345L);
    streetEntityRepository.save(streetEntity);

    streetEntity.setName("Second Street");
    StreetEntity updatedStreetEntity = streetEntityRepository.update(streetEntity);

    assertEquals("Second Street", updatedStreetEntity.getName());
  }

  @Test
  void testFindById() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setName("Main Street");
    streetEntity.setPostalCode(12345L);
    streetEntityRepository.save(streetEntity);

    Optional<StreetEntity> optionalStreetEntity = streetEntityRepository.findById(streetEntity.getId());

    assertTrue(optionalStreetEntity.isPresent());
    assertEquals("Main Street", optionalStreetEntity.get().getName());
  }

  @Test
  void testFindAll() {
    StreetEntity streetEntity1 = new StreetEntity();
    streetEntity1.setName("Main Street");
    streetEntity1.setPostalCode(12345L);
    streetEntityRepository.save(streetEntity1);

    StreetEntity streetEntity2 = new StreetEntity();
    streetEntity2.setName("Second Street");
    streetEntity2.setPostalCode(54321L);
    streetEntityRepository.save(streetEntity2);

    List<StreetEntity> streetEntities = streetEntityRepository.findAll();

    assertEquals(2, streetEntities.size());
  }

  @Test
  void testExistsByNameAndPostalCode() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setName("Main Street");
    streetEntity.setPostalCode(12345L);
    streetEntityRepository.save(streetEntity);

    assertTrue(streetEntityRepository.existsByNameAndPostalCode("Main Street", 12345L));
    assertFalse(streetEntityRepository.existsByNameAndPostalCode("Second Street", 54321L));
  }

  @Test
  void testFindByPostalCode() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setName("Main Street");
    streetEntity.setPostalCode(12345L);
    streetEntityRepository.save(streetEntity);

    Optional<StreetEntity> optionalStreetEntity = streetEntityRepository.findByPostalCode(12345L);

    assertTrue(optionalStreetEntity.isPresent());
    assertEquals("Main Street", optionalStreetEntity.get().getName());
  }

  @Test
  void testFindByNameAndPostalCode() {
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setName("Main Street");
    streetEntity.setPostalCode(12345L);
    streetEntityRepository.save(streetEntity);

    Optional<StreetEntity> optionalStreetEntity = streetEntityRepository.findByNameAndPostalCode("Main Street", 12345L);

    assertTrue(optionalStreetEntity.isPresent());
    assertEquals("Main Street", optionalStreetEntity.get().getName());
  }
}
