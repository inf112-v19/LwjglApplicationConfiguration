package inf112.roborally.game.board;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.RepairSite;

public class VaultBoard extends Board {

    public VaultBoard() {
        flags.add(new Flag(13, 6, 1));
        flags.add(new Flag(5, 8, 2));
        flags.add(new Flag(11, 4, 3));

        repairSites.add(new RepairSite(5, 2));

        createBoard(RoboRallyGame.VAULT);

        Player player1 = new Player("Player1", 6, 6, Direction.NORTH, flags.size());

        Player player2 = new Player("Player2", 5, 7, Direction.SOUTH, flags.size());

        players.add(player1);
        players.add(player2);

        findLasers();
        placePlayers();
    }

}
