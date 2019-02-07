package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.ArrayList;

public class GameScreen implements Screen { //TODO: Should GameScreen implement ApplicationListener? Extends Game?

    public final int UNIT_SCALE = 3;
    public final int MOVE_DIST = 32*UNIT_SCALE;
    private RoboRallyGame game;
    private OrthographicCamera camera;
    private Viewport gamePort;

    // map stuff:
    private TmxMapLoader loader;
    private TiledMap board;
    private TiledMapRenderer mapRenderer;

    private SpriteBatch batch;
    private Player player;
    private ProgramCard programCards;
    private ArrayList<ProgramCard> listOfAllProgramCards;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;


    public GameScreen(RoboRallyGame game){
        this.game = game;

        //player:
        player = new Player("Player1", 65, 35);
        programCards = new ProgramCard();
        listOfAllProgramCards = programCards.makeStack();

        //Load the background
        backgroundTexture = new Texture(Gdx.files.internal("assets/gameboard/RoboRallyBoard2.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setPosition(0, 0);

        player.loadVisualRepresentation();
        for(int i = 0; i < 9; i++){
            player.receiveNewCard(listOfAllProgramCards.remove(0));
        }

        batch = new SpriteBatch();
    }



    @Override
    public void render(float delta) {
        float r = 158/255f;
        float g = 158/255f;
        float b = 158/255f;

        //The function glClearColor takes in values between 0 and 1. It creates the background color.
        Gdx.gl.glClearColor(r,g,b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();


        //Just for testing
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyJustPressed(Input.Keys.D))){
            player.setX(player.getX()+ MOVE_DIST);
            player.loadVisualRepresentation();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || (Gdx.input.isKeyJustPressed(Input.Keys.A))){
            player.setX(player.getX()- MOVE_DIST);
            player.loadVisualRepresentation();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isKeyJustPressed(Input.Keys.W))){
            player.setY(player.getY()- MOVE_DIST);
            player.loadVisualRepresentation();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || (Gdx.input.isKeyJustPressed(Input.Keys.S))){
            player.setY(player.getY()+ MOVE_DIST);
            player.loadVisualRepresentation();
        }


        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        //assetsInner.backgroundSprite.draw(batch);
        player.getSprite().draw(batch);
        batch.end();

    }


    @Override
    public void show() {
        //map:
        loader = new TmxMapLoader();
        board = loader.load("assets/gameboard/testMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(board, UNIT_SCALE);

        //view:
        camera = new OrthographicCamera();
        camera.setToOrtho(true);
        gamePort = new FitViewport(1920, 1080, camera);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        board.dispose();
        backgroundTexture.dispose();
        player.getTexture().dispose();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

}


