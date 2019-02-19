package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject{
    protected float x;
    protected float y;

    public GameObject(float x, float y){
        this.x = x;
        this.y = y;
    }

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

    public abstract Sprite getSprite();
}
