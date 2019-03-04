package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.GameLogic;
import inf112.roborally.game.board.VaultBoard;
import inf112.roborally.game.Main;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.board.Board;


public class GameScreen implements Screen {

    public static String mapPath = Main.TEST_MAP;
    private final RoboRallyGame game;
    private final Hud hud;
    private final GameLogic gameLogic;

    private final Board board;
    private final Player player;
    private Music music;

    Sprite background;
    SpriteBatch backgroundBatch;

    public GameScreen(RoboRallyGame game, String mapPath) {
        this.mapPath = mapPath;
        this.game = game;
        this.mapPath = mapPath;
        board = new VaultBoard();
        hud = new Hud(board.getPlayers().get(0));
        gameLogic = new GameLogic(board, hud.getCardsInHandDisplay());
        player = board.getPlayers().get(0);

        // Music
        music = Gdx.audio.newMusic(Gdx.files.internal("assets/music/testMusic1.ogg"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        background = new Sprite(new Texture("assets/img/background.png"));
        background.setSize(background.getWidth()/Main.UNIT_SCALE, background.getHeight()/Main.UNIT_SCALE);
        backgroundBatch = new SpriteBatch();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        update();
        float r = 10 / 255f;
        float g = 10 / 255f;
        float b = 10 / 255f;

        //The function glClearColor takes in values between 0 and 1. It creates the background color.
        Gdx.gl.glClearColor(r, g, b, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        backgroundBatch.begin();
        background.draw(backgroundBatch);
        backgroundBatch.end();

        game.camera.position.x = player.getSprite().getX() + player.getSprite().getWidth() / 2;
        game.camera.position.y = player.getSprite().getY() + player.getSprite().getHeight() / 2 - 32 * Main.UNIT_SCALE;
        game.camera.update();

        board.render(game.camera);

        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        board.drawBackup(game.batch);
        board.drawPlayers(game.batch);
        board.drawGameObjects(game.batch);
        game.batch.end();

        hud.draw();

    }

    private void update() {
        board.update();
        gameLogic.update();
    }

    @Override
    public void dispose() {
        backgroundBatch.dispose();
        background.getTexture().dispose();

        game.batch.dispose();
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
        game.viewPort.update(width, height);
        hud.resize(width, height);
    }

    public Hud getHud() {
        return hud;
    }
}


