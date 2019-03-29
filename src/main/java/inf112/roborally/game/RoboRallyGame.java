package inf112.roborally.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.gui.AssMan;
import inf112.roborally.game.gui.CameraListener;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.screens.*;

import java.util.ArrayList;

public class RoboRallyGame extends Game {
    //MAPS:
    public static final String VAULT = "assets/maps/vault.tmx";
    public static final String SPIRAL_MARATHON = "assets/maps/spiralmarathon.tmx";
    public static final String TEST_MAP = "assets/maps/testMap.tmx";
    public static final String LASER_TEST_MAP = "assets/maps/lasertest.tmx";
    //Music:
    public static final String MAIN_THEME = "assets/music/Zander Noriega - Perpetual Tension.wav";
    public static final String TEST_MUSIC = "assets/music/testMusic1.ogg";

    public static final int MAX_PLAYERS = 8;


    public OrthographicCamera dynamicCamera;
    public Viewport dynamicViewPort;
    public CameraListener cameraListener;

    public OrthographicCamera fixedCamera; //the position of this camera should never change!
    public FitViewport fixedViewPort;

    public SpriteBatch batch;

    public MenuScreen menuScreen;
    public SetupScreen setupScreen;
    public GameScreen gameScreen;
    public SettingsScreen settingsScreen;
    public EndGameScreen endGameScreen;

    public TestScreen testScreen;
    public LaserTestScreen laserTestScreen;

    private AssMan assMan;

    //The screen that was active before setting a new screen with setScreen(screen);
    private Screen screenBefore;


    public int nSkins;
    public String[] possibleRobotSkinFilepaths;

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

        createPossibleFilepaths();

        menuScreen = new MenuScreen(this);
//        gameScreen = new GameScreen(this, VAULT);
        settingsScreen = new SettingsScreen(this);
        endGameScreen = new EndGameScreen(this);

        testScreen = new TestScreen(this);
        laserTestScreen = new LaserTestScreen(this);

        setScreen(menuScreen);
    }

    /** Sets the current screen. {@link Screen#hide()} is called on any old screen, and {@link Screen#show()} is called on the new
     * screen, if any.
     *
     * Saves the screen that was used before the function call.
     *
     * @param screen may be {@code null} */
    @Override
    public void setScreen (Screen screen) {
        this.screenBefore = getScreen();
        if (this.screen != null) this.screen.hide();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    private void createPossibleFilepaths() {
        nSkins = 4;
        possibleRobotSkinFilepaths = new String[nSkins];
        possibleRobotSkinFilepaths[0] = AssMan.PLAYER_BARTENDER_CLAPTRAP.fileName;
        possibleRobotSkinFilepaths[1] = AssMan.PLAYER_BUTLER_REFINED.fileName;
        possibleRobotSkinFilepaths[2] = AssMan.PLAYER_CLAPTRAP_REFINED.fileName;
        possibleRobotSkinFilepaths[3] = AssMan.PLAYER_CLAPTRAP_3000.fileName;
    }

    public void newGame() {
        dispose();
        create();
    }

    public void createSetupScreen() {
        setupScreen = new SetupScreen(this, possibleRobotSkinFilepaths);
    }

    // Create GameScreen with preset skins and flag positions
    public void createGameScreen() {
        gameScreen = new GameScreen(this, 0, null);
    }

    public void createGameScreen(int robotChoiceIndex, ArrayList<Position> flagPositions) {
        gameScreen = new GameScreen(this, robotChoiceIndex, flagPositions);
    }

    public AssMan getAssMan(){
        return assMan;
    }

    @Override
    public void dispose() {
        if(screenBefore != null) {
            screenBefore.dispose();
        }
        batch.dispose();
//        testScreen.dispose();
        menuScreen.dispose();
        assMan.dispose();
        // It might not been made yet
        if (setupScreen != null) {
            setupScreen.dispose();
        }

        if(gameScreen != null) {
            gameScreen.dispose();
        }

    }

    public Screen getScreenBefore(){
        return this.screenBefore;
    }

}
