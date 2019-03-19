package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    Sprite clouds;
    Sprite grid;
    Sprite background;
    SpriteBatch batch;
    float originalCameraX, originalCameraY;
    float gridX, gridY;
    float cloudX, cloudY;
    float cloudW, cloudH;

    public Background(Camera camera) {
        originalCameraX = camera.position.x;
        originalCameraY = camera.position.y;

        batch = new SpriteBatch();

        background = new Sprite(new Texture("assets/img/background2.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        grid = new Sprite(new Texture("assets/img/grid2.png"));
        grid.setSize(Gdx.graphics.getWidth() * 1.4f, Gdx.graphics.getHeight() * 1.4f);
        gridX = -grid.getWidth() / 10;
        gridY = -grid.getHeight() / 10;

        clouds = new Sprite(new Texture("assets/img/clouds.png"));
        clouds.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        clouds.setOriginCenter();
        cloudW = clouds.getWidth() / 1.5f;
        cloudH = clouds.getHeight() / 1.5f;
        cloudX = 0;
        cloudY = 0;
    }

    public void update(OrthographicCamera camera) {
        grid.setPosition(gridX - camera.position.x / 3, gridY - camera.position.y / 3);
        clouds.setSize(cloudW / (camera.zoom), cloudH / (camera.zoom));
        clouds.setOriginCenter();
        clouds.setOriginBasedPosition(Gdx.graphics.getWidth() / 2 + (originalCameraX - camera.position.x),
                Gdx.graphics.getHeight() / 2 + (originalCameraY - camera.position.y));
    }

    public void draw() {
        this.batch.begin();
        background.draw(this.batch);
        grid.draw(this.batch);
        clouds.draw(batch);
        batch.end();
    }

    public void dispose() {
        background.getTexture().dispose();
        grid.getTexture().dispose();
    }


}
