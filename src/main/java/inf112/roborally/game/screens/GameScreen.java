package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import inf112.roborally.game.Main;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.animations.Animation;
import inf112.roborally.game.board.*;
import inf112.roborally.game.gui.Background;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.sound.GameMusic;

import java.util.ArrayList;

import static inf112.roborally.game.enums.Direction.NORTH;


public class GameScreen implements Screen {

    public static String mapPath = RoboRallyGame.TEST_MAP;
    private final RoboRallyGame game;
    private final Hud hud;
    private final GameLogic gameLogic;
    private final Board board;
    private Background background;
    private GameMusic music;

    public ArrayList<Animation> animations;

    public GameScreen(RoboRallyGame game, String mapPath) {
        this.mapPath = mapPath;
        this.game = game;

        board = new VaultBoard();

        board.addPlayer(new Player("Player1", "assets/robot/bartenderclaptrap.png", NORTH, board));
        board.addPlayer(new Player("Player2", "assets/robot/claptrapRefined.png", NORTH, board));
        board.addPlayer(new Player("Player3", "assets/robot/butlerRefined.png", NORTH, board));
        board.addPlayer(new Player("Player1", "assets/robot/claptrap3000.png", NORTH, board));
        board.placePlayers();

        hud = new Hud(board.getPlayers().get(0), game);
        System.out.println(game.fixedCamera.position);
        gameLogic = new GameLogic(board, hud.getCardsInHandDisplay(), game);

        // Music
        music = new GameMusic(RoboRallyGame.MAIN_THEME);

        // Move dynamicCamera to center of board:
        int x = board.getWidth() / 2 * Main.PIXELS_PER_TILE;
        int y = board.getHeight() / 2 * Main.PIXELS_PER_TILE;
        game.dynamicCamera.position.set(x, y, 0);
        game.dynamicCamera.zoom = 0.4f;
        game.dynamicCamera.update();

        background = new Background(game.dynamicCamera);

        animations = new ArrayList<>();
    }

    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
        update();
        float r = 10 / 255f;
        float g = 10 / 255f;
        float b = 10 / 255f;
        Gdx.gl.glClearColor(r, g, b, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(game.fixedCamera.combined);
        game.batch.begin();
        background.draw(game.batch);
        game.batch.end();

        board.render(game.dynamicCamera);

        game.batch.setProjectionMatrix(game.dynamicCamera.combined);
        game.batch.begin();
        board.drawGameObjects(game.batch);
        for (int i = 0; i < animations.size(); i++) {
            animations.get(i).draw(game.batch);
            if (animations.get(i).hasFinished())
                animations.remove(i--); // need to decrement i when removing an element?
        }
        game.batch.end();

        game.batch.setProjectionMatrix(game.fixedCamera.combined);
        hud.draw(game.batch);
    }

    private void update() {
        gameLogic.update();
        game.cameraListener.update();
        background.update(game.dynamicCamera);
    }


    @Override
    public void dispose() {
        System.out.println("disposing game screen");
        background.dispose();

        board.dispose();
        for (Player player : board.getPlayers()) {
            player.getSprite().getTexture().dispose();
            player.getBackup().getSprite().getTexture().dispose();
        }
        hud.dispose();
        music.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {
        game.dynamicViewPort.update(width, height);
        game.fixedViewPort.update(width, height);

    }

    public Hud getHud() {
        return hud;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public GameMusic getMusic() {
        return music;
    }

    public Board getBoard() {
        return board;
    }
}


