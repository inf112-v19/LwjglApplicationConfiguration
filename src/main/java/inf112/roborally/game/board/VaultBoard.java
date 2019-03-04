package inf112.roborally.game.board;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.RepairSite;

public class VaultBoard extends Board {

    public VaultBoard() {
        flags.add(new Flag(1, 10, 1));
        flags.add(new Flag(6, 2, 2));
        flags.add(new Flag(6, 10, 3));

        repairSites.add(new RepairSite(5, 2));

        createdBoard(RoboRallyGame.VAULT);

        Player player1 = new Player("Player1", 6, 6, Direction.NORTH, flags.size);

        Player player2 = new Player("Player2", 5, 7, Direction.SOUTH, flags.size);

        players.add(player1);
        players.add(player2);
    }

}
