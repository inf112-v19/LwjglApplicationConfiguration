package inf112.roborally.game.board;

import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.board.ProgramRegisters;
import inf112.roborally.game.objects.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static junit.framework.TestCase.*;

public class ProgramRegisterTest {
    ProgramRegisters programRegisters;
    Stack<ProgramCard> stack;
    private Player player;

    @Before
    public void setup(){
        player = new Player(0,0);
        programRegisters = player.getRegisters();
        stack = ProgramCard.makeProgramCardDeck();
    }

    @Test
    public void test_IsLocked_WithNoLockedRegisters(){
        for(int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS; i++){
            assertEquals(false, programRegisters.isLocked(i));
        }
        assertEquals(true, programRegisters.isLocked(ProgramRegisters.NUMBER_OF_REGISTERS));
    }

    @Test
    public void test_IsLocked_WithOneLockedRegister(){
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
    public void receiveCard_AddsOneCardToCardsInHand(){
        player.receiveCard(stack.pop());
        assertEquals(1, player.getNumberOfCardsInHand());
    }

    @Test
    public void getCardLimit_EqualsNine(){
        assertEquals(ProgramRegisters.MAX_NUMBER_OF_CARDS, programRegisters.getCardLimit(player));
    }

    @Test
    public void getCardLimit_EqualsEightAfterOneDamage(){
        player.takeDamage();
        assertEquals(ProgramRegisters.MAX_NUMBER_OF_CARDS-1, programRegisters.getCardLimit(player));
    }

    @Test
    public void getCardLimit_EqualsZeroAfterNineDamage(){
        for(int i = 0; i < ProgramRegisters.MAX_NUMBER_OF_CARDS; i++)
            player.takeDamage();
        assertEquals(0, programRegisters.getCardLimit(player));
    }

    @Test
    public void returnCards_ReturnsAllCards(){
        for(int i = 0; i < 9; i++){
            player.receiveCard(stack.pop());
        }
        assertEquals(9, player.returnCards().size());
    }

    @Test
    public void returnCards_ReturnsAllCardsWhenNoRegistersAreLocked(){
        for(int i = 0; i < 9; i++){
            player.receiveCard(stack.pop());
        }
        for(int i = 0; i < 5; i++)
            programRegisters.pickCard(i);
        player.returnCards();
        assertEquals(9, player.returnCards().size());
    }

    @Test
    public void returnCards_ReturnsFourCardsWhenFiveRegistersAreLocked(){
        for(int i = 0; i < 9; i++){
            player.receiveCard(stack.pop());
        }
        for(int i = 0; i < 5; i++)
            programRegisters.pickCard(0);
        for (int i = 0; i < 5; i++)
            programRegisters.lockRegister();
        assertEquals(4, player.returnCards().size());
    }

    @Test
    public void returnCardsReturns_8CardsWhenOneRegistersAreLocked(){
        for(int i = 0; i < 9; i++)
            player.receiveCard(stack.pop());

        for(int i = 0; i < 5; i++)
            programRegisters.pickCard(0);

        programRegisters.lockRegister();
        assertEquals(8, player.returnCards().size());
    }

    @Test
    public void registerIsFull_ReturnsTrueWhenFull(){
        for(int i = 0; i < 9; i++)
            player.receiveCard(stack.pop());

        for(int i = 0; i < 5; i++)
            player.pickCardInRegister(0);

        assertEquals(true, programRegisters.registerIsFull());
    }

    @Test
    public void registerIsFull_ReturnsFalseWhenNotFull(){
        for(int i = 0; i < 9; i++)
            player.receiveCard(stack.pop());

        for(int i = 0; i < 1; i++)
            player.pickCardInRegister(0);

        assertEquals(false, programRegisters.registerIsFull());
    }
}
