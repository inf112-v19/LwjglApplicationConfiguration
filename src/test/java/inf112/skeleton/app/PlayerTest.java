package inf112.skeleton.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerTest {
    Player player;

    @Before
    public void setup(){
        player = new Player("testBot", 0,0, Direction.SOUTH);
    }

    @Test
    public void takeDamageTest(){
        player.takeDamage();
        assertEquals(1, player.getDamage());
    }

    @Test
    public void takeDamageTest2(){
        int expected = 1;
        for(int i = 0; i < 9; i++){
            player.takeDamage();
            assertEquals(expected++, player.getDamage());
        }
    }

    @Test
    public void zeroDmgDoesNotDestroy(){
        assertEquals(3, player.getLives());
        assert(!player.isDestroyed());
    }

    @Test
    public void nineDmgDoesdNotDestroy(){
        assertEquals(3, player.getLives());
        for(int i = 0; i < 9; i++){
            player.takeDamage();
        }
        assertEquals(3, player.getLives());
        assert(!player.isDestroyed());
    }

    @Test
    public void tenDmgDestroys(){
        assertEquals(3, player.getLives());
        for(int i = 0; i < 10; i++){
            player.takeDamage();
        }
        assertEquals(2,player.getLives());
    }

    @Test
    public void fiveDmgLocksOneRegister(){
        for(int i = 0; i < 5; i++)
            player.takeDamage();
        assert(player.isLocked(4));

        //the other registers should not be locked:
        for(int i = 0; i < 4; i++)
            assert(!player.isLocked(i));
    }

    @Test
    public void nineDmgLocksAllRegisters(){
        for(int i = 0; i < 9; i++)
            player.takeDamage();
        //all registers should be locked:
        for(int i = 0; i < 5; i++)
            assert(player.isLocked(4-i));
    }

    @Test
    public void repairResultsInZeroDmg(){
        for(int i = 0; i < 9; i++)
            player.takeDamage();
        player.repairDamage();
        assertEquals(0, player.getDamage());
        for(int i = 0; i < 5; i++)
            assert(!player.isLocked(i));
    }

    @Test
    public void returnCardsTest(){
        // TODO:
        fail();
    }

    @Test
    public void lockedCardsAreNotReturned(){
        // TODO:
        fail();
    }

    @Test
    public void playerMoveTest(){
        player.move(3);
        assertEquals(3*150, player.getY());
    }
}
