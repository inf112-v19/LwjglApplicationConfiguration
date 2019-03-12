package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
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
        sprite.setPosition(getX() * Main.PIXELS_PER_TILE, getY() * Main.PIXELS_PER_TILE);
    }

    public void makeSprite() {
        sprite = new Sprite(new Texture(filePath));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector3 getSpritePosition(){
        return new Vector3(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2, 0);
    }

}
