<<<<<<< HEAD
package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

    private RoboRallyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public GameScreen(RoboRallyGame game){
        this.game = game;
        camera = new OrthographicCamera();

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

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //All rendering code goes here
        batch.draw(Assets.spriteBackground, 0, 0);
        batch.draw(Assets.robotSprite1, 0,0); //TODO: Transparency/smaller picture?
        batch.draw(Assets.robotSprite2, 450,0); //TODO: Make rotation work
        batch.end();
    }

    public void generalUpdate(){

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
    }

    @Override
    public void resize(int width, int height) {

    }
}
=======
package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

    private RoboRallyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public GameScreen(RoboRallyGame game){
        this.game = game;
        camera = new OrthographicCamera();

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

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //All rendering code goes here
        batch.draw(Assets.spriteBackground, 0, 0);
        batch.draw(Assets.robotTexture1, 0,0); //TODO: Transparency/smaller picture?
        batch.draw(Assets.robotTexture2, 450, 0); //TODO: Make rotation work
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

    }

    @Override
    public void resize(int width, int height) {

    }
}
>>>>>>> 86cd0dd78e6a0d8fd3393d2e3dfcf57c5be97f20
