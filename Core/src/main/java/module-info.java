module Core {
    exports start;
    requires Common;
    requires javafx.graphics;
    requires spring.core;
    requires spring.beans;
    requires java.net.http;
    requires spring.context;
    requires spring.web;
    opens start to javafx.graphics,spring.core, spring.beans, spring.context;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
}


