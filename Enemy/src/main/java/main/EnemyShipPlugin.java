package main;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyShipPlugin implements IGamePluginService {

    private Entity enemyShip;

    @Override
    public void start(GameData gameData, World world) {
        enemyShip = createEnemyShip(gameData);
        world.addEntity(enemyShip);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemyShip);
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemy = new EnemyShip(EntityType.ENEMY);
        enemy.setX(Math.random() * gameData.getDisplayWidth());
        enemy.setY(Math.random() * gameData.getDisplayHeight());
        enemy.setRotation(Math.random() * 360);
        enemy.setPolygonCoordinates(-10, -10, 10, 0, -10, 10);
        return enemy;
    }
}