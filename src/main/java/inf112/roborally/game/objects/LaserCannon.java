package inf112.roborally.game.objects;

import inf112.roborally.game.board.Board;

public class LaserCannon extends MovableGameObject {
    private Player gunOwner;

    public LaserCannon(Player player) {
        super(player.getX(), player.getY(), "");
        this.gunOwner = player;
    }

    public void fire(Board board) {
        correctPositionBeforeFire();
        while (this.canGo(this.getDirection(), board.getWallLayer())) {
            this.moveInDirection(this.getDirection());
            for (Player target : board.getPlayers()) {
                if (this.position.equals(target.position)) {
                    target.takeDamage();
                    System.out.println(gunOwner.getName() + " shoots " + target.getName());
                    return;
                }
            }
            if (outOfBounds(board)) {
                return;
            }
        }
    }

    private void correctPositionBeforeFire() {
        setDirection(gunOwner.getDirection());
        move(gunOwner.getX(), gunOwner.getY());
    }
}
