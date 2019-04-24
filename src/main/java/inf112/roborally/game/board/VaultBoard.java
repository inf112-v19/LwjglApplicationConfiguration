package inf112.roborally.game.board;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Position;

import java.util.ArrayList;

public class VaultBoard extends Board {

    public VaultBoard() {
        flags.add(new Flag(13, 6, 1));
        flags.add(new Flag(5, 8, 2));
        flags.add(new Flag(11, 4, 3));
        createBoard(RoboRallyGame.VAULT);
        findLaserGuns();
    }

    public VaultBoard(ArrayList<Position> flagPositions) {
        for (int i = 0; i < flagPositions.size(); i++) {
            Position currPos = flagPositions.get(i);
            flags.add(new Flag(currPos.getX(), currPos.getY(), i + 1));
        }
        createBoard(RoboRallyGame.VAULT);
        findLaserGuns();
    }

}
