package by.russianzak.repository;

import by.russianzak.config.DatabaseConfig;
import by.russianzak.model.RoadSurfaceEntity;
import by.russianzak.model.RoadSurfaceEntity.TypeOfRoadSurface;
import by.russianzak.repository.impl.RoadSurfaceEntityRepositoryImpl;
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
public class RoadSurfaceEntityRepositoryImplTest {

  @Autowired
  private RoadSurfaceEntityRepositoryImpl roadSurfaceEntityRepository;

  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

  @BeforeEach
  public void setUp() {
    roadSurfaceEntityRepository.deleteAll();
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
    RoadSurfaceEntity roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setDescription("Smooth asphalt road");
    roadSurfaceEntity.setFrictionCoefficient(0.8);

    RoadSurfaceEntity savedRoadSurfaceEntity = roadSurfaceEntityRepository.save(roadSurfaceEntity);

    assertEquals(TypeOfRoadSurface.ASPHALT, TypeOfRoadSurface.fromValue(savedRoadSurfaceEntity.getType()));
    assertEquals("Smooth asphalt road", savedRoadSurfaceEntity.getDescription());
    assertEquals(0.8, savedRoadSurfaceEntity.getFrictionCoefficient());
  }

  @Test
  void testDeleteById() {
    RoadSurfaceEntity roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setDescription("Smooth asphalt road");
    roadSurfaceEntity.setFrictionCoefficient(0.8);
    roadSurfaceEntityRepository.save(roadSurfaceEntity);

    roadSurfaceEntityRepository.deleteById(roadSurfaceEntity.getId());
    assertFalse(roadSurfaceEntityRepository.findById(roadSurfaceEntity.getId()).isPresent());
  }

  @Test
  void testUpdate() {
    RoadSurfaceEntity roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setDescription("Smooth asphalt road");
    roadSurfaceEntity.setFrictionCoefficient(0.8);
    roadSurfaceEntityRepository.save(roadSurfaceEntity);

    roadSurfaceEntity.setDescription("Rough asphalt road");
    RoadSurfaceEntity updatedRoadSurfaceEntity = roadSurfaceEntityRepository.update(roadSurfaceEntity);

    assertEquals("Rough asphalt road", updatedRoadSurfaceEntity.getDescription());
  }

  @Test
  void testFindById() {
    RoadSurfaceEntity roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setDescription("Smooth asphalt road");
    roadSurfaceEntity.setFrictionCoefficient(0.8);
    roadSurfaceEntityRepository.save(roadSurfaceEntity);

    Optional<RoadSurfaceEntity> optionalRoadSurfaceEntity = roadSurfaceEntityRepository.findById(roadSurfaceEntity.getId());

    assertTrue(optionalRoadSurfaceEntity.isPresent());
    assertEquals(TypeOfRoadSurface.ASPHALT, TypeOfRoadSurface.fromValue(optionalRoadSurfaceEntity.get().getType()));
    assertEquals("Smooth asphalt road", optionalRoadSurfaceEntity.get().getDescription());
    assertEquals(0.8, optionalRoadSurfaceEntity.get().getFrictionCoefficient());
  }

  @Test
  void testFindAll() {
    RoadSurfaceEntity roadSurfaceEntity1 = new RoadSurfaceEntity();
    roadSurfaceEntity1.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity1.setDescription("Smooth asphalt road");
    roadSurfaceEntity1.setFrictionCoefficient(0.8);
    roadSurfaceEntityRepository.save(roadSurfaceEntity1);

    RoadSurfaceEntity roadSurfaceEntity2 = new RoadSurfaceEntity();
    roadSurfaceEntity2.setType(TypeOfRoadSurface.CONCRETE);
    roadSurfaceEntity2.setDescription("Rough concrete road");
    roadSurfaceEntity2.setFrictionCoefficient(0.7);
    roadSurfaceEntityRepository.save(roadSurfaceEntity2);

    List<RoadSurfaceEntity> roadSurfaceEntities = roadSurfaceEntityRepository.findAll();

    assertEquals(2, roadSurfaceEntities.size());
  }

  @Test
  void testExistsByType() {
    RoadSurfaceEntity roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setDescription("Smooth asphalt road");
    roadSurfaceEntity.setFrictionCoefficient(0.8);
    RoadSurfaceEntity savedRoadSurfaceEntity = roadSurfaceEntityRepository.save(roadSurfaceEntity);

    assertTrue(roadSurfaceEntityRepository.existsByType(TypeOfRoadSurface.ASPHALT));
    assertFalse(roadSurfaceEntityRepository.existsByType(TypeOfRoadSurface.CONCRETE));
  }

  @Test
  void testFindByType() {
    RoadSurfaceEntity roadSurfaceEntity = new RoadSurfaceEntity();
    roadSurfaceEntity.setType(TypeOfRoadSurface.ASPHALT);
    roadSurfaceEntity.setDescription("Smooth asphalt road");
    roadSurfaceEntity.setFrictionCoefficient(0.8);
    RoadSurfaceEntity savedRoadSurfaceEntity = roadSurfaceEntityRepository.save(roadSurfaceEntity);

    Optional<RoadSurfaceEntity> optionalRoadSurfaceEntity = roadSurfaceEntityRepository.findByType(TypeOfRoadSurface.ASPHALT);

    assertTrue(optionalRoadSurfaceEntity.isPresent());
    assertEquals(TypeOfRoadSurface.ASPHALT, TypeOfRoadSurface.fromValue(optionalRoadSurfaceEntity.get().getType()));
    assertEquals("Smooth asphalt road", optionalRoadSurfaceEntity.get().getDescription());
    assertEquals(0.8, optionalRoadSurfaceEntity.get().getFrictionCoefficient());
  }
}
