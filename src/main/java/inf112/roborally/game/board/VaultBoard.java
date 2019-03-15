package inf112.roborally.game.board;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.RepairSite;

public class VaultBoard extends Board {

    public VaultBoard() {
        flags.add(new Flag(13, 6, 1));
        flags.add(new Flag(5, 8, 2));
        flags.add(new Flag(11, 4, 3));
        repairSites.add(new RepairSite(5, 2));
        createBoard(RoboRallyGame.VAULT);
        findLasers();
    }

}
