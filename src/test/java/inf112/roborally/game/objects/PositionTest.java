package inf112.roborally.game.objects;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class PositionTest {

    @Test
    public void testEquals(){
        Position a = new Position(1,1);
        Position b = new Position(1,1);
        Position c = new Position(0,0);

        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
    }

    @Test
    public void testPositionNotEqual(){
        Position a = new Position(1, 1);
        Position b = new Position(0, 1);
        Position c = new Position(1, 0);
        Position d = new Position(1,0);
        
        assertFalse(a.equals(b));
        assertFalse(a.equals(c));
        assertFalse(b.equals(c));

    }

    @Test
    public void testPositionEqual(){
        Position a = new Position(1, 1);
        Position b = new Position(0, 1);
        Position c = new Position(1, 0);
        Position d = new Position(1,0);

        assertTrue(a.equals(a));
        assertTrue(b.equals(b));
        assertTrue(c.equals(d));
    }
}
