package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.player.ProgramCard;

import java.util.ArrayList;

import static inf112.roborally.game.enums.GameState.BOARD_MOVES;
import static inf112.roborally.game.enums.GameState.GAME_OVER;

public class MultiplayerLogic extends BoardLogic implements Runnable {
    private final Hud hud;

    private Board board;
    private RoboRallyGame game;
    private Player thisPlayer;

    public MultiplayerLogic(Board board, Hud hud, RoboRallyGame game) {
        super(board.getPlayers());
        this.game = game;
        this.board = board;
        this.hud = hud;
        thisPlayer = board.getThisPlayer();

    }

    @Override
    public void run() {
        while (state != GameState.GAME_OVER) update();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                endGame();
            }
        });
    }

    public void update() {
        updatePlayers();
        executeLogic();
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            game.setScreen(game.settingsScreen);
        }
    }

    @Override
    public void doBeforeRound() {
//        super.doBeforeRound();
        System.out.println("Set up before multiplayerround");
        cleanBoard();
        respawnRobots();
        powerUpRobots();
        powerDownRobots();

        // Return the cards from this player only
        // Adds the retrieved cards into the stack returnedProgramCards in
        // the superclass BoardLogic
        retrieveCardsFromPlayer(thisPlayer);


        for(Player player : players) {
            if(!player.isPoweredDown() && !player.equals(thisPlayer)) {
                player.getHand().removeAllCards();
            }
        }

        // Get new cards from the server
        game.client.sendMessage("REQUEST_CARDS " + thisPlayer.getCardLimit() + " " + thisPlayer.getName());



        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                hud.updateCards();
                hud.resetPowerDown();
                hud.setButtonTouchable(true);
                hud.getPlayerStatusDisplay().clearCards();
            }
        });
    }

    @Override
    protected void boardMoves() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                board.boardMoves();
            }
        });
        super.boardMoves();
    }

    @Override
    protected void endGame() {
        game.endGameScreen.addWinner(checkIfAPlayerHasWon());
        game.setScreen(game.endGameScreen);
    }

    @Override
    protected void cleanBoard() {
        super.cleanBoard();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                board.cleanUp();
            }
        });
    }

    @Override
    protected void doPhase() {
        if (phase < 5) {
            hud.getPlayerStatusDisplay().clearCards();
            hud.getPlayerStatusDisplay().addCards(phase);
        }
        super.doPhase();
    }

    @Override
    protected Player checkIfAPlayerHasWon() {
        for(Player player : players) {
            if (player.hasWon()) {
                System.out.printf("%s just won the game by collecting all the flags!!%n", player.getName());
                state = GAME_OVER;
                return player;
            }
        }
        state = BOARD_MOVES;
        return null;
    }

}
