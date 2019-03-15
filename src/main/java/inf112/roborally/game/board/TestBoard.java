package inf112.roborally.game.board;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.RepairSite;

public class TestBoard extends Board {

    public TestBoard() {
        flags.add(new Flag(1, 10, 1));
        flags.add(new Flag(6, 2, 2));
        flags.add(new Flag(6, 10, 3));
        repairSites.add(new RepairSite(5, 2));
        createBoard(RoboRallyGame.TEST_MAP);
    }

}
