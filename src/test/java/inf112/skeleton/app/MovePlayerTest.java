package inf112.skeleton.app;


import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.GameObjects.PlayerMovement;
import inf112.skeleton.app.GameWorld.Board;
import inf112.skeleton.app.GameWorld.Direction;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MovePlayerTest {

    public static Player player;
    private int x = 0; //Starting x pos
    private int y = 0; //Starting y pos
    private PlayerMovement playerMovement;

    @Before
    public void initialize() {
        playerMovement = new PlayerMovement(x, y);
    }


    @Test
    public void move1ForwardWorks() {
        playerMovement.move(1);
        assertEquals(y - Main.TILE_LENGTH, playerMovement.getY());
    }

    @Test
    public void move2ForwardWorks() {
        playerMovement.move(1);
        assertEquals(y - Main.TILE_LENGTH *2f, playerMovement.getY());
    }

    @Test
    public void move3ForwardWorks() {
        playerMovement.move(3);
        assertEquals(y - Main.TILE_LENGTH *3, playerMovement.getY());
    }

    @Test
    public void rotateLeftMove1() {
        playerMovement.rotate(Rotate.LEFT);
        playerMovement.move(1);
        assertEquals(x + Main.TILE_LENGTH, playerMovement.getX());
    }


    @Test
    public void move1EastThen1West() {
        playerMovement.rotate(Rotate.LEFT);
        playerMovement.move(1);
        playerMovement.rotate(Rotate.UTURN);
        playerMovement.move(1);
        assertEquals(x, playerMovement.getX());
    }

    @Test
    public void Move1ThenRotateLeftThenMove2ThenRotateLeftMove1() {
        playerMovement.move(1);
        playerMovement.rotate(Rotate.LEFT);
        playerMovement.move(2);
        playerMovement.rotate(Rotate.LEFT);
        playerMovement.move(1);
        assertEquals(x + Main.TILE_LENGTH *2, playerMovement.getX());
        assertEquals(y, playerMovement.getY());
    }

    @Test
    public void rotateLeftMove3ThenRotateRightMove2ThenRotateUturnMove2() {
        playerMovement.rotate(Rotate.LEFT);
        playerMovement.move(3);
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.move(2);
        playerMovement.rotate(Rotate.UTURN);
        playerMovement.move(2);

        assertEquals(x + Main.TILE_LENGTH *3, playerMovement.getX());
        assertEquals(y, playerMovement.getY());
    }

    @Test
    public void RotateRightTwice() {
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        assertEquals(playerMovement.getDirection(), Direction.NORTH);
    }

    @Test
    public void rotateRightThreeTimes() {
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        assertEquals(playerMovement.getDirection(), Direction.EAST);
    }

    @Test
    public void rotateRightFourTimes(){
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        assertEquals(playerMovement.getDirection(), Direction.SOUTH);
    }

    @Test
    public void rotateRightFiveTimes(){
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        playerMovement.rotate(Rotate.RIGHT);
        assertEquals(playerMovement.getDirection(), Direction.WEST);
    }

    @Test
    public void doUturnThreeTimes(){
        playerMovement.rotate(Rotate.UTURN);
        playerMovement.rotate(Rotate.UTURN);
        playerMovement.rotate(Rotate.UTURN);
        assertEquals(playerMovement.getDirection(), Direction.NORTH);
    }
    //...

}