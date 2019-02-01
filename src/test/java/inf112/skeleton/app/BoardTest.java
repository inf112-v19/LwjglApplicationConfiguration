package inf112.skeleton.app;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    public static Player player;
    private int x = 300; //Starting x pos
    private int y = 450; //Starting y pos

    @Before
    public void initialize(){
        player = new Player("Player1", x,y,Direction.SOUTH);
    }


    @Test
    public void move1ForwardWorks(){
        player.move(1);
        assertEquals(y+150, player.getY());
    }

    @Test
    public void move2ForwardWorks(){
        player.move(2);
        assertEquals(y+300, player.getY());
    }

    @Test
    public void move3ForwardWorks(){
        player.move(3);
        assertEquals(y+450, player.getY());
    }

    @Test
    public void changeDirToEastMove1(){
        player.changeDirection(Direction.EAST);
        player.move(1);
        assertEquals(x+150, player.getX());
    }


    @Test
    public void move1EastThen1West(){
        player.changeDirection(Direction.EAST);
        player.move(1);
        player.changeDirection(Direction.WEST);
        player.move(1);
        assertEquals(x,player.getX());
    }

    @Test
    public void move1South2East1North(){
        player.move(1);
        player.changeDirection(Direction.EAST);
        player.move(2);
        player.changeDirection(Direction.NORTH);
        player.move(1);
        assertEquals(x+300, player.getX());
        assertEquals(y, player.getY());
    }

    //...

}
