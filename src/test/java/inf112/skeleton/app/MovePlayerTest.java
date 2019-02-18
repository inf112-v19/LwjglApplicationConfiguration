package inf112.skeleton.app;


import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.GameWorld.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovePlayerTest {

    public static Player player;
    private int x = 0; //Starting x pos
    private int y = 0; //Starting y pos

    @Before
    public void initialize() {
        //Board board = new Board(Main.VAULT);
        player = new Player("Player1", x, y, Direction.SOUTH);

    }


    @Test
    public void move1ForwardWorks() {
        player.move(1);
        assertEquals(y - Main.MOVE_DIST, player.getY());
    }

    @Test
    public void move2ForwardWorks() {
        player.move(2);
        assertEquals(y - Main.MOVE_DIST*2, player.getY());
    }

    @Test
    public void move3ForwardWorks() {
        player.move(3);
        assertEquals(y - Main.MOVE_DIST*3, player.getY());
    }

    @Test
    public void rotateLeftMove1() {
        player.rotate(Rotate.LEFT);
        player.move(1);
        assertEquals(x + Main.MOVE_DIST, player.getX());
    }


    @Test
    public void move1EastThen1West() {
        player.rotate(Rotate.LEFT);
        player.move(1);
        player.rotate(Rotate.UTURN);
        player.move(1);
        assertEquals(x, player.getX());
    }

    @Test
    public void Move1ThenRotateLeftThenMove2ThenRotateLeftMove1() {
        player.move(1);
        player.rotate(Rotate.LEFT);
        player.move(2);
        player.rotate(Rotate.LEFT);
        player.move(1);
        assertEquals(x + Main.MOVE_DIST*2, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    public void rotateLeftMove3ThenRotateRightMove2ThenRotateUturnMove2() {
        player.rotate(Rotate.LEFT);
        player.move(3);
        player.rotate(Rotate.RIGHT);
        player.move(2);
        player.rotate(Rotate.UTURN);
        player.move(2);

        assertEquals(x + Main.MOVE_DIST*3, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    public void RotateRightTwice() {
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        assertEquals(player.getDirection(), Direction.NORTH);
    }

    @Test
    public void rotateRightThreeTimes() {
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        assertEquals(player.getDirection(), Direction.EAST);
    }

    @Test
    public void rotateRightFourTimes(){
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        assertEquals(player.getDirection(), Direction.SOUTH);
    }

    @Test
    public void rotateRightFiveTimes(){
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        player.rotate(Rotate.RIGHT);
        assertEquals(player.getDirection(), Direction.WEST);
    }

    @Test
    public void doUturnThreeTimes(){
        player.rotate(Rotate.UTURN);
        player.rotate(Rotate.UTURN);
        player.rotate(Rotate.UTURN);
        assertEquals(player.getDirection(), Direction.NORTH);
    }
    //...

}