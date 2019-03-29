package inf112.roborally.game.board;

import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.objects.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class BoardLogicTest {
    BoardLogic boardLogic;
    ArrayList<Player> players;
    Player player1;
    Player player2;

    @Before
    public void setup() {
        player1 = new Player(0, 0, 1);
        player1.playerState = PlayerState.OPERATIONAL;
        player2 = new Player(1, 1, 1);
        player2.playerState = PlayerState.OPERATIONAL;
        players = new ArrayList<>();
        players.add(player2);
        players.add(player1);
        boardLogic = new BoardLogic(players);
    }

    @Test
    public void initialStateIsBETWEEN_ROUNDS() {
        assertEquals(GameState.BETWEEN_ROUNDS, boardLogic.getState());
    }

    @Test
    public void stateAfterUpdateIsPICKING_CARDS() {
        boardLogic.executeLogic();
        assertEquals(GameState.PICKING_CARDS, boardLogic.getState());
    }

    @Test
    public void doBeforeRoundDealsCorrectAmountOfCards() {
        player2.playerState = PlayerState.DESTROYED;
        boardLogic.doBeforeRound();
        assertEquals(9, player1.getCardsInHand().size());
        assertEquals(0, player2.getCardsInHand().size());
    }

    /**
     * Loops through the game loop making sure the game state is correct at all times
     */
    @Test
    public void sanityTest() {
        assertEquals(GameState.BETWEEN_ROUNDS, boardLogic.getState());
        boardLogic.executeLogic();

        boardLogic.executeLogic();
        assertEquals(GameState.PICKING_CARDS, boardLogic.getState());

        for (Player player : players) {
            while (!player.getRegisters().isFull())
                player.getRegisters().placeCard(0);
            player.playerState = PlayerState.READY;
        }

        for (int i = 0; i < 5; i ++) {
            boardLogic.executeLogic();
            assertEquals(GameState.ROUND, boardLogic.getState());
            for (Player player : players) {
                assertEquals(player.playerState, PlayerState.OPERATIONAL);
            }
            boardLogic.executeLogic();
            assertEquals(GameState.BOARD_MOVES, boardLogic.getState());
        }

        boardLogic.executeLogic();
        assertEquals(GameState.BETWEEN_ROUNDS, boardLogic.getState());

        boardLogic.executeLogic();
        assertEquals(GameState.PICKING_CARDS, boardLogic.getState());
    }

}
