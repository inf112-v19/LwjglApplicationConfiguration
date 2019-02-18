package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Main;

public abstract class GameObject{
    private Sprite sprite;

    public GameObject(float x, float y, String filePath){
        sprite = new Sprite(new Texture(filePath));
        move(x, y);
        sprite.setSize(sprite.getWidth() * Main.UNIT_SCALE, sprite.getHeight() * Main.UNIT_SCALE);
    }

    public void move(float x, float y){
        sprite.setX(x);
        sprite.setY(y);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public float getX() {
        return sprite.getX();
    }
    public float getY() {
        return sprite.getY();
    }
}
