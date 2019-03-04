package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.gui.CardsInHandDisplay;
import inf112.roborally.game.objects.Player;

import java.util.*;


public class GameLogic {

    boolean roundOver;
    int phase;
    private Board board;
    private GameState state;

    private Stack<ProgramCard> stackOfProgramCards;
    private ArrayList<Player> players;
    private Stack<ProgramCard> returnedProgramCards;
    private Player player1;

    private final CardsInHandDisplay cardsInHandDisplay;

    public GameLogic(Board board, CardsInHandDisplay cardsInHandDisplay) {
        state = GameState.PREROUND;
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


    public void doBeforeRound() {

            //Retrieve cards from last round
            retrieveCardsFromPlayer(player1);

            //Receive new cards
            giveCardsToPlayer(player1);
            cardsInHandDisplay.updateCardsInHandVisually();


        // Show cards
        // Choose cards to use for the round
        // Validate the choice

        state = GameState.PICKING_CARDS;
    }

    public void update() {
        switch (state) {
            case PREROUND:
                System.out.println("set up before round");
                doBeforeRound();
                System.out.println("player choosing cards");
                break;
            case PICKING_CARDS:
                if (playerReady(player1)) {
                    state = GameState.ROUND;
                }
                break;
            case ROUND:
                if(phase < 5) {

                    System.out.println("executing phase " + phase);
                    board.executeCard(player1, player1.getRegisters().getCardInRegister(phase));
                    checkIfAnyPlayersWon();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    phase++;
                    state = GameState.BOARDMOVES;
                } else {
                    phase = 0;
                    state = GameState.PREROUND;
                    System.out.println("round over");
                }
                break;
            case BOARDMOVES:
                board.updateBoard();
                state = GameState.ROUND;
                break;
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

    public boolean playerReady(Player player){
        return player.getRegisters().registerIsFull();
    }

    public boolean playersReady() {
        for (Player player : players) {
            if (!player.getRegisters().registerIsFull()) {
                return false;
            }
        }
        return true;
    }

    /*
     * Let the players choose which direction to face.
     *
     * -- Give out cards to the players. (done)
     * Choose which cards to use.
     * Validate the choice.
     * Lock the program cards.
     * Set a 30 second timer once there is only one person left that hasn't locked
     *
     * Start the round once all players have their cards locked.
     *
     * Round:
     * Turn around the first card (all players)
     * Highest priority goes first.
     * Once all cards in a phase has been used:
     *  Move belts
     *  Activate lasers
     *  Check for damage
     *  Move backup
     *  Check if someone is standing on their next flag at the end of the phase.
     *
     * After a round:
     *  Repair damage
     *  Be able to choose powerdown
     *  Hand out options-cards
     *  Take back all cards from players
     *
     *
     */

    /**
     * Gives the player as many cards as allowed from the stack of program cards.
     *
     * @param player which player to give program cards to.
     */
    private void giveCardsToPlayer(Player player) {
        for (int i = 0; i < player.getRegisters().getCardLimit(player); i++) {
            if (stackOfProgramCards.isEmpty()) { // in case the game drags on and we run out of cards - Morten
                reshuffleDeck();
            }
            player.receiveCard(stackOfProgramCards.pop());
        }
    }

    private void retrieveCardsFromPlayer(Player player) {
        ArrayList<ProgramCard> playerCards = player.returnCards();
        while (!playerCards.isEmpty())
            returnedProgramCards.push(playerCards.remove(0));
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

}
