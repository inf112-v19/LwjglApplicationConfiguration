package inf112.roborally.game.objects;

import inf112.roborally.game.Main;
import inf112.roborally.game.gui.AssMan;

public class Backup extends GameObject {

    private final Player player;

    /**
     * A BACKUP is an object with a sprite, an x and a y value
     * it servers as a checkpoint, intended uses is when a player gets destroyed the
     * backup moves the player to its current position.
     *
     * @param x position x
     * @param y position y
     */
    public Backup(Player player) {
        super(player.getX(), player.getY(), AssMan.BACKUP.fileName);
        this.player = player;
    }


    /**
     * Need to call this function to initialize the sprite.
     * Removed from constructor to make tests work.
     */
    public void setupSprite(){
        makeSprite();
        updateSprite();
        sprite.setSize(Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
    }

    public void movePlayerToBackup() {
        player.moveToPosition(this.position);
    }

    public void moveToPlayerPosition(){
        moveToPosition(player.position);
        updateSprite();
    }
}
