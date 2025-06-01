package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {

    private static final double ROTATION_SPEED = 5;
    private static final long SHOT_COOLDOWN = 200;
    private Collection<? extends BulletSPI> bulletSPIS;
    private BulletControlSystem bulletControlSystem;
    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - ROTATION_SPEED);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + ROTATION_SPEED);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            Player player1 = (Player) player;
            long currentTime = System.currentTimeMillis();
            if(gameData.getKeys().isDown(GameKeys.SPACE) && (currentTime - player1.getLastShotTime()) >= SHOT_COOLDOWN) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            bulletControlSystem = new BulletControlSystem();
                            Entity bullet = bulletControlSystem.createBullet(player, gameData);
                            world.addEntity(bullet);
                            player1.setLastShotTime(currentTime);
                        }
                );
            }


            double width = gameData.getDisplayWidth();
            double height = gameData.getDisplayHeight();

            if (player.getX() < 0) {
                player.setX(width);
            } else if (player.getX() > width) {
                player.setX(0);
            }

            if (player.getY() < 0) {
                player.setY(height);
            } else if (player.getY() > height) {
                player.setY(0);
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}