import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Enemy {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;
    provides IGamePluginService
            with main.EnemyShipPlugin;
    provides IEntityProcessingService
            with main.EnemyShipControlSystem;
}