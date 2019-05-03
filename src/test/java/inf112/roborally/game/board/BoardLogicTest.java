package inf112.roborally.game.board;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;
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
        player1.setPlayerState(PlayerState.OPERATIONAL);
        player2 = new Player(1, 1, 1);
        player2.setPlayerState(PlayerState.OPERATIONAL);
        players = new ArrayList<>();
        players.add(player2);
        players.add(player1);
        boardLogic = new BoardLogic(players, new RoboRallyGame());
    }

    @Test
    public void initialStateIsBETWEEN_ROUNDS() {
        assertEquals(GameState.BETWEEN_ROUNDS, boardLogic.getState());
    }

    @Test
    public void stateAfterExecuteLogicIsPICKING_CARDS() {
        boardLogic.executeLogic();
        assertEquals(GameState.PICKING_CARDS, boardLogic.getState());
    }

    @Test
    public void doBeforeRoundRespawnsBeforeDealingCards() {
        player2.setPlayerState(PlayerState.DESTROYED);
        boardLogic.doBeforeRound();
        assertEquals(9, player1.getHand().size());
        assertEquals(9, player2.getHand().size());
    }

    /**
     * Loops through the game loop making sure the game state is correct at all times
     */
    @Test
    public void sanityTest() {
        int nRounds = 2;
        for (int round = 0; round < nRounds; round++) {
            assertEquals(GameState.BETWEEN_ROUNDS, boardLogic.getState());

            boardLogic.executeLogic();
            assertEquals(GameState.PICKING_CARDS, boardLogic.getState());

            for (Player player : players) {
                while (!player.getRegisters().isFull())
                    player.getRegisters().placeCard(0);
                player.setPlayerState(PlayerState.READY);
            }

            for(int phase = 0; phase < 5; phase++) {
                boardLogic.executeLogic();
                assertEquals(GameState.ROUND, boardLogic.getState());
                boardLogic.executeLogic();
                boardLogic.executeLogic();
                boardLogic.executeLogic();
                boardLogic.executeLogic();
                assertEquals(GameState.BOARD_MOVES, boardLogic.getState());
            }
            boardLogic.executeLogic();
            boardLogic.executeLogic();

        }
    }
}
