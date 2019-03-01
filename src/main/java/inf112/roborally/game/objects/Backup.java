package inf112.roborally.game.objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.roborally.game.Main;

public class Backup extends GameObject {

    /**
     * A Backup is an object with a sprite, an x and a y value
     * it servers as a checkpoint, intended uses is when a player gets destroyed the
     * backup moves the player to its current position.
     *
     * @param x position x
     * @param y position y
     */
    public Backup(Position pos) {
        super(pos, "assets/robot/backup.png");
        makeSprite();
        updateSprite();
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
    }

    public void movePlayer(Player player) {
        player.move(getX(), getY());
    }
}
