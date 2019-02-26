package inf112.roborally.game;

import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.world.Board;
import inf112.roborally.game.world.Direction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test for a player and "picking up" flags
 * Using the test-player constructor, with a default of 3 flags on the map
 */
public class FlagsFoundTest {
    private Player player;
//    private ArrayList<Flag> flags;
    // Not sure if board should be setup every time, so I set it up here
//    private Board board = new Board("assets/gameboard/vault.tmx");


    @Before
    public void setup() {
//        flags = new ArrayList<>();
//        flags.add(new Flag(1, 10, 1));
//        flags.add(new Flag(1, 2, 2));
//        flags.add(new Flag(6, 10, 3));

        player = new Player(0,0);

    }

    @Test
    public void playerFindsOneFlag() {
        player.addFlag(1);
        assertEquals(1, player.getFlagCounter());
    }

    @Test
    public void playerFindsTwoFlags() {
        player.addFlag(1);
        player.addFlag(2);
        assertEquals(2, player.getFlagCounter());
    }

    @Test
    public void playerFindsTheSameFlagTwice() {
        int flagnumber = 1;
        player.addFlag(flagnumber);
        player.addFlag(flagnumber);
        assertEquals(1, player.getFlagCounter());
    }

    @Test
    public void playerHasWon() {
        for (int i = 1; i < 4; i++) {
            player.addFlag(i);
        }
        assertTrue(player.thisPlayerHasWon());
    }

    @Test
    public void playerDoesNotWinWith2Flags() {
        player.addFlag(1);
        player.addFlag(2);
        assertFalse(player.thisPlayerHasWon());
    }

}
