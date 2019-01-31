package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
 * If you get an error saying "Couldn't load file: RoboRallyBoard1.png" then press "Run" -> "Edit Configurations"
 * and change Working directory to assets folder.
 */

public class Board implements ApplicationListener {
    private SpriteBatch batch;
    private Texture boardImage;
    private TextureRegion boardBackground;

    @Override
    public void create() {
        boardImage = new Texture(Gdx.files.internal("RoboRallyBoard1.png"));
        boardBackground = new TextureRegion(boardImage, 0, 0, 1800, 1800);
        batch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(boardBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
