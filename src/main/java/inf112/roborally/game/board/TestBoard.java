package inf112.roborally.game.board;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.RepairSite;

import java.util.ArrayList;

public class TestBoard extends Board {

    public TestBoard() {
        createBoard(RoboRallyGame.TEST_MAP);
    }

}
