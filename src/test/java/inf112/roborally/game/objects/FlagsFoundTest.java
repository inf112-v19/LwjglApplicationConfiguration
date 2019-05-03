package inf112.roborally.game.objects;

import inf112.roborally.game.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for a player and "picking up" flags
 * Using the test-player constructor, with a default of 3 flags on the map
 */
public class FlagsFoundTest {
    private Player player;

    @Before
    public void setup() {
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
    public void playerDoesNotWinUnlessGoingToFlagsInCorrectOrder(){
        player.visitFlag(2);
        player.visitFlag(1);
        player.visitFlag(3);
        assertFalse(player.hasWon());
        assertEquals(2, player.getTargetFlag());

        player.visitFlag(3);
        player.visitFlag(2);
        player.visitFlag(1);
        assertEquals(3, player.getTargetFlag());

        player.visitFlag(2);
        player.visitFlag(3);
        assertTrue(player.hasWon());
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
