package inf112.roborally.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.screens.setup.SelectMapScreen;
import inf112.roborally.game.screens.setup.SelectSkinScreen;
import inf112.roborally.game.screens.setup.SetupScreen;
import inf112.roborally.game.server.ChatClient;
import inf112.roborally.game.server.ChatServer;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.gui.CameraListener;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.screens.*;
import sun.awt.windows.ThemeReader;

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
//    public SetupScreen setupScreen;
    public SelectSkinScreen selectSkinScreen;
    public SelectMapScreen selectMapScreen;
    public GameScreen gameScreen;
    public SettingsScreen settingsScreen;
    public EndGameScreen endGameScreen;

    public TestScreen testScreen;
    public LaserTestScreen laserTestScreen;

    private boolean runTestMap = false;

    public static boolean soundMuted;

    /** The screen that was active before setting a new screen with {@link #setScreen(Screen)} */
    private Screen screenBefore;
    public ChatServer server;
    public ChatClient client;

    public ArrayList<String> players;
    public Board board;

    @Override
    public void create() {
        players = new ArrayList<>();
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

        menuScreen = new MenuScreen(this);
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

    public void newGame() {
        dispose();
        create();
    }

//    public void createSetupScreen() {
//        setupScreen = new SetupScreen(this, possibleRobotSkinFilepaths);
//    }

    // Create GameScreen with preset skins, map and flag positions
    public void createGameScreen() {
        gameScreen = new GameScreen(this, 0, null, null , runTestMap);
    }

    public void createGameScreen(int robotChoiceIndex, ArrayList<Position> flagPositions, int mapChoiceIndex) {
        gameScreen = new GameScreen(this, robotChoiceIndex, flagPositions, chosenMap(mapChoiceIndex), runTestMap);
    }

    @Override
    public void dispose() {
        System.out.println("Disposing RoboRallyGame");
        if(screenBefore != null) {
            screenBefore.dispose();
        }
        batch.dispose();
        testScreen.dispose();
        menuScreen.dispose();
        AssMan.dispose();

//        if (setupScreen != null) {
//            setupScreen.dispose();
//        }

        if(gameScreen != null) {
            gameScreen.dispose();
        }
    }

    public void setLaunchTestMap(boolean bool){
        runTestMap = bool;
    }

    public String chosenMap(int mapIndex) {
        String[] mapChoices = new String[4];
        mapChoices[0] = VAULT;
        mapChoices[1] = SPACE_BUG;
        mapChoices[2] = SPACE_BUG2;
        mapChoices[3] = AROUND_THE_WORLD;

        return mapChoices[mapIndex];
    }


    public void joinGame(String ip){
        try {
            client = new ChatClient(ip, 8000);
            new Thread(client).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Screen getScreenBefore(){
        return this.screenBefore;
    }

    public Board getBoard() {
        return board;
    }

    public void startServer() {
        server = new ChatServer(8000, this);
        new Thread(server).start();
    }
}
