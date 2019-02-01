<<<<<<< HEAD
package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = 1920;
        cfg.height = 1080;

        new LwjglApplication(new RoboRallyGame(), cfg);
    }
=======
package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = 1920;
        cfg.height = 1080;

        new LwjglApplication(new RoboRallyGame(), cfg);
    }
>>>>>>> 86cd0dd78e6a0d8fd3393d2e3dfcf57c5be97f20
}