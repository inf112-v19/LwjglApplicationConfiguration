package inf112.roborally.game.server;

import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.player.ProgramCard;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PacketSplitterTest {
    String msg;

    @Before
    public void setup() {
        msg = "KEYWORD la di da dooo";
    }

    @Test
    public void sanityTest() {
        assertEquals("KEYWORD", PacketSplitter.keyWord(msg));
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongKeywordMakesPacketSplitterThrowE() {
        PacketSplitter.programCard(msg);
    }

    @Test (expected =  IndexOutOfBoundsException.class)
    public void shortMessageMakesPacketSplitterThrowE(){
        PacketSplitter.programCard("CARD");
    }

    @Test
    public void cardTest(){
        ProgramCard pc = PacketSplitter.programCard("CARD name NONE 3 10");
        assertEquals(Rotate.NONE, pc.getRotate());
        assertEquals(3, pc.getMoveDistance());
        assertEquals(10, pc.getPriority());
    }

    @Test
    public void nCardsTest(){
        assertEquals(4, PacketSplitter.nCards("RECEIVE_CARD 4 name"));
    }

    @Test
    public void nameTest(){
        assertEquals("name", PacketSplitter.name("RECEIVE_CARD 4 name"));
    }

    @Test
    public void cardMsgTest() {
        String cardMsg = "CARD name NONE 2 10";
        assertEquals("CARD", PacketSplitter.keyWord(cardMsg));
    }
}
