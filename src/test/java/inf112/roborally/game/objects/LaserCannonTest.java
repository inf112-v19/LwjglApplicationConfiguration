package inf112.roborally.game.objects;

import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * These tests checks if lasers are fired from the correct position in the correct direction.
 * They do not test if lasers are fired through walls.
 */
public class LaserCannonTest {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private ArrayList<Player> players;

    @Before
    public void setup(){
        player1 = new Player(0,0,1);
        player2 = new Player(4,0,1);
        player3 = new Player(3,0,1);
        player4 = new Player(2,1,1);
        player5 = new Player(1,1,1);
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
    }

    @Test
    public void lasersShotsDoNotPenetrateRobots(){
        player1.setDirection(Direction.EAST);
        player1.getLaserCannon().fire(players);
        assertEquals(0, player2.getDamage());
        assertEquals(1, player3.getDamage());
    }

    @Test
    public void lasersCanFireInAnyDirection(){
        player1.move(1,1);
        player2.move(0,1);
        player3.move(1,0);
        player4.move(2,1);
        player5.move(1,2);
        // Player 1 has a robot in every direction

        player1.setDirection(Direction.NORTH);
        player1.getLaserCannon().fire(players);
        assertEquals(0, player1.getDamage());
        assertEquals(0, player2.getDamage());
        assertEquals(0, player3.getDamage());
        assertEquals(0, player4.getDamage());
        assertEquals(1, player5.getDamage());

        player1.setDirection(Direction.WEST);
        player1.getLaserCannon().fire(players);
        assertEquals(0, player1.getDamage());
        assertEquals(1, player2.getDamage());
        assertEquals(0, player3.getDamage());
        assertEquals(0, player4.getDamage());
        assertEquals(1, player5.getDamage());

        player1.setDirection(Direction.SOUTH);
        player1.getLaserCannon().fire(players);
        assertEquals(0, player1.getDamage());
        assertEquals(1, player2.getDamage());
        assertEquals(1, player3.getDamage());
        assertEquals(0, player4.getDamage());
        assertEquals(1, player5.getDamage());

        player1.setDirection(Direction.EAST);
        player1.getLaserCannon().fire(players);
        assertEquals(0, player1.getDamage());
        assertEquals(1, player2.getDamage());
        assertEquals(1, player3.getDamage());
        assertEquals(1, player4.getDamage());
        assertEquals(1, player5.getDamage());
    }
}
