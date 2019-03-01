package inf112.roborally.game;

import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.RepairSite;
import inf112.roborally.game.world.Board;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Quite foolish test for player getting repaired by RepairSite
 */
public class RepairSiteTest {
    private Player player;

    @Before
    public void setup() {
        player = new Player(0, 0);
//        repairSites = new ArrayList<>();
//        repairSites.add(new RepairSite(0, 0));
        // Oof, player takes one damage before each test
        player.takeDamage();
    }

    @Test
    public void repairOneDamageOnPlayer() {
        player.repairOneDamage();
        assertEquals(0, player.getDamage());
    }


}
