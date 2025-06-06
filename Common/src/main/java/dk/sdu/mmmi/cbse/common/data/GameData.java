package dk.sdu.mmmi.cbse.common.data;

import org.springframework.stereotype.Component;

@Component
public class GameData {

    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private int time = 0;
    private final GameKeys keys = new GameKeys();


    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }
    public int getTime(){
        return time;
    }


}
