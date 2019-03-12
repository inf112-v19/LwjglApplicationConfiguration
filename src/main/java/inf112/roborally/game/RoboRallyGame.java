package inf112.roborally.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.screens.GameScreen;
import inf112.roborally.game.screens.MenuScreen;
import inf112.roborally.game.screens.TestScreen;

public class RoboRallyGame extends Game {

    //MAPS:
    public static final String VAULT = "assets/gameboard/vault.tmx";
    public static final String SPIRAL_MARATHON = "assets/gameboard/spiralmarathon.tmx";
    public static final String TEST_MAP = "assets/gameboard/testMap.tmx";

    private static final boolean DEBUGGING = false;

    public OrthographicCamera camera;
    public Viewport viewPort;
    public SpriteBatch batch;

    public GameScreen gameScreen;
    public TestScreen registerTestScreen;
    public MenuScreen menuScreen;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.update();
        viewPort = new FitViewport(1920, 1080, camera);
        batch = new SpriteBatch();
//        handler = new CameraHandler(camera);
//        Gdx.input.setInputProcessor(handler);


        if (DEBUGGING) {
            registerTestScreen = new TestScreen();
            setScreen(registerTestScreen);
        }
        else {
            menuScreen = new MenuScreen(this);
            gameScreen = new GameScreen(this, VAULT);
            setScreen(menuScreen);
        }
    }

    @Override
    public void dispose() {
        if (DEBUGGING) {
            registerTestScreen.dispose();
        }
        else {
            gameScreen.dispose();
            menuScreen.dispose();
        }
    }
}
