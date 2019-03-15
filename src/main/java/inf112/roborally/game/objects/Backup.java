package inf112.roborally.game.objects;

import inf112.roborally.game.Main;

public class Backup extends GameObject {

    private final Player player;

    /**
     * A Backup is an object with a sprite, an x and a y value
     * it servers as a checkpoint, intended uses is when a player gets destroyed the
     * backup moves the player to its current position.
     *
     * @param x position x
     * @param y position y
     */
    public Backup(int x, int y, Player player) {
        super(x, y, "assets/objects/backup.png");
        this.player = player;
        makeSprite();
        updateSprite();
        sprite.setSize(Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
    }

    public void movePlayer() {
        player.move(getX(), getY());
    }

    public void moveToPlayerPosition(){
        move(player.getX(), player.getY());
    }
}
