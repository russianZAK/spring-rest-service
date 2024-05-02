package by.russianzak.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.russianzak.config.DatabaseConfig;
import by.russianzak.model.HouseEntity;
import by.russianzak.model.HouseEntity.TypeOfBuilding;
import by.russianzak.model.StreetEntity;
import by.russianzak.repository.impl.HouseEntityRepositoryImpl;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringJUnitConfig(classes = {DatabaseConfig.class})
@Testcontainers
@Transactional
public class HouseEntityRepositoryImplTest {
  @Autowired
  private HouseEntityRepositoryImpl houseEntityRepository;

  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

  @BeforeAll
  static void beforeAll() {
    postgreSQLContainer.start();
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
    HouseEntity houseEntity = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("123").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(12L).build();
    houseEntity.setStreet(StreetEntity.builder().setName("Main Street").setPostalCode(345L).build());
    HouseEntity savedHouseEntity = houseEntityRepository.save(houseEntity);

    assertEquals("123", savedHouseEntity.getHouseNumber());
    assertEquals("Main Street", savedHouseEntity.getStreet().getName());
  }

  @Test
  void testDelete() {
    HouseEntity houseEntity = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("123").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(12L).build();
    houseEntity.setStreet(StreetEntity.builder().setName("Main Street").setPostalCode(345L).build());
    houseEntityRepository.save(houseEntity);

    houseEntityRepository.delete(houseEntity);
    assertFalse(houseEntityRepository.findById(houseEntity.getId()).isPresent());
  }

  @Test
  void testDeleteById() {
    HouseEntity houseEntity = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("123").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(12L).build();
    houseEntity.setStreet(StreetEntity.builder().setName("Main Street").setPostalCode(345L).build());
    houseEntityRepository.save(houseEntity);

    houseEntityRepository.deleteById(houseEntity.getId());
    assertFalse(houseEntityRepository.findById(houseEntity.getId()).isPresent());
  }

  @Test
  void testUpdate() {
    HouseEntity houseEntity = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("123").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(12L).build();
    houseEntity.setStreet(StreetEntity.builder().setName("Main Street").setPostalCode(345L).build());
    houseEntityRepository.save(houseEntity);

    houseEntity.setHouseNumber("456");
    HouseEntity updatedHouseEntity = houseEntityRepository.update(houseEntity);

    assertEquals("456", updatedHouseEntity.getHouseNumber());
  }

  @Test
  void testGetById() {
    HouseEntity houseEntity = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("123").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(12L).build();
    houseEntity.setStreet(StreetEntity.builder().setName("Main Street").setPostalCode(345L).build());
    houseEntityRepository.save(houseEntity);

    Optional<HouseEntity> optionalHouseEntity = houseEntityRepository.findById(houseEntity.getId());

    assertTrue(optionalHouseEntity.isPresent());
    assertEquals("123", optionalHouseEntity.get().getHouseNumber());
  }

  @Test
  void testFindById() {
    HouseEntity houseEntity = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("123").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(12L).build();
    houseEntity.setStreet(StreetEntity.builder().setName("Main Street").setPostalCode(345L).build());
    houseEntityRepository.save(houseEntity);

    Optional<HouseEntity> optionalHouseEntity = houseEntityRepository.findById(houseEntity.getId());

    assertTrue(optionalHouseEntity.isPresent());
    assertEquals("123", optionalHouseEntity.get().getHouseNumber());
  }

  @Test
  void testFindAll() {
    HouseEntity houseEntity1 = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("123").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(12L).build();
    houseEntity1.setStreet(StreetEntity.builder().setName("Main Street").setPostalCode(345L).build());
    houseEntityRepository.save(houseEntity1);

    HouseEntity houseEntity2 = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("1234").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(123L).build();
    houseEntity2.setStreet(StreetEntity.builder().setName("Second Street").setPostalCode(3455L).build());
    houseEntityRepository.save(houseEntity2);

    List<HouseEntity> houseEntities = houseEntityRepository.findAll();

    assertEquals(2, houseEntities.size());
  }

  @Test
  void testExistsByHouseNumberAndStreet() {
    HouseEntity houseEntity = HouseEntity.builder().setBuildDate(new Date()).setHouseNumber("123").setType(TypeOfBuilding.COMMERCIAL).setNumFloors(12L).build();
    houseEntity.setStreet(StreetEntity.builder().setName("Main Street").setPostalCode(345L).build());
    HouseEntity saveHouseEntity = houseEntityRepository.save(houseEntity);

    assertTrue(houseEntityRepository.existsByHouseNumberAndStreet("123", saveHouseEntity.getStreet()));
    assertFalse(houseEntityRepository.existsByHouseNumberAndStreet("456", saveHouseEntity.getStreet()));
  }

}
