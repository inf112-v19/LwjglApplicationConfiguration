package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;


public class GameScreen implements Screen { //TODO: Should GameScreen implement ApplicationListener? Extends Game? Something else?

    private RoboRallyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private ProgramCard programCards;
    private ArrayList<ProgramCard> listOfAllProgramCards;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;



    public GameScreen(RoboRallyGame game){
        this.game = game;
        camera = new OrthographicCamera();
        player = new Player("Player1", 0, 0);
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

        //Set to true if you want to have an inverted x y axis with 0 at the top left.
        camera.setToOrtho(true, 4000, 2200);
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


        camera.update();

        //Just for testing.
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyJustPressed(Input.Keys.D))){
            player.setX(player.getX()+150);
            player.loadVisualRepresentation();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || (Gdx.input.isKeyJustPressed(Input.Keys.A))){
            player.setX(player.getX()-150);
            player.loadVisualRepresentation();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isKeyJustPressed(Input.Keys.W))){
            player.setY(player.getY()-150);
            player.loadVisualRepresentation();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || (Gdx.input.isKeyJustPressed(Input.Keys.S))){
            player.setY(player.getY()+150);
            player.loadVisualRepresentation();
        }


        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //All rendering code goes here
        backgroundSprite.draw(batch);
        player.getSprite().draw(batch);
        batch.end();

    }


    @Override
    public void show() {

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
        player.getTexture().dispose();
        backgroundTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

}


