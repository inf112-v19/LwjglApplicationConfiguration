package inf112.roborally.game.player;

import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.enums.Rotate;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * Tests for executing cards
 * Note: Since the deck is a stack, when adding cards to the stack, its FILO,
 * so the first card to be added is the last card to be executed (I forgot that for a little while)
 */
public class CardExecuteTest {
    private Player player;
    private Stack<ProgramCard> manualTestDeck;
    private int standardPriority = 0;

    @Before
    public void setup() {
        player = new Player(0, 0, 1); // Player will by default face south
        player.rotate(Rotate.UTURN); // Rotate player so it faces North
        manualTestDeck = new Stack<>();
    }

    @Test
    public void playerMovesOneForward() {
        manualTestDeck.push(new ProgramCard(Rotate.NONE, 1, standardPriority,""));
        movePlayerAllCardsInDeck();
        assertEquals(0, player.getX()); // Check X pos
        assertEquals(1, player.getY()); // Check Y pos
    }

    @Test
    public void playerMovesTwoForwards() {
        manualTestDeck.push(new ProgramCard(Rotate.NONE, 2, standardPriority,""));
        movePlayerAllCardsInDeck();
        assertEquals(0, player.getX());
        assertEquals(2, player.getY());
    }

    @Test
    public void rotateRightThenOneForward() {
        manualTestDeck.push(new ProgramCard(Rotate.NONE, 1, standardPriority,""));
        manualTestDeck.push(new ProgramCard(Rotate.RIGHT, 0, standardPriority,""));
        movePlayerAllCardsInDeck();
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void playerMovesFiveCards() {
        manualTestDeck.push(new ProgramCard(Rotate.NONE, 3, standardPriority,""));
        manualTestDeck.push(new ProgramCard(Rotate.LEFT, 3, standardPriority,""));
        manualTestDeck.push(new ProgramCard(Rotate.UTURN, 0, standardPriority,""));
        manualTestDeck.push(new ProgramCard(Rotate.NONE, 2, standardPriority,""));
        manualTestDeck.push(new ProgramCard(Rotate.RIGHT, 0, standardPriority,""));
        movePlayerAllCardsInDeck();
        assertEquals(2, player.getX());
        assertEquals(-3, player.getY());
    }


    // Helper method only, not a test itself
    private void movePlayerAllCardsInDeck() {
        int n = manualTestDeck.size();
        for (int i = 0; i < n; i++) {
            ProgramCard currentCard = manualTestDeck.pop();
            if(currentCard.getRotate() != Rotate.NONE){
                player.rotate(currentCard.getRotate());
            }
            else {
                for (int j = 0; j < currentCard.getMoveDistance(); j++) {
                    player.move(1);
                }
            }
        }
    }


}
