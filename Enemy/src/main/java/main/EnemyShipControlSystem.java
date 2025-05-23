package main;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class EnemyShipControlSystem implements IEntityProcessingService {

    private static final double SPEED = 1.5;
    private static final double TURN_RATE = 5;
    private static final double SHOOT_INTERVAL = 2_000_000_000; // 2 seconds
    private long lastShotTime = 0;
    private final Random random = new Random();


    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(EnemyShip.class)) {

            // Randomly change direction
            if (random.nextDouble() < 0.02) { // 2% chance per frame
                enemy.setRotation(enemy.getRotation() + (random.nextBoolean() ? TURN_RATE : -TURN_RATE));
            }

            // Move forward
            double radians = Math.toRadians(enemy.getRotation());
            enemy.setX(enemy.getX() + Math.cos(radians) * SPEED);
            enemy.setY(enemy.getY() + Math.sin(radians) * SPEED);

            // Screen wrapping
            if (enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
            if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
            if (enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
            if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);
            // Shoot bullets every interval
            if (gameData.getTime() - lastShotTime > SHOOT_INTERVAL) {
                shootBullet(enemy, world);
                lastShotTime = gameData.getTime();
            }
        }
    }

    private void shootBullet(Entity enemy, World world) {
        Bullet bullet = new Bullet(EntityType.BULLET);
        bullet.setX(enemy.getX());
        bullet.setY(enemy.getY());
        bullet.setRotation(enemy.getRotation());

        double radians = Math.toRadians(enemy.getRotation());
        bullet.setVelocityX(Math.cos(radians) * 5);
        bullet.setVelocityY(Math.sin(radians) * 5);

        world.addEntity(bullet);
    }
}