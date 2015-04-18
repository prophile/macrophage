package uk.co.alynn.games.macrophage.desktop;

import uk.co.alynn.games.macrophage.MacrophageGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = 1024;
        config.height = 640;

        config.useHDPI = true;

        config.resizable = true;

        config.title = "Macrophage";

        new LwjglApplication(new MacrophageGame(), config);
    }
}
