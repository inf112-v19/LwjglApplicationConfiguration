package inf112.roborally.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static final int UNIT_SCALE = 2;
    public static final int PIXELS_PER_TILE = 32;
    public static final int TILE_LENGTH = PIXELS_PER_TILE *UNIT_SCALE;

    // Maps:
    public static final String TEST_MAP = "assets/gameboard/testMap.tmx";
    public static final String VAULT = "assets/gameboard/vault.tmx";


    public static final int GAME_WIDTH = 1536;
    public static final int GAME_HEIGHT = 864;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = 1280/3*2;
        cfg.height = 720/3*2;
//        cfg.width = 1280;
//        cfg.height = 720;

        new LwjglApplication(new RoboRallyGame(), cfg);
    }
}