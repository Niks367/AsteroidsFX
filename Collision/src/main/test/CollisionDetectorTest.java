import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CollisionDetectorTest {

    @Mock
    private GameData gameData;

    @Mock
    private World world;

    @Mock
    private Entity shipEntity;

    @Mock
    private Entity asteroidEntity;

    @Mock
    private Asteroid asteroid;

    @Mock
    private Entity bulletEntity;

    private CollisionDetector collisionDetector;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        collisionDetector = new CollisionDetector();
    }

    @Test
    public void testProcessNoCollisions() {
        List<Entity> entities = new ArrayList<>();
        when(world.getEntities()).thenReturn(entities);

        collisionDetector.process(gameData, world);

        verifyZeroInteractions(gameData);
    }

    @Test
    public void testProcessCollisionShipAndAsteroid() {
        when(shipEntity.getID()).thenReturn("1");
        when(shipEntity.getType()).thenReturn(EntityType.SHIP);
        when(asteroidEntity.getID()).thenReturn("2");
        when(asteroidEntity.getType()).thenReturn(EntityType.ASTEROID);

        List<Entity> entities = new ArrayList<>();
        entities.add(shipEntity);
        entities.add(asteroidEntity);
        when(world.getEntities()).thenReturn(entities);

        collisionDetector.process(gameData, world);

        verify(world).removeEntity(shipEntity);
        verify(world).removeEntity(asteroidEntity);
    }

    @Test
    public void testProcessCollisionBulletAndAsteroid() {
        when(bulletEntity.getID()).thenReturn("1");
        when(bulletEntity.getType()).thenReturn(EntityType.BULLET);
        when(asteroidEntity.getID()).thenReturn("2");
        when(asteroidEntity.getType()).thenReturn(EntityType.ASTEROID);

        List<Entity> entities = new ArrayList<>();
        entities.add(bulletEntity);
        entities.add(asteroidEntity);
        when(world.getEntities()).thenReturn(entities);

        collisionDetector.process(gameData, world);

        verify(world).removeEntity(bulletEntity);
    }

    @Test
    public void testSplitAsteroid() {

        Asteroid largeAsteroid = mock(Asteroid.class);
        when(largeAsteroid.getRadius()).thenReturn(20.0f);

        collisionDetector.splitAsteroid(gameData, world, largeAsteroid);

        verify(world).addEntity(any(Entity.class));
    }

    @Test
    public void testCollides() {
        when(shipEntity.getX()).thenReturn(10.0);
        when(shipEntity.getY()).thenReturn(5.0);
        when(shipEntity.getRadius()).thenReturn(2.0f);

        when(bulletEntity.getX()).thenReturn(9.0);
        when(bulletEntity.getY()).thenReturn(4.0);
        when(bulletEntity.getRadius()).thenReturn(1.0f);

        boolean result = collisionDetector.collides(shipEntity, bulletEntity);

        assertTrue(result);
    }

}