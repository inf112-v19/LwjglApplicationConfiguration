package inf112.roborally.game.objects;

import inf112.roborally.game.board.Board;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;

public class LaserCannon extends MovableGameObject {
    private Player gunOwner;

    public LaserCannon(Player player) {
        super(player.getX(), player.getY(), "");
        this.gunOwner = player;
    }

    public boolean fire(Board board) {
        if(!gunOwner.isOperational()) return false;

        correctPositionBeforeFire();
        while (this.canGo(this.getDirection(), board.getWallLayer())) {
            this.moveInDirection(this.getDirection());
            for (Player target : board.getPlayers()) {
                if (this.position.equals(target.position)) {
                    target.takeDamage();
                    System.out.println(gunOwner.getName() + " shoots " + target.getName());
                    return true;
                }
            }
            if (outOfBounds(board)) {
                break;
            }
        }
        return false;
    }

    /**
     * For testing only! this method can fire through walls
     */
    public void fire(ArrayList<Player> targets) {
        correctPositionBeforeFire();
        while (getX() >= 0 && getX() < 10 && getY() >= 0 && getY() < 10) {
            this.moveInDirection(this.getDirection());
            for (Player target : targets) {
                if (this.position.equals(target.position)) {
                    target.takeDamage();
                    System.out.println(gunOwner.getName() + " shoots " + target.getName());
                    return;
                }
            }
        }
    }

    private void correctPositionBeforeFire() {
        setDirection(gunOwner.getDirection());
        move(gunOwner.getX(), gunOwner.getY());
    }
}
