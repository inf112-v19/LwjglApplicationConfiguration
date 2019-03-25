package inf112.roborally.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.roborally.game.gui.AssMan;
import inf112.roborally.game.gui.CameraListener;
import inf112.roborally.game.screens.*;

public class RoboRallyGame extends Game {
    //MAPS:
    public static final String VAULT = "assets/maps/vault.tmx";
    public static final String SPIRAL_MARATHON = "assets/maps/spiralmarathon.tmx";
    public static final String TEST_MAP = "assets/maps/testMap.tmx";
    //Music:
    public static final String MAIN_THEME = "assets/music/Zander Noriega - Perpetual Tension.wav";
    public static final String TEST_MUSIC = "assets/music/testMusic1.ogg";


    public OrthographicCamera dynamicCamera;
    public Viewport dynamicViewPort;
    public CameraListener cameraListener;

    public OrthographicCamera fixedCamera; //the position of this camera should never change!
    public FitViewport fixedViewPort;

    public SpriteBatch batch;

    public GameScreen gameScreen;
    public TestScreen testScreen;
    public MenuScreen menuScreen;
    public SettingsScreen settingsScreen;
    public EndGameScreen endGameScreen;

    private AssMan assMan;

    @Override
    public void create() {
        assMan = new AssMan();
        assMan.load();
        assMan.manager.finishLoading();

        dynamicCamera = new OrthographicCamera();
        dynamicCamera.setToOrtho(false);
        dynamicCamera.update();
        dynamicViewPort = new FitViewport(1920, 1080, dynamicCamera);
        cameraListener = new CameraListener(dynamicCamera);

        fixedCamera = new OrthographicCamera();
        fixedCamera.update();
        fixedViewPort = new FitViewport(1920, 1080, fixedCamera);

        batch = new SpriteBatch();

        testScreen = new TestScreen(this);
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this, VAULT);
        settingsScreen = new SettingsScreen(this);
        endGameScreen = new EndGameScreen(this);
        setScreen(menuScreen);

    }

    public void newGame() {
        dispose();
        create();
    }

    public GameScreen getGameScreen() {
        return this.gameScreen;
    }


    public AssMan getAssMan(){
        return assMan;
    }

    @Override
    public void dispose() {
        batch.dispose();
        testScreen.dispose();
        gameScreen.dispose();
        menuScreen.dispose();
    }
}
