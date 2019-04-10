package inf112.roborally.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.screens.setup.SelectMapScreen;
import inf112.roborally.game.screens.setup.SelectSkinScreen;
import inf112.roborally.game.screens.setup.SetupScreen;
import inf112.roborally.game.tools.AssMan;
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
    public static final String SPACE_BUG = "assets/maps/space_bug.tmx";
    public static final String SPACE_BUG2 = "assets/maps/space_bug2.tmx";
    public static final String AROUND_THE_WORLD = "assets/maps/around_the_world.tmx";

    //Music:
    public static final String MAIN_THEME = AssMan.MUSIC_MAIN_THEME.fileName;
    public static final String TEST_MUSIC = "assets/music/testMusic1.ogg";

    public static final int MAX_PLAYERS = 8;

    public boolean AIvsAI = false;

    public OrthographicCamera dynamicCamera;
    public Viewport dynamicViewPort;
    public CameraListener cameraListener;

    public OrthographicCamera fixedCamera; //the position of this camera should never change!
    public FitViewport fixedViewPort;

    public SpriteBatch batch;

    public MenuScreen menuScreen;
    public SetupScreen setupScreen;
    public SelectSkinScreen selectSkinScreen;
    public SelectMapScreen selectMapScreen;
    public GameScreen gameScreen;
    public SettingsScreen settingsScreen;
    public EndGameScreen endGameScreen;

    public TestScreen testScreen;
    public LaserTestScreen laserTestScreen;

    private boolean runTestMap = false;


    /** The screen that was active before setting a new screen with {@link #setScreen(Screen)} */
    private Screen screenBefore;


    public int nSkins;
    public String[] possibleRobotSkinFilepaths;

    @Override
    public void create() {
        AssMan.load();
        AssMan.manager.finishLoading();
        AIvsAI = false;

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
        selectSkinScreen = new SelectSkinScreen(this);
        selectMapScreen = new SelectMapScreen(this);

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
        nSkins = 8;
        possibleRobotSkinFilepaths = new String[nSkins];
        possibleRobotSkinFilepaths[0] = AssMan.PLAYER_BARTENDER_CLAPTRAP.fileName;
        possibleRobotSkinFilepaths[1] = AssMan.PLAYER_BUTLER_REFINED.fileName;
        possibleRobotSkinFilepaths[2] = AssMan.PLAYER_CLAPTRAP_REFINED.fileName;
        possibleRobotSkinFilepaths[3] = AssMan.PLAYER_CLAPTRAP_3000.fileName;
        possibleRobotSkinFilepaths[4] = AssMan.PLAYER_NURSE_BOT.fileName;
        possibleRobotSkinFilepaths[5] = AssMan.PLAYER_CAPTAIN_BOT.fileName;
        possibleRobotSkinFilepaths[6] = AssMan.PLAYER_COP_BOT.fileName;
        possibleRobotSkinFilepaths[7] = AssMan.PLAYER_WIZZARD_BOT.fileName;
    }

    public void newGame() {
        dispose();
        create();
    }

    public void createSetupScreen() {
        setupScreen = new SetupScreen(this, possibleRobotSkinFilepaths);
    }

    // Create GameScreen with preset skins, map and flag positions
    public void createGameScreen() {
        gameScreen = new GameScreen(this, 0, new ArrayList<Position>(), null , runTestMap);
    }

    public void createGameScreen(int robotChoiceIndex, ArrayList<Position> flagPositions, int mapChoiceIndex) {
        gameScreen = new GameScreen(this, robotChoiceIndex, flagPositions, chosenMap(mapChoiceIndex), runTestMap);
    }

    @Override
    public void dispose() {
        if(screenBefore != null) {
            screenBefore.dispose();
        }
        batch.dispose();
        testScreen.dispose();
        menuScreen.dispose();
        AssMan.dispose();
        // It might not been made yet
        if (setupScreen != null) {
            setupScreen.dispose();
        }

        if(gameScreen != null) {
            gameScreen.dispose();
        }

    }

    public String chosenMap(int mapIndex) {
        String[] mapChoices = new String[4];
        mapChoices[0] = VAULT;
        mapChoices[1] = SPACE_BUG;
        mapChoices[2] = SPACE_BUG2;
        mapChoices[3] = AROUND_THE_WORLD;

        return mapChoices[mapIndex];
    }

    public Screen getScreenBefore(){
        return this.screenBefore;
    }

    public void setLauncTestMap(boolean bool){
        runTestMap = bool;
    }
}
