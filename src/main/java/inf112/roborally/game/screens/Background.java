package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    Sprite grid;
    Sprite background;
    SpriteBatch backgroundBatch;
    float x, y;

    public Background() {
        grid = new Sprite(new Texture("assets/img/grid2.png"));
        background = new Sprite(new Texture("assets/img/background2.png"));
        grid.setSize(Gdx.graphics.getWidth() * 1.4f, Gdx.graphics.getHeight() * 1.4f);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundBatch = new SpriteBatch();
        x = -grid.getWidth() / 10;
        y = -grid.getHeight() / 10;
    }


    public void draw() {
        backgroundBatch.begin();
        background.draw(backgroundBatch);
        grid.draw(backgroundBatch);
        backgroundBatch.end();
    }

    public void dispose() {
        backgroundBatch.dispose();
        background.getTexture().dispose();
        grid.getTexture().dispose();
    }

    public void update(Camera camera) {
        grid.setPosition(x - camera.position.x / 3, y - camera.position.y / 3);
    }
}
