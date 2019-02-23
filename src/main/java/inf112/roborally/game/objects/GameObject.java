package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.roborally.game.Main;
import inf112.roborally.game.screens.MenuScreen;

public abstract class GameObject{
    protected String filePath;
    protected int x;
    protected int y;
    protected Sprite sprite;

    /**
     *
     * Objects on the board that needs to be drawn, that are not on the TiledMap
     * It only has a position and needs a sprite
     *
     * Constructor doesn't create a sprite for easier testing. Tests get Texture null pointer.
     * @param x position x
     * @param y position y
     */
    public GameObject(int x, int y, String filePath){
        this.filePath = filePath;
        this.x = x;
        this.y = y;
    }

    public void updateSprite() {
        sprite.setPosition(getX() *Main.TILE_LENGTH, getY() *Main.TILE_LENGTH);
    }

    public void makeSprite(){
        sprite = new Sprite(new Texture(filePath));
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void moveX(int x){
       this.x = x;
    }

    public void moveY(int y){
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
