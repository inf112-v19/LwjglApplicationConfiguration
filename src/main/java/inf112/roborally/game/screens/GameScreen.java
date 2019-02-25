package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.GameLogic;
import inf112.roborally.game.Hud;
import inf112.roborally.game.Main;
import inf112.roborally.game.ProgramCard;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.world.Board;
import inf112.roborally.game.world.Direction;


public class GameScreen implements Screen { //TODO: Should GameScreen implement ApplicationListener? Extends Game?

    public static String mapPath = Main.TEST_MAP;

    private GameLogic gameLogic;

    private OrthographicCamera camera;
    private Viewport gamePort;
    private Hud hud;
    private Board board;

    private SpriteBatch batch;

    public GameScreen(String mapPath){
        this.mapPath = mapPath;
        this.board = new Board(Main.VAULT);

        batch = new SpriteBatch();
        hud = new Hud(batch, board.getPlayers());

        this.gameLogic = new GameLogic(board, hud);

    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.position.set(Main.GAME_WIDTH/2,Main.GAME_HEIGHT/2,0);
        camera.update();
        gamePort = new FitViewport(Main.GAME_WIDTH, Main.GAME_HEIGHT, camera);
    }

    @Override
    public void render(float delta) {
        update();
        float r = 158/255f;
        float g = 158/255f;
        float b = 158/255f;

        //The function glClearColor takes in values between 0 and 1. It creates the background color.
        Gdx.gl.glClearColor(r,g,b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        board.render(camera);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        board.drawObjects(batch);
        board.drawFlags(batch);

        batch.end();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    private void update() {
        board.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        board.dispose();
        for(Player player : board.getPlayers()) {
            player.getSprite().getTexture().dispose();
            player.getBackup().getSprite().getTexture().dispose();
        }
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
        gamePort.update(width,height);
    }
}


