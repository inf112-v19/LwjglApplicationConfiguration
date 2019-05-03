package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.player.ProgramCard;

import java.util.ArrayList;

import static inf112.roborally.game.enums.GameState.*;

public class MultiplayerLogic extends BoardLogic implements Runnable {
    private final Hud hud;

    private Board board;
    private RoboRallyGame game;
    private Player thisPlayer;

    public MultiplayerLogic(Board board, Hud hud, RoboRallyGame game) {
        super(board.getPlayers(), game);
        this.game = game;
        this.board = board;
        this.hud = hud;
        thisPlayer = board.getThisPlayer();

    }


    public void update() {
        updatePlayers();
        executeLogic();
    }

    @Override
    public void executeLogic() {
        switch (state) {
            case BETWEEN_ROUNDS:
                doBeforeRound();
                break;
            case PICKING_CARDS:
                if (thisPlayer.isReady() || thisPlayer.isPoweredDown()) {
                    state = WAITINGFORONLINEPLAYERS;
                }
                break;
            case WAITINGFORONLINEPLAYERS:
                // Picking cards or waiting for other players
                break;
            case ROUND:
                doPhase();
                break;
            case BOARD_MOVES:
                boardMoves();
                break;
            case GAME_OVER:
                endGame();
                break;
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
        removeDeadRobots();

        // Return the cards from this player only
        // Adds the retrieved cards into the stack returnedProgramCards in
        // the superclass BoardLogic
        retrieveCardsFromPlayer(thisPlayer);


        for (Player player : players) {
            if (!player.equals(thisPlayer)) {
                player.getHand().removeAllCards();
                player.getRegisters().returnCards();
                System.out.println("Cleared the register for player " + player.getName());
            }
        }

        // Request new cards from the server
        if (!thisPlayer.isPoweredDown()) {
            game.client.sendMessage("REQUEST_CARDS " + thisPlayer.getCardLimit() + " " + thisPlayer.getName());
        }
        System.out.println("Players choosing cards. Players alive: " + players.size());
        state = PICKING_CARDS;

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                hud.updateCardButtons();
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
    public void run() {
        while (state != GameState.GAME_OVER) update();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                endGame();
            }
        });
    }

    // To be used when receiving only one card
    public void receiveCardsFromServer(String name, ProgramCard card) {
        for (Player player : board.players) {
            if (player.getName().equals(name)) {
                if (name.equals(game.playerName)) {
                    // add to hand of this player:
                    player.getHand().getCardsInHand().add(card);
                } else {
                    // add the hand of another player
                    player.getHand().getCardsInHand().add(card);
                }
                return;
            }
        }
    }

    // To be used when receiving multiple cards at the time
    public void receiveCardsFromServer(String name, ArrayList<ProgramCard> allCards) {
        System.out.println("Inside receiveCardsFromServer");
        System.out.println("Parameter name: " + name);
        for (Player player : board.players) {
            if (player.getName().equals(name)) {
                if (name.equals(game.playerName)) {
                    // add to hand of this player:
                    for (ProgramCard card : allCards) {
                        player.getHand().getCardsInHand().add(card);
                    }
                } else {
                    // place in register of other player:
                    for (ProgramCard card : allCards) {
                        player.getRegisters().placeCard(card);
                        System.out.printf("Added the card %s to player %s´s register%n", card.toString(), player.getName());
                    }
                }
                return;
            }
        }
    }

    @Override
    public void setToRound() {
        for (Player player : players) {
            if (player.getPlayerState() == PlayerState.READY) //true if submit button is pressed
                player.setPlayerState(PlayerState.OPERATIONAL);
        }
        state = ROUND;
    }
}
