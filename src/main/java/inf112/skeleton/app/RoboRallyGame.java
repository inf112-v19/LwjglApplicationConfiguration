<<<<<<< HEAD
package inf112.skeleton.app;

import com.badlogic.gdx.Game;

public class RoboRallyGame extends Game {

    public GameScreen gameScreen;

    @Override
    public void create() {
        Assets.load();
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }
}
=======
package inf112.skeleton.app;

import com.badlogic.gdx.Game;

public class RoboRallyGame extends Game {

    public GameScreen gameScreen;

    @Override
    public void create() {
        Assets.load();
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }
}
>>>>>>> 86cd0dd78e6a0d8fd3393d2e3dfcf57c5be97f20
