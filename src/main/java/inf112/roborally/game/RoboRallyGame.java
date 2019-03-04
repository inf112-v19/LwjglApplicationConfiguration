package inf112.roborally.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.screens.GameScreen;
import inf112.roborally.game.screens.MenuScreen;
import inf112.roborally.game.screens.TestScreen;

public class RoboRallyGame extends Game {

    public static final String VAULT = "assets/gameboard/vault.tmx";
    public static final String SPIRAL_MARATHON = "assets/gameboard/spiralmarathon.tmx";
    private static final boolean DEBUGGING = false;

    public OrthographicCamera camera;
    public Viewport viewPort;

    public GameScreen gameScreen;
    public TestScreen registerTestScreen;
    public MenuScreen menuScreen;
    public SpriteBatch batch;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.update();
        viewPort = new FitViewport(1920, 1080, camera);
        batch = new SpriteBatch();


        if(DEBUGGING) {
            registerTestScreen = new TestScreen();
            setScreen(registerTestScreen);
        }
        else {
            menuScreen = new MenuScreen(this);
            gameScreen = new GameScreen(this, VAULT);
            setScreen(menuScreen);
        }
    }

}
