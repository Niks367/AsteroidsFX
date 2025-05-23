package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;

/**
 * @author corfixen
 */
public class Asteroid extends Entity {
    private float dx, dy;

    public Asteroid(EntityType entityType) {
        super(entityType);

    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }
    public float getDy(){
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }
}
