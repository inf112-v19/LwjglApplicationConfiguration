package inf112.roborally.game;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.gui.CardsInHandDisplay;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.world.Board;

import java.util.*;


public class GameLogic {

    boolean roundOver;
    int phase;

    private CardsInHandDisplay hud;
    private Board board;

    private Stack<ProgramCard> stackOfProgramCards;
    private ArrayList<Player> players;
    private Stack<ProgramCard> returnedProgramCards;

    private Player player1;

    public GameLogic(Board board, CardsInHandDisplay hud) {
        stackOfProgramCards = ProgramCard.makeProgramCardDeck();
        returnedProgramCards = new Stack<>();
        this.players = board.getPlayers();
        player1 = players.get(0);
        this.board = board;
        this.hud = hud;

        roundOver = true;
        phase = 0;

        update();

        //TODO: Player choosing which direction to face needs to happen when the game initially starts.
    }


    public void doBeforeRound() {
        phase = 0;

        for (Player currentPlayer : players) {
            //Retrieve cards from last round
            retrieveCardsFromPlayer(currentPlayer);

            //Receive new cards
            giveCardsToPlayer(currentPlayer);
        }

        // Show cards
        // Choose cards to use for the round
        // Validate the choice

        roundOver = false;
    }

    public void update() {
        if (roundOver) {
            System.out.println("set up before round");
            doBeforeRound();
            System.out.println("player chosing cards");
        } else if (!playerReady(player1)) {
            // choosing cards
        } else {
            if(phase < 5) {
                System.out.println("executing phase " + phase);
                board.executeCard(player1, player1.getRegisters().getCardInRegister(phase));
                board.updateBoard();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                phase++;
            } else {
                roundOver = true;
                System.out.println("round over");
            }
            hud.update(player1);


            // after phase is over :
        }
        for (Player pl : players) {
            if (pl.thisPlayerHasWon()) {
                System.out.printf("%s just won the game by collecting all the flags!!%n", pl.getName());
                // Might not be necessary to exit the game when it's finished
                Gdx.app.exit();
            }

            if(board.playerIsOnRepair(pl.getX(), pl.getY())) {
                player1.updateBackup();
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
        for (int i = 0; i < 9; i++) {
            if (stackOfProgramCards.isEmpty()) { // in case the game drags on and we run out of cards - Morten
                reshuffleDeck();
            }
            player.getRegisters().receiveCard(stackOfProgramCards.pop());
        }
    }

    private void retrieveCardsFromPlayer(Player player) {
        ArrayList<ProgramCard> playerCards = player.getRegisters().returnCards();
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
