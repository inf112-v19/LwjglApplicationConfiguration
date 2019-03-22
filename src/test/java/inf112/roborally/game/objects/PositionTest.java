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
}
