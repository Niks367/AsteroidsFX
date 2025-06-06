package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {
    /**
     * Calls the entities processes, which get constantly updated during the game.
     * @param gameData
     * @param world
     */
    void process(GameData gameData, World world);
}
