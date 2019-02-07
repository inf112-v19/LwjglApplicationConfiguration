package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1280;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = GAME_WIDTH;
        cfg.height = GAME_HEIGHT;

        new LwjglApplication(new RoboRallyGame(), cfg);
    }
}