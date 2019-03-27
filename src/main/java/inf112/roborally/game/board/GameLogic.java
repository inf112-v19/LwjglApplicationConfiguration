package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.objects.Player;

import java.util.*;


public class GameLogic {

    private int phase;
    private Board board;
    private GameState state;
    private RoboRallyGame game;

    private Stack<ProgramCard> stackOfProgramCards;
    private ArrayList<Player> players;
    private Stack<ProgramCard> returnedProgramCards;
    private Player player1;

    private final Hud hud;


    public GameLogic(Board board, Hud hud, RoboRallyGame game) {
        state = GameState.BETWEEN_ROUNDS;
        this.game = game;
        stackOfProgramCards = ProgramCard.makeProgramCardDeck();
        returnedProgramCards = new Stack<>();
        this.players = board.getPlayers();
        player1 = players.get(0);
        this.board = board;
        this.hud = hud;

        phase = 0;

        update();
        //TODO: Player choosing which direction to face needs to happen when the game initially starts.
    }

    public void update() {
        handleInput();
        updatePlayers();

        switch (state) {
            case BETWEEN_ROUNDS:
                doBeforeRound();
                break;
            case PICKING_CARDS:
                checkIfReady();
                break;
            case ROUND:
                handlePhase();
                break;
            case BOARD_MOVES:
                board.boardMoves();
                state = GameState.ROUND;
                break;
            case GAME_OVER:
                game.setScreen(game.endGameScreen);
        }

    }

    public void doBeforeRound() {
        System.out.println("set up before round");
        board.cleanUp();
        powerUpRobots();
        powerDownRobots();
        if (!player1.isPoweredDown()) {
            retrieveCardsFromPlayer(player1);
        }
        giveCardsToPlayer(player1);

        hud.clearAllCards();
        hud.updateCards();

        System.out.println("players choosing cards");
        state = GameState.PICKING_CARDS;
    }

    private void checkIfReady() {
        if (player1.isReady()) { // Player is ready if it has clicked submit or is powered down
            if (player1.playerState == PlayerState.READY) {
                player1.playerState = PlayerState.OPERATIONAL;
            }
            state = GameState.ROUND;
        }
    }

    private void handlePhase() {
        if (phase < 5) {
            System.out.println("executing phase " + phase);
            player1.getRegisters().executeCard(phase);
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            state = GameState.BOARD_MOVES;
            checkIfAPlayerHasWon();
            phase++;
        }
        else {
            phase = 0;
            state = GameState.BETWEEN_ROUNDS;
            System.out.println("round over");
        }
    }

    private void powerUpRobots() {
        for (Player player : players) {
            player.powerUp();
        }
    }

    private void powerDownRobots() {
        for (Player player : players) {
            player.powerDown();
        }
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            game.setScreen(game.settingsScreen);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            System.out.println("Switched to EndGame screen");
            game.endGameScreen.addWinner(player1);
            game.setScreen(game.endGameScreen);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            players.get(0).getRegisters().returnCards(players.get(0));
            hud.clearAllCards();
            hud.updateCards();
        }
    }

    private void checkIfAPlayerHasWon() {
        for (Player player : players) {
            if (player.hasWon()) {
                System.out.printf("%s just won the game by collecting all the flags!!%n", player.getName());
                state = GameState.GAME_OVER;
                game.endGameScreen.addWinner(player);
                break;
            }
        }
    }

    public void updatePlayers() {
        for (Player pl : players) {
            pl.update();
        }
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

    public GameState getState() {
        return state;
    }
}
