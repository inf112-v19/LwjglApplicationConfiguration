package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import inf112.roborally.game.Main;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.animations.Animation;
import inf112.roborally.game.board.*;
import inf112.roborally.game.gui.Background;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;

import static inf112.roborally.game.enums.Direction.NORTH;


public class GameScreen implements Screen {

    private final RoboRallyGame game;
    private final Hud hud;
    private final GameLogic gameLogic;
    private final Board board;
    public ArrayList<Animation> animations;
    private Background background;
    private Music music;


    public GameScreen(RoboRallyGame game, int robotChoiceIndex, ArrayList<Position> flagPositions, String mapFilePath, boolean runTestMap) {
        this.game = game;
        if (!runTestMap) {
            if (flagPositions != null) {
                board = new BoardCreator(mapFilePath, flagPositions);
            } else {
                board = new VaultBoard();
            }
        } else {
            board = new TestBoard();
        }
        addPlayersToBoard(robotChoiceIndex);
        board.placePlayers();
        hud = new Hud(board.getPlayers().get(0), game);
        hud.createButtons();
        System.out.println(game.fixedCamera.position);
        gameLogic = new GameLogic(board, hud, game);

        // Music
        music = AssMan.manager.get(AssMan.MUSIC_MAIN_THEME);
        if(!RoboRallyGame.soundMuted) {
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

    private void addPlayersToBoard(int robotChoiceIndex) {
        int index = robotChoiceIndex;
        int n = AssMan.getPlayerSkins().length;
        for (int i = 0; i < n; i++) {
            if (index >= n) {
                index = 0;
            }
            board.addPlayer(new Player("Player" + (i + 1), AssMan.getPlayerSkins()[index], NORTH, board));

            index++;
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud.stage);

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

        gameLogic.removeDeadRobots();
        game.batch.begin();
        board.drawGameObjects(game.batch);
        for (int i = 0; i < animations.size(); i++) {
            animations.get(i).draw(game.batch);
            if (animations.get(i).hasFinished())
                animations.remove(i--);
        }
        game.batch.end();

        game.batch.setProjectionMatrix(game.fixedCamera.combined);
        hud.draw();
    }

    private void update() {
        game.cameraListener.updateZoom();
        background.update(game.dynamicCamera);
        gameLogic.handleInput();
        gameLogic.update();
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

        for(Animation animation : animations){
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

    public boolean playMusic(boolean bool){
        if(bool) {
            music.play();
            RoboRallyGame.soundMuted = false;
        }else{
            music.pause();
            RoboRallyGame.soundMuted = true;
        }
        return !bool;
    }

    public Music getMusic() {
        return music;
    }

    public Board getBoard() {
        return board;
    }
}


