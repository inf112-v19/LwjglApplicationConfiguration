package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.RoboRallyGame;

public abstract class AbstractScreen implements Screen {

    protected RoboRallyGame game;
    protected SpriteBatch batch;
    protected Sprite background;

    public AbstractScreen (RoboRallyGame game, String filepath) {
        this.game = game;
//        batch = new SpriteBatch();
        batch = game.batch;
        background = new Sprite(new Texture(filepath));
        game.fixedViewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
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

        // Moved the drawing with the batch to each subclass, because its often used
        // differently

        // Moved the handleInput() call to the subclass
    }

    private void handleInput() {

    }

    @Override
    public void resize(int w, int h) {
        game.fixedViewPort.update(w, h);
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

}
