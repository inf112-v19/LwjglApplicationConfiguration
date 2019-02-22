package inf112.roborally.game;

import com.badlogic.gdx.Game;
import inf112.roborally.game.screens.GameScreen;
import inf112.roborally.game.screens.MenuScreen;

public class RoboRallyGame extends Game {

    //MenuScreen
    //EndScreen
    //etc...

    public GameScreen gameScreen;

    @Override
    public void create() {

        MenuScreen menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

}
