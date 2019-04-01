package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import inf112.roborally.game.Main;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.animations.Animation;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.board.GameLogic;
import inf112.roborally.game.board.TestBoard;
import inf112.roborally.game.board.VaultBoard;
import inf112.roborally.game.gui.Background;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.sound.GameMusic;

import java.util.ArrayList;

import static inf112.roborally.game.enums.Direction.NORTH;


public class GameScreen implements Screen {

    private final RoboRallyGame game;
    private final Hud hud;
    private final GameLogic gameLogic;
    private final Board board;
    private Background background;
    private GameMusic music;
    private boolean testMap = false;

    public ArrayList<Animation> animations;


    public GameScreen(RoboRallyGame game, int robotChoiceIndex, ArrayList<Position> flagPositions) {
        this.game = game;
        if (!testMap) {
            if (flagPositions != null) {
                board = new VaultBoard(flagPositions);
            }
            else {
                board = new VaultBoard();
            }
        }
        else {
            board = new TestBoard();
        }
        addPlayersToBoard(robotChoiceIndex);
        board.placePlayers();
        hud = new Hud(board.getPlayers().get(0), game);
        hud.createButtons();
        System.out.println(game.fixedCamera.position);
        gameLogic = new GameLogic(board, hud, game);
        new Thread(gameLogic).start();
        // Music
        music = new GameMusic(RoboRallyGame.MAIN_THEME);
        music.play();

        // Move dynamicCamera to center of board:
        int x = board.getWidth() / 2 * Main.PIXELS_PER_TILE;
        int y = board.getHeight() / 2 * Main.PIXELS_PER_TILE;
        game.dynamicCamera.position.set(x, y, 0);
        game.dynamicCamera.zoom = 0.4f;
        game.dynamicCamera.update();
        background = new Background(game.dynamicCamera);
        animations = new ArrayList<>();
    }

    private void addPlayersToBoard(int robotChoiceIndex) {
        String[] filepaths = game.possibleRobotSkinFilepaths;
        StringBuilder namebuilder = new StringBuilder();
        namebuilder.append("Player");
        int index = robotChoiceIndex;
        int n = game.nSkins;
        for (int i = 0; i < n; i++) {
            if (index >= n) {
                index = 0;
            }
            namebuilder.append(Integer.toString(i + 1));
            board.addPlayer(new Player(namebuilder.toString(), filepaths[index], NORTH, board));
            namebuilder.deleteCharAt(6); // Delete the last character, which is the player number
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
                animations.remove(i--); // need to decrement i when removing an element?
        }
        board.renderLayer(board.getWallLayer());
        game.batch.end();

        game.batch.setProjectionMatrix(game.fixedCamera.combined);
        hud.draw();
    }

    private void update() {
        game.cameraListener.updateZoom();
        background.update(game.dynamicCamera);
        gameLogic.handleInput();
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

    public GameMusic getMusic() {
        return music;
    }

    public Board getBoard() {
        return board;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }
}


