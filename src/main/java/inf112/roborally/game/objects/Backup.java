package inf112.roborally.game.objects;

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
    public Backup(int x, int y) {
        super(x, y, "assets/robot/backup.png");
        makeSprite();
        updateSprite();
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
    }

    public void movePlayer(Player player) {
        player.move(getX(), getY());
    }
}
