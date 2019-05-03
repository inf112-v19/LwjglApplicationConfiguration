package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import inf112.roborally.game.Main;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.animations.Animation;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.board.GameLogic;
import inf112.roborally.game.board.MultiplayerLogic;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.gui.Background;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;


public class GameScreen implements Screen {

    private final RoboRallyGame game;
    private final Hud hud;
    private final GameLogic gameLogic;
    private final MultiplayerLogic multiplayerLogic;
    private final Board board;
    public ArrayList<Animation> animations;
    private Background background;
    private Music music;


    public GameScreen(RoboRallyGame game) {
        this.game = game;
        board = game.getBoard();
        board.placePlayers();
        board.setThisPlayer();
        System.out.println("PLAYER: " + board.getThisPlayer());
//        hud = new Hud(board.getPlayers().get(0), game);
        hud = new Hud(board.getThisPlayer(), game);
        hud.createButtons();
        System.out.println(game.fixedCamera.position);

        if (game.multiPlayer) {
            multiplayerLogic = new MultiplayerLogic(board, hud, game);
            gameLogic = null;
        }
        else {
            gameLogic = new GameLogic(board, hud, game);
            multiplayerLogic = null;
        }

        // Music
        music = AssMan.manager.get(AssMan.MUSIC_MAIN_THEME);
        if (!game.soundMuted) {
            music.play();
        }

        // Move dynamicCamera to center of board:
        int x = board.getWidth() / 2 * Main.PIXELS_PER_TILE;
        int y = board.getHeight() / 2 * Main.PIXELS_PER_TILE;
        game.dynamicCamera.position.set(x, y, 0);
        game.dynamicCamera.zoom = 0.4f;
        game.dynamicCamera.update();
        background = new Background(game.dynamicCamera);
        animations = new ArrayList<>();
        hud.addPlayerStatusDisplay(board.getPlayers());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud.stage);

    }

    @Override
    public void render(float delta) {
        update();
        handleInput();
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

        if (gameLogic != null) {
            gameLogic.removeDeadRobots();
        }
        game.batch.begin();
        board.drawGameObjects(game.batch, animations);
        game.batch.end();

        game.batch.setProjectionMatrix(game.fixedCamera.combined);
        hud.draw();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.settingsScreen);
        }
        // if we are playing a normal game, return here..
        if (!game.testing) return;


        boolean updatePlayer = true;
        Player player1 = board.getThisPlayer();
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player1.move(1);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player1.rotate(Rotate.LEFT);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player1.rotate(Rotate.RIGHT);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player1.reverse();
        }
        else {
            updatePlayer = false;
        }
        if (updatePlayer) {
            board.boardMoves();
            gameLogic.updatePlayers();
        }
    }

    private void update() {
        game.cameraListener.updateZoom();
        background.update(game.dynamicCamera);
        if (gameLogic != null) {
            gameLogic.update();
        }
        if (multiplayerLogic != null) {
            multiplayerLogic.update();
        }
    }


    @Override
    public void dispose() {
        System.out.println("Disposing GameScreen");
        background.dispose();

        board.dispose();
        for (Player player : board.getPlayers()) {
            player.getSprite().getTexture().dispose();
            player.getBackup().getSprite().getTexture().dispose();
        }

        for (Animation animation : animations) {
            animation.dispose();
        }
        music.dispose();
        hud.dispose();
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

    public boolean playMusic(boolean bool) {
        if (bool) {
            music.play();
            game.soundMuted = false;
        }
        else {
            music.pause();
            game.soundMuted = true;
        }
        return !bool;
    }

    public Music getMusic() {
        return music;
    }

    public Board getBoard() {
        return board;
    }

    public Hud getHud() {
        return hud;
    }

    public MultiplayerLogic getMultiplayerLogic() {
        return multiplayerLogic;
    }
}


