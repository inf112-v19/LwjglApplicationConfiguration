package inf112.roborally.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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

    public TestScreen testScreen;
    public MenuScreen menuScreen;
    public SetupScreen setupScreen;
    public GameScreen gameScreen;
    public SettingsScreen settingsScreen;
    public EndGameScreen endGameScreen;

    public int nSkins;
    public String[] possibleRobotSkinFilepaths;

    @Override
    public void create() {

        dynamicCamera = new OrthographicCamera();
        dynamicCamera.setToOrtho(false);
        dynamicCamera.update();
        dynamicViewPort = new FitViewport(1920, 1080, dynamicCamera);
        cameraListener = new CameraListener(dynamicCamera);

        fixedCamera = new OrthographicCamera();
        fixedCamera.update();
        fixedViewPort = new FitViewport(1920, 1080, fixedCamera);

        batch = new SpriteBatch();

        createPossibleFilepaths();

//        testScreen = new TestScreen(this);
        menuScreen = new MenuScreen(this);
//        gameScreen = new GameScreen(this, VAULT);
        endGameScreen = new EndGameScreen(this);
        setScreen(menuScreen);

    }

    private void createPossibleFilepaths() {
        nSkins = 4;
        possibleRobotSkinFilepaths = new String[nSkins];
        possibleRobotSkinFilepaths[0] = "assets/robot/bartenderclaptrap.png";
        possibleRobotSkinFilepaths[1] = "assets/robot/claptrapRefined.png";
        possibleRobotSkinFilepaths[2] = "assets/robot/butlerRefined.png";
        possibleRobotSkinFilepaths[3] = "assets/robot/claptrap3000.png";

    }

    public void newGame() {
        dispose();
        create();
    }

    public void createSetupScreen() {
        setupScreen = new SetupScreen(this, possibleRobotSkinFilepaths);
    }

    public void createGameScreen(int robotChoiceIndex) {
        gameScreen = new GameScreen(this, VAULT, robotChoiceIndex);
    }


    public GameScreen getGameScreen() {
        return this.gameScreen;
    }

    @Override
    public void dispose() {
        batch.dispose();
//        testScreen.dispose();
        menuScreen.dispose();
        // It might not been made yet
        if (setupScreen != null) {
            setupScreen.dispose();
        }

        if(gameScreen != null) {
            gameScreen.dispose();
        }

    }
}
