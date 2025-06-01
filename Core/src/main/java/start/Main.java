package start;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage primaryStage) {
        var ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);
        World world = ctx.getBean(World.class);
        GameData gameData = ctx.getBean(GameData.class);
        List<IEntityProcessingService> entityProcessingServices = ctx.getBean("entityProcessingServiceList", List.class);
        List<IGamePluginService> gamePluginServices = ctx.getBean("gamePluginServices", List.class);
        List<IPostEntityProcessingService> postEntityProcessingServices = ctx.getBean("postEntityProcessingServices", List.class);
        Game game = new Game(gameData, world, gamePluginServices, entityProcessingServices, postEntityProcessingServices);
        game.start(primaryStage);
    }
}
