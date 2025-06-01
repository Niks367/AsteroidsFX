package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());

        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity2 = entities.get(j);

                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                if (collides(entity1, entity2)) {
                    handleCollision(gameData, world, entity1, entity2);
                }
            }
        }
    }

    private void handleCollision(GameData gameData, World world, Entity entity1, Entity entity2) {
        EntityType type1 = entity1.getType();
        EntityType type2 = entity2.getType();
        System.out.println(type1);
        System.out.println(type2);
        if (type1 == EntityType.SHIP && type2 == EntityType.ASTEROID) {
            destroyShip(gameData, world, entity1);
            destroyAsteroid(gameData, world, entity2);
        } else if (type1 == EntityType.ASTEROID && type2 == EntityType.SHIP) {
            destroyShip(gameData, world, entity2);
            destroyAsteroid(gameData, world, entity1);
        } else if (type1 == EntityType.BULLET && type2 == EntityType.ASTEROID) {
            splitAsteroid(gameData, world, entity2);
            world.removeEntity(entity1);
        } else if (type1 == EntityType.ASTEROID && type2 == EntityType.BULLET) {
            splitAsteroid(gameData, world, entity1);
            world.removeEntity(entity2);
        } else if (type1 == EntityType.BULLET && type2 == EntityType.ENEMY) {
            destroyShip(gameData, world, entity2);
            world.removeEntity(entity2);
        } else if (type1 == EntityType.ENEMY && type2 == EntityType.BULLET) {
            destroyShip(gameData, world, entity1);
            world.removeEntity(entity1);
        }
    }

    private void destroyShip(GameData gameData, World world, Entity ship) {
        world.removeEntity(ship);
    }

    private void destroyAsteroid(GameData gameData, World world, Entity asteroid) {
        world.removeEntity(asteroid);
    }

    public void splitAsteroid(GameData gameData, World world, Entity asteroid) {
        if (asteroid.getRadius() > 10) {
            double newRadius = asteroid.getRadius() / 2;

            System.out.println("Splitting Asteroid at position: (" + asteroid.getX() + ", " + asteroid.getY() + ") with radius: " + asteroid.getRadius());

            Entity smallerAsteroid1 = createNewAsteroid((Asteroid) asteroid, newRadius, -30);
            Entity smallerAsteroid2 = createNewAsteroid((Asteroid) asteroid, newRadius, 30);

            System.out.println("Created Smaller Asteroid 1 at position: (" + smallerAsteroid1.getX() + ", " + smallerAsteroid1.getY() + ") with radius: " + smallerAsteroid1.getRadius());
            System.out.println("Created Smaller Asteroid 2 at position: (" + smallerAsteroid2.getX() + ", " + smallerAsteroid2.getY() + ") with radius: " + smallerAsteroid2.getRadius());

            String id1 = world.addEntity(smallerAsteroid1);
            String id2 = world.addEntity(smallerAsteroid2);

            System.out.println("Smaller Asteroid 1 Added: " + (id1 != null));
            System.out.println("Smaller Asteroid 2 Added: " + (id2 != null));

        } else {
            System.out.println("Asteroid too small to split, destroying at position: (" + asteroid.getX() + ", " + asteroid.getY() + ") with radius: " + asteroid.getRadius());
        }

        destroyAsteroid(gameData, world, asteroid);
    }

    private Entity createNewAsteroid(Asteroid original, double radius, double offset) {
        Asteroid newAsteroid = new Asteroid(EntityType.ASTEROID);
        newAsteroid.setPolygonCoordinates(radius, -radius, -radius, -radius, -radius, radius, radius, radius);
        newAsteroid.setX(original.getX() + offset);
        newAsteroid.setY(original.getY() + offset);
        newAsteroid.setRotation(original.getRotation() + offset);
        newAsteroid.setRadius((float) radius);
        newAsteroid.setDx(original.getDx());
        newAsteroid.setDy(original.getDy());
        return newAsteroid;
    }


    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

}
