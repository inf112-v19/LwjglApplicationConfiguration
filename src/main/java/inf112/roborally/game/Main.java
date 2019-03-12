package inf112.roborally.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static final int PIXELS_PER_TILE = 32;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = 1280/3*2;
        cfg.height = 720/3*2;

        new LwjglApplication(new RoboRallyGame(), cfg);
    }
}