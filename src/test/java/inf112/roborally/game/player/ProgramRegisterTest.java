package inf112.roborally.game.player;

import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.player.ProgramRegisters;
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
        player = new Player(0,0, 1);
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
        programRegisters.lock();
        for(int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS-1; i++){
            assertEquals(false, programRegisters.isLocked(i));
        }
        assertEquals(true, programRegisters.isLocked(ProgramRegisters.NUMBER_OF_REGISTERS-1));
    }

    @Test
    public void lockingAllRegistersLocksAllRegisters(){
        for(int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS; i++){
            programRegisters.lock();
        }

        for(int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS; i++){
            assertEquals(true, programRegisters.isLocked(i));
        }
    }

    @Test
    public void receiveCard_AddsOneCardToCardsInHand(){
        player.getHand().receiveCard(stack.pop());
        assertEquals(1, player.getHand().size());
    }

    @Test
    public void PlayerDoesNotReceiveACardIfHandIsFull(){
        for(int i = 0; i < player.getCardLimit(); i++)
            player.getHand().receiveCard(stack.pop());

        assertEquals(ProgramRegisters.MAX_NUMBER_OF_CARDS, player.getHand().size());

        player.getHand().receiveCard(stack.pop());
        assertEquals(ProgramRegisters.MAX_NUMBER_OF_CARDS, player.getHand().size());
    }



    @Test
    public void getCardLimit_EqualsMaxNumberOfCards(){
        assertEquals(ProgramRegisters.MAX_NUMBER_OF_CARDS, player.getCardLimit());
    }

    @Test
    public void getCardLimit_EqualsEightAfterOneDamage(){
        player.takeDamage();
        assertEquals(ProgramRegisters.MAX_NUMBER_OF_CARDS-1, player.getCardLimit());
    }

    @Test
    public void getCardLimit_EqualsZeroAfterNineDamage(){
        for(int i = 0; i < ProgramRegisters.MAX_NUMBER_OF_CARDS; i++)
            player.takeDamage();
        assertEquals(0, player.getCardLimit());
    }

    @Test
    public void returnCards_ReturnsAllCards(){
        for(int i = 0; i < 9; i++){
            player.getHand().receiveCard(stack.pop());
        }
        assertEquals(9, player.returnCards().size());
    }

    @Test
    public void returnCards_ReturnsAllCardsWhenNoRegistersAreLocked(){
        for(int i = 0; i < 9; i++){
            player.getHand().receiveCard(stack.pop());
        }
        for(int i = 0; i < 5; i++)
            programRegisters.placeCard(i);
        player.returnCards();
        assertEquals(9, player.returnCards().size());
    }

    @Test
    public void returnCards_ReturnsFourCardsWhenFiveRegistersAreLocked(){
        for(int i = 0; i < 9; i++){
            player.getHand().receiveCard(stack.pop());
        }
        for(int i = 0; i < 5; i++)
            programRegisters.placeCard(0);
        for (int i = 0; i < 5; i++)
            programRegisters.lock();
        assertEquals(4, player.returnCards().size());
    }

    @Test
    public void returnCardsReturns_8CardsWhenOneRegistersAreLocked(){
        for(int i = 0; i < 9; i++)
            player.getHand().receiveCard(stack.pop());

        for(int i = 0; i < 5; i++)
            programRegisters.placeCard(0);

        programRegisters.lock();
        assertEquals(8, player.returnCards().size());
    }

    @Test
    public void registerIsFull_ReturnsTrueWhenFull(){
        for(int i = 0; i < 9; i++)
            player.getHand().receiveCard(stack.pop());

        for(int i = 0; i < 5; i++)
            player.getRegisters().placeCard(0);

        assertEquals(true, programRegisters.isFull());
    }

    @Test
    public void registerIsFull_ReturnsFalseWhenNotFull(){
        for(int i = 0; i < 9; i++)
            player.getHand().receiveCard(stack.pop());

        for(int i = 0; i < 1; i++)
            player.getRegisters().placeCard(0);

        assertEquals(false, programRegisters.isFull());
    }

    @Test
    public void registerIsFull_ReturnsFalseWhenCalling_returnCards_AfterRegisterBeingFull(){
        for(int i = 0; i < 9; i++)
            player.getHand().receiveCard(stack.pop());

        for(int i = 0; i < 5; i++)
            player.getRegisters().placeCard(0);
        player.getRegisters().returnCards(player);

        assertEquals(false, programRegisters.isFull());
    }

}
