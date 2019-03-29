package inf112.roborally.game.board;

import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.objects.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BoardInteractionTest {
    Board board;
    ArrayList<Player> players;
    Player player1;
    Player player2;

    @Before
    public void setup(){
        board = new TestBoard();
        player1 = new Player(0, 0, 1);
        player1.playerState = PlayerState.OPERATIONAL;
        player2 = new Player(1, 1, 1);
        player2.playerState = PlayerState.OPERATIONAL;
        players = new ArrayList<>();
        players.add(player2);
        players.add(player1);
    }

}
