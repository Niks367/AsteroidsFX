package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * @author corfixen
 */
@Component
public class AsteroidPlugin implements IGamePluginService {
    private static int AsteroidNumber = 3;

    @Override
    public void start(GameData gameData, World world) {
        Random rnd = new Random();
        System.out.println("Spawning " + AsteroidNumber + " asteroids...");

        for (int i = 0; i < AsteroidNumber; i++) {
            Entity asteroid = createAsteroid(gameData, rnd);
            world.addEntity(asteroid);
            System.out.println("Asteroid " + i + " spawned at (" + asteroid.getX() + ", " + asteroid.getY() + ")");
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData, Random random) {
        Asteroid asteroid = new Asteroid(EntityType.ASTEROID);
        System.out.println("Created asteroid with ID: " + asteroid.getID());
        int size = random.nextInt(20) + 10;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(random.nextInt(gameData.getDisplayWidth()));
        asteroid.setY(random.nextInt(gameData.getDisplayHeight()));
        asteroid.setRadius(size);
        asteroid.setRotation(random.nextInt(360));
        double angle = Math.toRadians(random.nextInt(360));
        float speed = random.nextFloat() * 1.5f + 0.5f;
        asteroid.setDx((float) (Math.cos(angle) * speed));
        asteroid.setDy((float) (Math.sin(angle) * speed));
        return asteroid;
    }
}
