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

public class SettingsScreen implements Screen {

    private RoboRallyGame roboRallyGame;
    private SpriteBatch batch;
    private Sprite background;
    private Viewport viewport;

    public SettingsScreen(RoboRallyGame roborallygame) {
        this.roboRallyGame = roborallygame;
        batch = new SpriteBatch();
        viewport = new FitViewport(1920, 1080);

        background = new Sprite(new Texture("assets/img/background2.png"));
        background.setPosition(Gdx.graphics.getWidth() / 2 - background.getWidth() / 2,
                Gdx.graphics.getHeight() - background.getHeight());
        

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
        batch.begin();
        background.draw(batch);
        batch.end();

        handleInput();

    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            System.out.println("KEY M IS PRESSED");
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            System.out.println("KEY G IS PRESSED, EXITING");
            Gdx.app.exit();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            System.out.println("Key B is pressed, going back to the GameScreen");
            roboRallyGame.setScreen(roboRallyGame.gameScreen);
            dispose();
        }
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
//        background.getTexture().dispose();
    }
}
