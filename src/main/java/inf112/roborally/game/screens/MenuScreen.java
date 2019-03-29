package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.RoboRallyGame;

import java.awt.event.InputEvent;

public class MenuScreen implements Screen {

    private RoboRallyGame roboRallyGame;
    private SpriteBatch batch;
    private Sprite background;
    private Sprite pressEnter;
    private Viewport viewport;
    private int stateTimer;

    public MenuScreen(RoboRallyGame roboRallyGame) {
        this.roboRallyGame = roboRallyGame;
        batch = new SpriteBatch();
        batch.enableBlending();

        viewport = new FitViewport(1920, 1080);

        background = new Sprite(new Texture("assets/img/titlescreen.jpg"));
        background.setPosition(Gdx.graphics.getWidth() / 2 - background.getWidth() / 2,
                Gdx.graphics.getHeight() - background.getHeight());
        pressEnter = new Sprite(new Texture("assets/img/pressEnterWhite.png"));
        pressEnter.setPosition(Gdx.graphics.getWidth() / 2 - pressEnter.getWidth() / 2,
                (Gdx.graphics.getHeight() - background.getHeight()) / 2 - pressEnter.getHeight() / 2);

        stateTimer = 0;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        float r = 0 / 255f;
        float g = 20 / 255f;
        float b = 15 / 255f;

        Gdx.gl.glClearColor(r, g, b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        pressEnter.setAlpha(stateTimer / 255f);
        background.setAlpha(stateTimer / 255f);
        if (stateTimer < 255) stateTimer++;
        batch.begin();
        background.draw(batch);
        pressEnter.draw(batch);
        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
//            roboRallyGame.setScreen(roboRallyGame.gameScreen);
            roboRallyGame.createGameScreen();
            roboRallyGame.setScreen(roboRallyGame.gameScreen);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            roboRallyGame.createSetupScreen();
            roboRallyGame.setScreen(roboRallyGame.setupScreen);
            dispose();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
//        else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
//            roboRallyGame.setScreen(roboRallyGame.testScreen);
//            dispose();
//        }
    }

    @Override
    public void resize(int w, int h) {
        viewport.update(w, h);
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
        pressEnter.getTexture().dispose();
        background.getTexture().dispose();
    }
}
