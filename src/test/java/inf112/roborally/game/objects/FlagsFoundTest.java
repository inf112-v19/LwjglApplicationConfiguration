package inf112.roborally.game.objects;

import org.junit.Before;
import org.junit.Test;

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

        player = new Player(0,0, 3);

    }

    @Test
    public void playerFindsOneFlag() {
        player.visitFlag(1);
        assertEquals(2, player.getTargetFlag());
    }

    @Test
    public void playerFindsTwoFlags() {
        player.visitFlag(1);
        player.visitFlag(2);
        assertEquals(3, player.getTargetFlag());
    }

    @Test
    public void playerFindsTheSameFlagTwice() {
        int flagnumber = 1;
        player.visitFlag(flagnumber);
        player.visitFlag(flagnumber);
        assertEquals(2, player.getTargetFlag());
    }

    @Test
    public void playerHasWon() {
        for (int i = 1; i < 4; i++) {
            player.visitFlag(i);
        }
        assertTrue(player.hasWon());
    }

    @Test
    public void playerDoesNotWinWith2Flags() {
        player.visitFlag(1);
        player.visitFlag(2);
        assertFalse(player.hasWon());
    }

    @Test
    public void flagNumberDoesNotExceedNumberOfFlags() {
        player.visitFlag(1);
        player.visitFlag(2);
        player.visitFlag(3);
        player.visitFlag(4);
        assertEquals(4, player.getTargetFlag());
    }
}
