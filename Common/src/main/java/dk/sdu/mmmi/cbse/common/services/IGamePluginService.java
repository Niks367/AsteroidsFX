package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {
    /**
     * Starts the given plugin in the game.
     * @param gameData      The data of the game
     * @param world         The data of the world
     */
    void start(GameData gameData, World world);

    /**
     * Stops the given plugin in the game.
     * @param gameData      The data of the game
     * @param world         The data of the world
     */
    void stop(GameData gameData, World world);
}
