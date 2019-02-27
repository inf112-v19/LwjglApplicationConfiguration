package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.GameLogic;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.Main;
import inf112.roborally.game.gui.RegisterVisuals;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.world.Board;


public class GameScreen implements Screen { //TODO: Should GameScreen implement ApplicationListener? Extends Game?

    public static String mapPath = Main.TEST_MAP;

    private GameLogic gameLogic;

    private OrthographicCamera camera;
    private Viewport gamePort;
    private Hud hud;
    private Board board;

    RegisterVisuals registerVisuals;
    Player player;

    private SpriteBatch batch;

    public GameScreen(String mapPath){
        this.mapPath = mapPath;
        this.board = new Board(Main.VAULT);

        batch = new SpriteBatch();
        hud = new Hud(batch, board.getPlayers().get(0));


        this.gameLogic = new GameLogic(board, hud);
        registerVisuals = new RegisterVisuals(board.getPlayers().get(0));
        player = board.getPlayers().get(0);
    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.update();
        gamePort = new FitViewport(1920, 1080, camera);
    }

    @Override
    public void render(float delta) {
        update();
        float r = 0/255f;
        float g = 0/255f;
        float b = 0/255f;

        //The function glClearColor takes in values between 0 and 1. It creates the background color.
        Gdx.gl.glClearColor(r,g,b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.x = player.getSprite().getX() + player.getSprite().getWidth()/2;
        camera.position.y = player.getSprite().getY() + player.getSprite().getHeight()/2;
        camera.update();

        board.render(camera);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        board.drawPlayers(batch);
        board.drawGameObjects(batch);
        registerVisuals.draw(batch, camera, gamePort);

        batch.end();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    private void update() {
        board.update();
        gameLogic.update();
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


