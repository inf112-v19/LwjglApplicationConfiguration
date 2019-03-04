package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.GameLogic;
import inf112.roborally.game.board.VaultBoard;
import inf112.roborally.game.gui.CardsInHandDisplay;
import inf112.roborally.game.Main;
import inf112.roborally.game.gui.ProgramRegisterDisplay;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.board.Board;


public class GameScreen implements Screen { //TODO: Should GameScreen implement ApplicationListener? Extends Game?

    public static String mapPath = Main.TEST_MAP;
    private final RoboRallyGame game;
    private GameLogic gameLogic;
    private CardsInHandDisplay cardsInHandDisplay;
    private Board board;
    ProgramRegisterDisplay programRegisterDisplay;
    Player player;

    public GameScreen(RoboRallyGame game, String mapPath){
        this.mapPath = mapPath;
        this.game = game;
        board = new VaultBoard();
        cardsInHandDisplay = new CardsInHandDisplay(board.getPlayers().get(0), new Stage(game.viewPort, game.batch));
        gameLogic = new GameLogic(board, cardsInHandDisplay);
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
        game.camera.position.x = player.getSprite().getX() + player.getSprite().getWidth()/2;
        game.camera.position.y = player.getSprite().getY() + player.getSprite().getHeight()/2 - 32*Main.UNIT_SCALE;
        game.camera.update();

        board.render(game.camera);


        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        board.drawBackup(game.batch);
        board.drawPlayers(game.batch);
        board.drawGameObjects(game.batch);
        programRegisterDisplay.draw(game.batch, game.camera);
        game.batch.end();

        game.batch.setProjectionMatrix(cardsInHandDisplay.stage.getCamera().combined);
        cardsInHandDisplay.stage.draw();
    }

    private void update() {
        board.update();
        gameLogic.update();
    }

    @Override
    public void dispose() {
        game.batch.dispose();
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
        game.viewPort.update(width,height);
    }

    public CardsInHandDisplay getCardsInHandDisplay(){
        return cardsInHandDisplay;
    }
}


