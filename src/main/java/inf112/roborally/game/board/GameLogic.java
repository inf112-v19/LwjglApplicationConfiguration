package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.gui.CardsInHandDisplay;
import inf112.roborally.game.objects.Player;

import java.util.*;


public class GameLogic {

    private boolean roundOver;
    private int phase;
    private Board board;
    private GameState state;
    private RoboRallyGame game;

    private Stack<ProgramCard> stackOfProgramCards;
    private ArrayList<Player> players;
    private Stack<ProgramCard> returnedProgramCards;
    private Player player1;

    private final CardsInHandDisplay cardsInHandDisplay;

    public GameLogic(Board board, CardsInHandDisplay cardsInHandDisplay, RoboRallyGame game) {
        state = GameState.PREROUND;
        this.game = game;
        stackOfProgramCards = ProgramCard.makeProgramCardDeck();
        returnedProgramCards = new Stack<>();
        this.players = board.getPlayers();
        player1 = players.get(0);
        this.board = board;
        this.cardsInHandDisplay = cardsInHandDisplay;

        roundOver = true;
        phase = 0;

        update();
        //TODO: Player choosing which direction to face needs to happen when the game initially starts.
    }

    /**
     * Retrieve cards from last round
     * Receive new cards
     */
    public void doBeforeRound() {
        // todo: check if a player has won
        retrieveCardsFromPlayer(player1);
        board.cleanBoard();
        giveCardsToPlayer(player1);
        cardsInHandDisplay.updateCardsInHandVisually();
        state = GameState.PICKING_CARDS;
    }

    public void update() {
        handleInput();
        board.updatePlayers();

        switch (state) {
            case PREROUND:
                System.out.println("set up before round");
                doBeforeRound();
                System.out.println("player choosing cards");
                for(Player player : players) {
                    player.playerState = PlayerState.PICKING_CARDS;
                }
                break;
            case PICKING_CARDS:
                if (playerReady(player1)) {
                    state = GameState.ROUND;
                }
                break;
            case ROUND:
                if (phase < 5) {

                    System.out.println("executing phase " + phase);
                    player1.executeCard(player1.getRegisters().getCard(phase));
                    checkIfAnyPlayersWon();
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    phase++;
                    state = GameState.BOARDMOVES;
                }
                else {
                    phase = 0;
                    state = GameState.PREROUND;
                    System.out.println("round over");
                }
                break;
            case BOARDMOVES:
                board.boardMoves();
                state = GameState.ROUND;
                break;
        }

    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            Gdx.app.exit();
            game.setScreen(game.settingsScreen);

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player1.executeCard(new ProgramCard(Rotate.NONE, 1, 0, ""));
            board.boardMoves();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player1.executeCard(new ProgramCard(Rotate.UTURN, 0, 0, ""));
            board.boardMoves();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player1.executeCard(new ProgramCard(Rotate.RIGHT, 0, 0, ""));
            board.boardMoves();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player1.executeCard(new ProgramCard(Rotate.LEFT, 0, 0, ""));
            board.boardMoves();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            player1.executeCard(new ProgramCard(Rotate.NONE, 0, 0, ""));
            board.boardMoves();
        }


        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            players.get(0).getRegisters().returnCards(players.get(0).getCardsInHand());
            // messy but it works:
            ((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getHud().getCardsInHandDisplay().
                    updateCardsInHandVisually();
        }
    }

    private void checkIfAnyPlayersWon() {
        for (Player pl : players) {
            if (pl.thisPlayerHasWon()) {
                System.out.printf("%s just won the game by collecting all the flags!!%n", pl.getName());
                // Might not be necessary to exit the game when it's finished
                Gdx.app.exit();
            }
        }
    }

    public boolean playerReady(Player player) {
        return player.getRegisters().isFull() &&
                player.playerState == PlayerState.READY;
    }

    public boolean playersReady() {
        for (Player player : players) {
            if (!player.getRegisters().isFull()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gives the player as many cards as allowed from the stack of program cards.*
     *
     * @param player which player to give program cards to.
     */
    private void giveCardsToPlayer(Player player) {
        for (int i = 0; i < player.getCardLimit(); i++) {
            if (stackOfProgramCards.isEmpty()) { // in case the game drags on and we run out of cards - Morten
                reshuffleDeck();
            }
            player.receiveCard(stackOfProgramCards.pop());
        }
    }

    private void retrieveCardsFromPlayer(Player player) {
        ArrayList<ProgramCard> playerCards = player.returnCards();
        while (!playerCards.isEmpty()) {
            returnedProgramCards.push(playerCards.remove(0));
        }
    }

    /**
     * If the stack of cards run out of cards we need to reshuffle all the returned cards and deal them out instead
     * Move this to ProgramCard maybe..
     */
    private void reshuffleDeck() {
        while (!returnedProgramCards.empty())
            stackOfProgramCards.push(returnedProgramCards.pop());
        Collections.shuffle(stackOfProgramCards);
    }

    public GameState getState(){
        return state;
    }
}
