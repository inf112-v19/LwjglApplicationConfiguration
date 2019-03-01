package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.GameLogic;
import inf112.roborally.game.gui.CardsInHandDisplay;
import inf112.roborally.game.Main;
import inf112.roborally.game.gui.ProgramRegisterDisplay;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.world.Board;


public class GameScreen implements Screen { //TODO: Should GameScreen implement ApplicationListener? Extends Game?

    public static String mapPath = Main.TEST_MAP;

    private GameLogic gameLogic;

    public OrthographicCamera camera;
    private Viewport   viewPort;
    private CardsInHandDisplay cardsInHandDisplay;
    private Board board;

    ProgramRegisterDisplay programRegisterDisplay;
    Player player;

    private SpriteBatch batch;

    public GameScreen(String mapPath){
        this.mapPath = mapPath;
        this.board = new Board(mapPath);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.update();
        viewPort = new FitViewport(1920, 1080, camera);
        batch = new SpriteBatch();
        Stage stage = new Stage(viewPort, batch);
        cardsInHandDisplay = new CardsInHandDisplay(board.getPlayers().get(0), stage);
        this.gameLogic = new GameLogic(board, cardsInHandDisplay);

        programRegisterDisplay = new ProgramRegisterDisplay(board.getPlayers().get(0));
        player = board.getPlayers().get(0);
    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta) {
        update();
        float r = 30/255f;
        float g = 30/255f;
        float b = 30/255f;

        //The function glClearColor takes in values between 0 and 1. It creates the background color.
        Gdx.gl.glClearColor(r,g,b, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.x = player.getSprite().getX() + player.getSprite().getWidth()/2;
        camera.position.y = player.getSprite().getY() + player.getSprite().getHeight()/2 - 32*Main.UNIT_SCALE;
        camera.update();

        board.render(camera);


        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        board.drawBackup(batch);
        board.drawPlayers(batch);
        board.drawGameObjects(batch);
        programRegisterDisplay.draw(batch, camera);
        batch.end();

        batch.setProjectionMatrix(cardsInHandDisplay.stage.getCamera().combined);
        cardsInHandDisplay.stage.draw();


    }

    private void update() {
        board.update();
        gameLogic.update();
        cardsInHandDisplay.update(player);
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
        viewPort.update(width,height);
    }
}


