package inf112.skeleton.app;

import com.badlogic.gdx.Game;

public class RoboRallyGame extends Game {

    //MenuScreen
    //EndScreen
    //etc...

    public GameScreen gameScreen;


    @Override
    public void create() {

        gameScreen = new GameScreen(Main.VAULT);
        setScreen(gameScreen);
    }

}
