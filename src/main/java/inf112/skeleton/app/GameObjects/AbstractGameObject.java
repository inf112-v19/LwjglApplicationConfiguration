package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Abstract class for objects to be drawn on the gameboard (which is not
 *  included in the TileMap), e.g Flags and "Savepoints"
 */

public abstract class AbstractGameObject {
    private int x;
    private int y;
    private String filename;

    private Texture texture;
    private Sprite sprite;

    public AbstractGameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void loadVisualRepresentation(String filename) {
        texture = new Texture(Gdx.files.internal(filename));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.setPosition(this.x, this.y-10);
        sprite.setOriginCenter();
    }

    public Sprite getSprite() {
        return sprite;
    }

}
