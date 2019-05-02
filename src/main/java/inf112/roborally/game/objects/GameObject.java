package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.Main;

public abstract class GameObject {
    public Position position;
    protected Sprite sprite;

    /**
     * Objects on the board that needs to be drawn, that are not on the TiledMap
     * It only has a position and needs a sprite
     * <p>
     * Constructor doesn't create a sprite for easier testing. Tests get Texture null pointer.
     *
     * @param x position x
     * @param y position y
     */
    public GameObject(int x, int y) {
        position = new Position(x, y);
    }

    public GameObject(Position position) {
        this.position = position;
    }


    public void updateSprite() {
        sprite.setPosition(position.getX() * Main.PIXELS_PER_TILE, position.getY() * Main.PIXELS_PER_TILE);
    }


    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void move(int x, int y) {
        position.move(x, y);
    }

    public void moveToPosition(Position position) {
        this.position.move(position.getX(), position.getY());
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
