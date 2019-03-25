package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private Sprite clouds;
    private Sprite grid;
    private Sprite background;
    private float originalCameraX, originalCameraY;
    private float gridX, gridY;
    private float cloudX, cloudY;
    private float cloudW, cloudH;

    public Background(OrthographicCamera camera) {
        originalCameraX = camera.position.x;
        originalCameraY = camera.position.y;

        background = new Sprite(new Texture("assets/img/background2.png"));

        grid = new Sprite(new Texture("assets/img/grid2.png"));
        grid.setSize(background.getWidth() * 1.4f, background.getHeight() * 1.4f);
        gridX = -grid.getWidth() / 10;
        gridY = -grid.getHeight() / 10;

        clouds = new Sprite(new Texture("assets/img/clouds.png"));
        clouds.setSize(background.getWidth() * 1.4f, background.getHeight() * 1.4f);
        clouds.setOriginCenter();
        cloudX = clouds.getOriginX() - 200;
        cloudY = clouds.getOriginY() - 70;
    }

    public void update(OrthographicCamera camera) {
        grid.setPosition(gridX - camera.position.x / 3, gridY - camera.position.y / 3);
        clouds.setOriginBasedPosition(cloudX - camera.position.x / 1.5f, cloudY - camera.position.y / 1.5f);
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        grid.draw(batch);
        clouds.draw(batch);
    }

    public void dispose() {
        background.getTexture().dispose();
        grid.getTexture().dispose();
    }


}