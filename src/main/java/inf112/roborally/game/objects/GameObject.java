package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class GameObject{
    protected String filePath;
    protected float x;
    protected float y;
    protected Sprite sprite;

    /**
     *
     * Objects on the board that needs to be drawn, that are not on the TiledMap
     * It only has a position and needs a sprite
     *
     * Constructer doesnt create a sprite for easier use of tests. Tests get Texture nullpointer.
     * @param x position x
     * @param y position y
     */
    public GameObject(float x, float y, String filePath){
        this.filePath = filePath;
        this.x = x;
        this.y = y;
    }

    public void updateSprite() {
        sprite.setPosition(getX(), getY());
    }

    public void makeSprite(){
        sprite = new Sprite(new Texture(filePath));
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void move(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void moveX(float x){
       this.x = x;
    }

    public void moveY(float y){
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
