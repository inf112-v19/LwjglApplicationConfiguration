package inf112.skeleton.app.GameObjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.Main;

public class Backup extends GameObject {

    private Sprite sprite;

    /**
     * A Backup is an object with a sprite, an x and a y value
     * it servers as a checkpoint, intended uses is when a player gets destroyed the
     * backup moves the player to its current position.
     * @param x position x
     * @param y position y
     */
    public Backup(int x, int y){
        super(x, y);
        sprite = new Sprite(new Texture("assets/robot/backup.png"));
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
        updateSprite();
    }

    public void movePlayer(Player player){
        player.move(getX(), getY());
    }

    public void updateSprite(){
        sprite.setPosition(getX(), getY());
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }
}
