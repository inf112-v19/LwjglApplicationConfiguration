package inf112.roborally.game;

import inf112.roborally.game.Main;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.Rotate;
import inf112.roborally.game.world.Direction;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MovePlayerTest {

    private int x = 0; //Starting x pos
    private int y = 0; //Starting y pos
    private Player player;

    @Before
    public void initialize() {
        player = new Player(x, y);
    }


    @Test
    public void move1ForwardWorks() {
        player.move(1);
        assertEquals(y - Main.TILE_LENGTH,(int) player.getY());
    }

    @Test
    public void move2ForwardWorks() {
        player.move(2);
        assertEquals(y - Main.TILE_LENGTH *2, (int) player.getY());
    }

    @Test
    public void move3ForwardWorks() {
        player.move(3);
        assertEquals(y - Main.TILE_LENGTH *3, (int) player.getY());
    }

    @Test
    public void rotateLeftMove1() {
        player.rotate(Rotate.LEFT);
        player.move(1);
        assertEquals(x + Main.TILE_LENGTH, (int) player.getX());
    }


    @Test
    public void move1EastThen1West() {
        player.rotate(Rotate.LEFT);
        player.move(1);
        player.rotate(Rotate.UTURN);
        player.move(1);
        assertEquals(x, (int) player.getX());
    }

    @Test
    public void Move1ThenRotateLeftThenMove2ThenRotateLeftMove1() {
        player.move(1);
        player.rotate(Rotate.LEFT);
        player.move(2);
        player.rotate(Rotate.LEFT);
        player.move(1);
        assertEquals(x + Main.TILE_LENGTH *2, (int) player.getX());
        assertEquals(y, (int) player.getY());
    }

    @Test
    public void rotateLeftMove3ThenRotateRightMove2ThenRotateUturnMove2() {
        player.rotate(Rotate.LEFT);
        player.move(3);
        player.rotate(Rotate.RIGHT);
        player.move(2);
        player.rotate(Rotate.UTURN);
        player.move(2);

        assertEquals(x + Main.TILE_LENGTH *3, (int) player.getX());
        assertEquals(y, (int) player.getY());
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