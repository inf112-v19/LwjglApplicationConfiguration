package inf112.skeleton.app;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerTest {
    Player player;
    ArrayList<ProgramCardCD> stack;

    @Before
    public void setup(){
        player = new Player("testBot", 0,0, Direction.SOUTH);
        stack = ProgramCardCD.makeStack();
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
        assert(!player.isDestroyed());
    }

    @Test
    public void nineDmgDoesdNotDestroy(){
        for(int i = 0; i < 9; i++){
            player.takeDamage();
        }
        assert(!player.isDestroyed());
    }

    @Test
    public void tenDmgDestroys(){
        for(int i = 0; i < 10; i++){
            player.takeDamage();
        }
        assert(player.isDestroyed());
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
    public void noLockedRegistersReturnsAllCards(){
        for(int i = 0; i < 9; i++) {
            // Player is given nine cards:
            player.receiveNewCard(stack.remove(0));
            if (i < 5)
                // Player puts the five first in registers:
                player.pickCard(0);
        }

        ArrayList<ProgramCardCD> cardsReturned = player.returnCards();
        assertEquals(9, cardsReturned.size());

        // All registers are empty:
        for(ProgramCardCD register : player.getRegisters())
            assert(register == null);
    }

    @Test
    public void lockedCardsAreNotReturned(){
        for(int i = 0; i < 9; i++) {
            // Player is given nine cards:
            player.receiveNewCard(stack.remove(0));
            if (i < 5)
                // Player puts the five first in registers:
                player.pickCard(0);
        }
        // Player takes nine damage, locking all registers:
        for(int i = 0; i < 9; i++)
            player.takeDamage();

        // Only cards in hand are returned:
        ArrayList<ProgramCardCD> cardsReturned = player.returnCards();
        assertEquals(4, cardsReturned.size());

        // All registers contains program cards:
        for(ProgramCardCD register : player.getRegisters())
            assert(register != null);
    }

    @Test
    public void lockedCardsAreNotReturned2(){
        for(int i = 0; i < 9; i++) {
            // Player is given nine cards:
            player.receiveNewCard(stack.remove(0));
            if (i < 5)
                // Player puts the five first in registers:
                player.pickCard(0);
        }
        // Player takes 5 damage, locking one register:
        for(int i = 0; i < 5; i++)
            player.takeDamage();

        // Cards in hand are returned + 4 from registers:
        ArrayList<ProgramCardCD> cardsReturned = player.returnCards();
        assertEquals(8, cardsReturned.size());

        // The first 4 registers contains program cards:
        for(int i = 0; i < 4; i++)
            assert(player.getRegisters().get(i) == null);
        // The last register does not:
        assert(player.getRegisters().get(4) != null);
    }

    @Test
    public void priorityTest(){
        PriorityQueue<ProgramCardCD> q = new PriorityQueue<>();
        // create 3 players:
        Player p1 = new Player("p1", 0,0);
        Player p2 = new Player("p2", 0,1);
        Player p3 = new Player("p3", 0,2);
        // give them five cards each:
        for(int i = 0; i < 5; i++){
            p1.receiveNewCard(stack.remove(0));
            p2.receiveNewCard(stack.remove(0));
            p3.receiveNewCard(stack.remove(0));
        }
        // have them all pick the first card and place it in a register:
        p1.pickCard(0);
        p2.pickCard(0);
        p3.pickCard(0);
        // retrive cards from the first register:
        q.add(p1.getCardInRegister(0));
        q.add(p2.getCardInRegister(0));
        q.add(p3.getCardInRegister(0));
        // print in order of highest priority
        while(!q.isEmpty()) {
            System.out.println(q.poll());
        }
    }

}
