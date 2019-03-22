package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import inf112.roborally.game.Main;
import inf112.roborally.game.enums.Direction;

public abstract class GameObject {
    protected String filePath;
    protected Sprite sprite;

    public Position position;

    /**
     * Objects on the board that needs to be drawn, that are not on the TiledMap
     * It only has a position and needs a sprite
     * <p>
     * Constructor doesn't create a sprite for easier testing. Tests get Texture null pointer.
     *
     * @param x position x
     * @param y position y
     */
    public GameObject(int x, int y, String filePath) {
        this.filePath = filePath;
        position = new Position(x, y);
    }

    public GameObject(Position position, String filePath) {
        this.filePath = filePath;
        this.position = position;
    }


    public void updateSprite() {
        sprite.setPosition(position.getX() * Main.PIXELS_PER_TILE, position.getY() * Main.PIXELS_PER_TILE);
    }

    public void makeSprite() {
        sprite = new Sprite(new Texture(filePath));
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }


    public void move(int x, int y) {
        position.move(x, y);
    }

    public void moveToPosition(Position position){
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

    public Vector3 getSpritePosition() {
        return new Vector3(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }

}
