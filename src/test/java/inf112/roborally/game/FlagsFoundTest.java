package inf112.roborally.game;

import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.world.Direction;
import org.junit.Before;

import java.util.ArrayList;

public class FlagsFoundTest {
    private Player player;
    private ArrayList<Flag> flags;


    @Before
    public void setup() {
        flags = new ArrayList<>();
        flags.add(new Flag(1, 10, 1));
        flags.add(new Flag(1, 2, 2));
        flags.add(new Flag(6, 10, 3));

        player = new Player("Player 1", 2, 2, Direction.NORTH, 3);

    }

}
