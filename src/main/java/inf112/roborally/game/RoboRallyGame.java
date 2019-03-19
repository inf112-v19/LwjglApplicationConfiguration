package inf112.roborally.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.gui.CameraListener;
import inf112.roborally.game.screens.GameScreen;
import inf112.roborally.game.screens.MenuScreen;
import inf112.roborally.game.screens.TestScreen;

public class RoboRallyGame extends Game {

    //MAPS:
    public static final String VAULT = "assets/maps/vault.tmx";
    public static final String SPIRAL_MARATHON = "assets/maps/spiralmarathon.tmx";
    public static final String TEST_MAP = "assets/maps/testMap.tmx";
    //Music:
    public static final String MAIN_THEME = "assets/music/Zander Noriega - Perpetual Tension.wav";
    public static final String TEST_MUSIC = "assets/music/testMusic1.ogg";


    private static final boolean DEBUGGING = false;

    public OrthographicCamera camera;
    public Viewport viewPort;
    public SpriteBatch batch;

    public GameScreen gameScreen;
    public TestScreen registerTestScreen;
    public MenuScreen menuScreen;
    public CameraListener cameraListener;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.update();
        viewPort = new FitViewport(1920, 1080, camera);
        batch = new SpriteBatch(11);
        cameraListener = new CameraListener(camera);


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
