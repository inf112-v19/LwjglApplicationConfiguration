package inf112.roborally.game;

import com.badlogic.gdx.Game;
import inf112.roborally.game.screens.GameScreen;
import inf112.roborally.game.screens.MenuScreen;
import inf112.roborally.game.screens.TestScreen;

public class RoboRallyGame extends Game {

    //MenuScreen
    //EndScreen
    //etc...

    public GameScreen gameScreen;
    public TestScreen testScreen;
    public MenuScreen menuScreen;

    @Override
    public void create() {
        testScreen = new TestScreen();
        menuScreen = new MenuScreen(this);

        setScreen(testScreen);
    }

}
