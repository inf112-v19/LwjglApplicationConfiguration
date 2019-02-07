package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static final String TEST_MAP = "assets/gameboard/testMap.tmx";
    public static final String VAULT = "assets/gameboard/vault.tmx";

    public static final int GAME_WIDTH = 1536;
    public static final int GAME_HEIGHT = 864;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = 1280/2;
        cfg.height = 720/2;

        new LwjglApplication(new RoboRallyGame(), cfg);
    }
}