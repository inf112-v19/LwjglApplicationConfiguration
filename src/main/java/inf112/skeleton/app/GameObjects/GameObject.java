package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Main;

public abstract class GameObject{
    protected Sprite sprite;

    public GameObject(float x, float y, String filePath){
        sprite = new Sprite(new Texture(filePath));
        move(x, y);
        sprite.setSize(sprite.getWidth() * Main.UNIT_SCALE, sprite.getHeight() * Main.UNIT_SCALE);
    }

    public void move(float x, float y){
        sprite.setX(x);
        sprite.setY(y);
    }

    public void moveX(float x){
        sprite.setX(x);
    }

    public void moveY(float y){
        sprite.setY(y);
    }


    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
    public void dispose(){
        sprite.getTexture().dispose();
    }

    public float getX() {
        return sprite.getX();
    }
    public float getY() {
        return sprite.getY();
    }

    public Sprite getSprite(){
        return this.sprite;
    }
}
