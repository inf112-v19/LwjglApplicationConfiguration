package inf112.roborally.game;

import inf112.roborally.game.objects.Rotate;
import inf112.roborally.game.world.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {
    int result;
    Direction dir;
    Rotate rotation;

    @Before
    public void setup(){
        result = 0;
        dir = Direction.NORTH;
        rotation = Rotate.NONE;
    }

    @Test
    public void directionToDegree(){
        result = 0;
        dir = Direction.SOUTH;
        assertEquals(result, dir.getRotationDegree());

        result += 90;
        dir = Direction.EAST;
        assertEquals(result, dir.getRotationDegree());
        result += 90;
        dir = Direction.NORTH;
        assertEquals(result, dir.getRotationDegree());

        result += 90;
        dir = Direction.WEST;
        assertEquals(result, dir.getRotationDegree());
    }

    @Test
    public void testGetOpposite(){
        dir = Direction.EAST;
        assertEquals(dir.getOppositeDirection(), Direction.WEST);

        dir = Direction.WEST;
        assertEquals(dir.getOppositeDirection(), Direction.EAST);

        dir = Direction.NORTH;
        assertEquals(dir.getOppositeDirection(), Direction.SOUTH);

        dir = Direction.SOUTH;
        assertEquals(dir.getOppositeDirection(), Direction.NORTH);
    }

    @Test
    public void testUturn(){
        rotation = Rotate.UTURN;
        assertEquals(Direction.SOUTH, Direction.NORTH.rotate(rotation));
        assertEquals(Direction.NORTH, Direction.SOUTH.rotate(rotation));
        assertEquals(Direction.WEST, Direction.EAST.rotate(rotation));
        assertEquals(Direction.EAST, Direction.WEST.rotate(rotation));
    }

    @Test
    public void testTurnLeft(){
        rotation = Rotate.LEFT;
        assertEquals(Direction.SOUTH, Direction.WEST.rotate(rotation));
        assertEquals(Direction.WEST, Direction.NORTH.rotate(rotation));
        assertEquals(Direction.NORTH, Direction.EAST.rotate(rotation));
        assertEquals(Direction.EAST, Direction.SOUTH.rotate(rotation));
    }

    @Test
    public void testTurnRight(){
        rotation = Rotate.RIGHT;
        assertEquals(Direction.SOUTH, Direction.EAST.rotate(rotation));
        assertEquals(Direction.EAST, Direction.NORTH.rotate(rotation));
        assertEquals(Direction.NORTH, Direction.WEST.rotate(rotation));
        assertEquals(Direction.WEST, Direction.SOUTH.rotate(rotation));
    }

}
