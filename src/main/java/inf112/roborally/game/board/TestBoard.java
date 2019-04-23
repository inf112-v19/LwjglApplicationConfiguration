package inf112.roborally.game.board;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Flag;

public class TestBoard extends Board {

    public TestBoard() {flags.add(new Flag(16, 16, 1));
        createBoard(RoboRallyGame.TEST_MAP);
        findLaserGuns();
    }

}
