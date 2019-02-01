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
