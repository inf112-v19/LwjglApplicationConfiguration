package inf112.roborally.game.player;

import inf112.roborally.game.enums.Rotate;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProgramCardTest {
    private Stack<ProgramCard> stackOfCards;

    @Before
    public void setup() {
        stackOfCards = ProgramCard.makeProgramCardDeck();
    }

    @Test
    public void secondConstructorTest() {
        ProgramCard pc = new ProgramCard("NONE","1","10");
        assertEquals(Rotate.NONE, pc.getRotate());
        assertEquals(1, pc.getMoveDistance());
        assertEquals(10, pc.getPriority());

        pc = new ProgramCard("LEFT", "0", "111");
        assertEquals(Rotate.LEFT, pc.getRotate());
        assertEquals(0, pc.getMoveDistance());
        assertEquals(111, pc.getPriority());

        pc = new ProgramCard("RIGHT", "10", "9000");
        assertEquals(Rotate.RIGHT, pc.getRotate());
        assertEquals(10, pc.getMoveDistance());
        assertEquals(9000, pc.getPriority());


    }

    @Test
    public void stackContains18RotateLeft() {
        int result = 0;
        for (ProgramCard pc : stackOfCards) {
            if (pc.getRotate() == Rotate.LEFT)
                result++;
        }
        assertEquals(18, result);
    }

    @Test
    public void stackContains18RotateRight() {
        int result = 0;
        for (ProgramCard pc : stackOfCards) {
            if (pc.getRotate() == Rotate.RIGHT)
                result++;
        }
        assertEquals(18, result);
    }

    @Test
    public void stackContains6TurnAroundCards() {
        int result = 0;
        for (ProgramCard pc : stackOfCards) {
            if (pc.getRotate() == Rotate.UTURN)
                result++;
        }
        assertEquals(6, result);
    }

    @Test
    public void stackContains18MoveOneCards() {
        int result = 0;
        for (ProgramCard pc : stackOfCards) {
            if (pc.getMoveDistance() == 1)
                result++;
        }
        assertEquals(18, result);
    }

    @Test
    public void stackContains12MoveTwoCards() {
        int result = 0;
        for (ProgramCard pc : stackOfCards) {
            if (pc.getMoveDistance() == 2)
                result++;
        }
        assertEquals(12, result);
    }

    @Test
    public void stackContains6MoveThreeCards() {
        int result = 0;
        for (ProgramCard pc : stackOfCards) {
            if (pc.getMoveDistance() == 3)
                result++;
        }
        assertEquals(6, result);
    }

    @Test
    public void stackContains6MoveBackwardsCards() {
        int result = 0;
        for (ProgramCard pc : stackOfCards) {
            if (pc.getMoveDistance() == -1)
                result++;
        }
        assertEquals(6, result);
    }

    @Test
    public void stackContainsAllUniquePriorities() {
        for (int i = 0; i < stackOfCards.size() - 1; i++) {
            for (int j = i + 1; j < stackOfCards.size(); j++) {
                int priorityI = stackOfCards.get(i).getPriority();
                int priorityJ = stackOfCards.get(j).getPriority();
                assert (priorityI != priorityJ);
            }
            System.out.println(stackOfCards.get(i));
        }
        System.out.println(stackOfCards.get(stackOfCards.size() - 1));
    }

    @Test
    public void everyCardHasEitherRotateOrMoveValue() {
        for (ProgramCard pc : stackOfCards) {
            if (pc.getMoveDistance() != 0)
                assert (pc.getRotate() == Rotate.NONE);
            if (pc.getRotate() != Rotate.NONE)
                assertEquals(0, pc.getMoveDistance());
            if (pc.getRotate() == Rotate.NONE && pc.getMoveDistance() == 0)
                fail();
        }
    }
}
