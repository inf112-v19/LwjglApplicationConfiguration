package inf112.roborally.game;

import inf112.roborally.game.objects.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static junit.framework.TestCase.*;

public class ProgramRegisterTest {
    Player player = new Player(0,0);
    ProgramRegisters programRegisters;
    Stack<ProgramCard> stack;

    @Before
    public void setup(){
        programRegisters = new ProgramRegisters();
        stack = ProgramCard.makeStack();
    }

    @Test
    public void testIsLockedWithNoLockedRegisters(){
        for(int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS; i++){
            assertEquals(false, programRegisters.isLocked(i));
        }
        assertEquals(true, programRegisters.isLocked(ProgramRegisters.NUMBER_OF_REGISTERS));
    }

    @Test
    public void testIsLockedWithOneLockedRegister(){
        programRegisters.lockRegister();
        for(int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS-1; i++){
            assertEquals(false, programRegisters.isLocked(i));
        }
        assertEquals(true, programRegisters.isLocked(ProgramRegisters.NUMBER_OF_REGISTERS-1));
    }

    @Test
    public void lockingAllRegistersLocksAllRegisters(){
        for(int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS; i++){
            programRegisters.lockRegister();
        }

        for(int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS; i++){
            assertEquals(true, programRegisters.isLocked(i));
        }
    }

    @Test
    public void receiveCardAddsOneCardToCardsInHand(){
        programRegisters.receiveCard(stack.pop());
        assertEquals(1, programRegisters.getCardsInHand().size());
    }

    @Test
    public void getCardLimitEqualsNine(){
        assertEquals(ProgramRegisters.MAX_NUMBER_OF_CARDS, programRegisters.getCardLimit(player));
    }

    @Test
    public void getCardLimitEqualsEightAfterOneDamage(){
        player.takeDamage();
        assertEquals(ProgramRegisters.MAX_NUMBER_OF_CARDS-1, programRegisters.getCardLimit(player));
    }

    @Test
    public void getCardLimitEqualsZeroAfterNineDamage(){
        for(int i = 0; i < ProgramRegisters.MAX_NUMBER_OF_CARDS; i++)
            player.takeDamage();
        assertEquals(0, programRegisters.getCardLimit(player));
    }

    @Test
    public void returnCardsReturnsAllCards(){
        for(int i = 0; i < 9; i++){
            programRegisters.receiveCard(stack.pop());
        }
        assertEquals(9, programRegisters.returnCards().size());
    }

    @Test
    public void returnCardsReturnsAllCardsWhenNoRegistersAreLocked(){
        for(int i = 0; i < 9; i++){
            programRegisters.receiveCard(stack.pop());
        }
        for(int i = 0; i < 5; i++)
            programRegisters.pickCard(0);
        assertEquals(9, programRegisters.returnCards().size());
    }

    @Test
    public void returnCardsReturnsFourCardsWhenFiveRegistersAreLocked(){
        for(int i = 0; i < 9; i++){
            programRegisters.receiveCard(stack.pop());
        }
        for(int i = 0; i < 5; i++)
            programRegisters.pickCard(0);
        for (int i = 0; i < 5; i++)
            programRegisters.lockRegister();
        assertEquals(4, programRegisters.returnCards().size());
    }

    @Test
    public void returnCardsReturns8CardsWhenOneRegistersAreLocked(){
        for(int i = 0; i < 9; i++)
            programRegisters.receiveCard(stack.pop());

        for(int i = 0; i < 5; i++)
            programRegisters.pickCard(0);

        programRegisters.lockRegister();
        assertEquals(8, programRegisters.returnCards().size());
    }

    @Test
    public void registerIsFullReturnsTrueWhenFull(){
        for(int i = 0; i < 9; i++)
            programRegisters.receiveCard(stack.pop());

        for(int i = 0; i < 5; i++)
            programRegisters.pickCard(0);

        assertEquals(true, programRegisters.registerIsFull());
    }

    @Test
    public void registerIsFullReturnsFalseWhenNotFull(){
        for(int i = 0; i < 9; i++)
            programRegisters.receiveCard(stack.pop());

        for(int i = 0; i < 1; i++)
            programRegisters.pickCard(0);

        assertEquals(false, programRegisters.registerIsFull());
    }
}
