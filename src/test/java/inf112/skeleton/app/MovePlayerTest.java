package inf112.skeleton.app;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MovePlayerTest {

    public static Player player;
    private int x = 300;
    private int y = 450;
    final static int ONE_STEP = 150;

    /*
    Stacks of cards are used when doing the same move twice in a test. It is to have
    two separate cards, but this might be overkill in terms of readability for the code.
    Might want to clean up later.
    */
    public static ArrayList<ProgramCard> stackOfCards;
    public static ArrayList<ProgramCard> rotateLeftStack;
    public static ArrayList<ProgramCard> rotateRightStack;
    public static ArrayList<ProgramCard> rotateUturnStack;
    public static ArrayList<ProgramCard> move1Stack;
    public static ArrayList<ProgramCard> move2Stack;
    public static ArrayList<ProgramCard> move3Stack;


    public static int move1;
    public static int move2;
    public static int move3;
    public static Rotate rotateLeft;
    public static Rotate rotateRight;
    public static Rotate rotateUturn;

    @Before
    public void initialize() {
        player = new Player("Player1", x, y, Direction.SOUTH);
        stackOfCards = new ProgramCard().makeStack();

        rotateLeftStack = new ArrayList<>();
        rotateRightStack = new ArrayList<>();
        rotateUturnStack = new ArrayList<>();
        move1Stack = new ArrayList<>();
        move2Stack = new ArrayList<>();
        move3Stack = new ArrayList<>();

        rotateLeft = Rotate.NONE;
        rotateRight = Rotate.NONE;
        rotateUturn = Rotate.NONE;
        organizeCards();
    }

    public void organizeCards() {
        for (ProgramCard pc : stackOfCards) {
            if (pc.getRotate() == Rotate.LEFT) {
                rotateLeftStack.add(pc);
                rotateLeft = pc.getRotate();
            } else if (pc.getRotate() == Rotate.RIGHT) {
                rotateRightStack.add(pc);
                rotateRight = pc.getRotate();
            } else if (pc.getRotate() == Rotate.UTURN) {
                rotateUturnStack.add(pc);
                rotateUturn = pc.getRotate();
            } else if (pc.getMoveDistance() == 1) {
                move1Stack.add(pc);
                move1 = pc.getMoveDistance();
            } else if (pc.getMoveDistance() == 2) {
                move2Stack.add(pc);
                move2 = pc.getMoveDistance();
            } else if (pc.getMoveDistance() == 3) {
                move3Stack.add(pc);
                move3 = pc.getMoveDistance();
            }
        }
    }

    @Test
    public void move1ForwardWorks() {
        player.move(move1);
        assertEquals(y + ONE_STEP, player.getY());
    }

    @Test
    public void move2ForwardWorks() {
        player.move(move2);
        assertEquals(y + ONE_STEP * 2, player.getY());
    }

    @Test
    public void move3ForwardWorks() {
        player.move(move3);
        assertEquals(y + ONE_STEP * 3, player.getY());
    }

    @Test
    public void move3Forward5TimesWithDifferentCards() {
        for (int i = 0; i < 5; i++) {
            player.move(move3Stack.remove(0).getMoveDistance());
        }
        assertEquals(y + ONE_STEP * 3 * 5, player.getY());
    }

    @Test
    public void rotateLeft() {
        player.rotate(rotateLeft);
        assertEquals(Direction.EAST, player.getDirection());
    }

    @Test
    public void rotateLeftMove1() {
        player.rotate(rotateLeft);
        player.move(move1);
        assertEquals(x + ONE_STEP, player.getX());
    }


    @Test
    public void rotateLeftMove1ThenDoUturnMove1() {
        player.rotate(rotateLeft);
        player.move(move1Stack.remove(0).getMoveDistance());
        player.rotate(rotateUturn);
        player.move(move1Stack.remove(0).getMoveDistance());
        assertEquals(x, player.getX());
    }


    @Test
    public void move1RotateLeftMove2ThenRotateLeftMove1() {
        player.move(move1Stack.remove(0).getMoveDistance());
        player.rotate(rotateLeftStack.remove(0).getRotate());
        player.move(move2);
        player.rotate(rotateLeftStack.remove(0).getRotate());
        player.move(move1Stack.remove(0).getMoveDistance());
        assertEquals(x + ONE_STEP * 2, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    public void rotateLeftMove3RotateRightMove2DoUturnMove2() {
        player.rotate(rotateLeft);
        player.move(move3);
        player.rotate(rotateRight);
        player.move(move2);
        player.rotate(rotateUturn);
        player.move(move2);

        assertEquals(x + ONE_STEP * 3, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    public void RotateRightTwice() {
        for (int i = 0; i < 2; i++) {
            player.rotate(rotateRightStack.remove(0).getRotate());
        }
        assertEquals(player.getDirection(), Direction.NORTH);
    }

    @Test
    public void rotateRightThreeTimes() {
        for (int i = 0; i < 3; i++) {
            player.rotate(rotateRightStack.remove(0).getRotate());
        }
        assertEquals(player.getDirection(), Direction.EAST);
    }

    @Test
    public void rotateRightFourTimes() {
        for (int i = 0; i < 4; i++) {
            player.rotate(rotateRightStack.remove(0).getRotate());
        }
        assertEquals(player.getDirection(), Direction.SOUTH);
    }

    @Test
    public void rotateRightFiveTimes() {
        for (int i = 0; i < 5; i++) {
            player.rotate(rotateRightStack.remove(0).getRotate());
        }
        assertEquals(player.getDirection(), Direction.WEST);
    }

    @Test
    public void doUturnThreeTimes() {
        for (int i = 0; i < 3; i++) {
            player.rotate(rotateUturnStack.remove(0).getRotate());
        }
        assertEquals(player.getDirection(), Direction.NORTH);
    }

}
