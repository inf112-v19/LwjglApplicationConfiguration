package inf112.roborally.game.objects;

import inf112.roborally.game.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Quite foolish test for player getting repaired by RepairSite
 */
public class RepairSiteTest {
    private Player player;

    @Before
    public void setup() {
        player = new Player(0, 0, 1);
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

    @Test
    public void checkIfTakenDamage(){
        assertEquals(1, player.getDamage());
    }

    @Test
    public void repairAllWhileHavingOneDamage(){
        player.repairAllDamage();
        assertEquals(0, player.getDamage());
    }


    @Test
    public void repairAllDamage(){
        for (int i = 0; i < 5; i++)
            player.takeDamage();
        player.repairAllDamage();
        assertEquals(0, player.getDamage());
    }

    @Test
    public void repairAfterBeingDestroyedGivesZero(){
        for(int i = 0; i < 9; i++)
            player.takeDamage();
        player.repairAllDamage();
        assertEquals(2, player.getLives());
        assertEquals(0, player.getDamage());
    }


    @Test
    public void take2DamageAfterRepair(){
        for(int i = 0; i < 8; i++)
            player.takeDamage();
        player.repairAllDamage();
        player.takeDamage();
        player.takeDamage();
        assertEquals(2, player.getDamage());
    }



}
