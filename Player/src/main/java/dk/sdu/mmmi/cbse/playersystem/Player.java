package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;


/**
 *
 * @author Emil
 */
public class Player extends Entity {
    private double lastShotTime;
    public Player(EntityType ship) {
        super(ship);
        lastShotTime = 0;
    }

    public double getLastShotTime() {
        return lastShotTime;
    }

    public void setLastShotTime(double lastShotTime) {
        this.lastShotTime = lastShotTime;
    }
}