package inf112.roborally.game;

import com.badlogic.gdx.Game;
import inf112.roborally.game.screens.GameScreen;
import inf112.roborally.game.screens.MenuScreen;
import inf112.roborally.game.screens.TestScreen;

public class RoboRallyGame extends Game {

    public static final String VAULT = "assets/gameboard/vault.tmx";
    public static final String SPIRAL_MARATHON = "assets/gameboard/spiralmarathon.tmx";
    private static final boolean DEBUGGING = false;


    //MenuScreen
    //EndScreen
    //etc...

    public GameScreen gameScreen;
    public TestScreen registerTestScreen;
    public MenuScreen menuScreen;

    @Override
    public void create() {
        if(DEBUGGING) {
            registerTestScreen = new TestScreen();
            setScreen(registerTestScreen);
        }
        else {
            menuScreen = new MenuScreen(this);
            gameScreen = new GameScreen(VAULT);
            setScreen(gameScreen);
        }
    }

}
