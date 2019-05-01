package inf112.roborally.game.player;

import inf112.roborally.game.enums.Rotate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class PlayerHandTest {
    private Player player;
    private PlayerHand hand;
    private ProgramCard card;

    @Before
    public void setup() {
        player = new Player(0, 0, 1);
        hand = player.getHand();
        card = new ProgramCard(Rotate.NONE, 0, 0);
    }

    @Test
    public void returnCardsRemovesAllCards() {
        for (int i = 0; i < 9; i++) hand.receiveCard(card);
        hand.returnCards(new ArrayList<ProgramCard>());
        assertEquals(0, hand.size());
        assertEquals(false, hand.isFull());
    }

    @Test
    public void removeCardRemovesOneCard() {
        for (int i = 0; i < 9; i++) hand.receiveCard(card);
        hand.removeCard(0);
        assertEquals(8, hand.size());
        assertEquals(false, hand.isFull());
    }

    @Test
    public void handFullDoesNotRecieveMoreCards() {
        for (int i = 0; i < 10; i++) hand.receiveCard(card);
        assertEquals(9, hand.size());
        assertEquals(true, hand.isFull());
    }

    @Test
    public void handFullWhenReceivingNineCards() {
        for (int i = 0; i < 9; i++) hand.receiveCard(card);
        assertEquals(9, hand.size());
        assertEquals(true, hand.isFull());
    }

    @Test
    public void handNotFullWhenReceivingEightCards() {
        for (int i = 0; i < 8; i++) hand.receiveCard(card);
        assertEquals(8, hand.size());
        assertEquals(false, hand.isFull());
    }

    @Test
    public void handNotFullWhenReceivingTwoCards() {
        for (int i = 0; i < 2; i++) hand.receiveCard(card);
        assertEquals(2, hand.size());
        assertEquals(false, hand.isFull());
    }

    @Test
    public void handNotFullWhenReceivingOneCard() {
        hand.receiveCard(card);
        assertEquals(1, hand.size());
        assertEquals(false, hand.isFull());
        assertEquals(true, hand.getCardsInHand().contains(card));
    }
}
