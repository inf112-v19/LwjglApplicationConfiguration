package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class GameObject{
    protected float x;
    protected float y;

    /**
     * Objects on the board that needs to be drawn, that are not on the TiledMap
     * It only has a position and needs a sprite
     * @param x position x
     * @param y position y
     */
    public GameObject(float x, float y){
        this.x = x;
        this.y = y;
    }

    public abstract void updateSprite();

    public abstract Sprite getSprite();

    public void move(float x, float y){
        this.x = x;
        this.y = y;
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
