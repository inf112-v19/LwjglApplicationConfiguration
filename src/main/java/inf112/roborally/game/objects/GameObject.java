package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.roborally.game.Main;

public abstract class GameObject extends Position {
    protected String filePath;
    protected Sprite sprite;

    /**
     * Objects on the board that needs to be drawn, that are not on the TiledMap
     * It only has a position and needs a sprite
     * <p>
     * Constructor doesn't create a sprite for easier testing. Tests get Texture null pointer.
     * @param x position x
     * @param y position y
     */
    public GameObject(int x, int y, String filePath) {
        super(x, y);
        this.filePath = filePath;
    }


    public void updateSprite() {
        sprite.setPosition(getX() * Main.TILE_LENGTH, getY() * Main.TILE_LENGTH);
    }

    public void makeSprite() {
        sprite = new Sprite(new Texture(filePath));
    }

    public Sprite getSprite() {
        return sprite;
    }

}
