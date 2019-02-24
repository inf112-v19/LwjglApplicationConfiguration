package inf112.roborally.game;

import inf112.roborally.game.objects.Player;
import inf112.roborally.game.world.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class GameLogic {

    public static boolean canEditCards = false; // Public boolean to let players know if the can edit cards or not?

    private Hud hud;
    private Board board;

    private Stack<ProgramCard> stackOfProgramCards;
    private ArrayList<Player> players;
    private Stack<ProgramCard> returnedProgramCards;

    public GameLogic(Board board, Hud hud){
        stackOfProgramCards = ProgramCard.makeStack();
        returnedProgramCards = new Stack<>();
        this.players = board.getPlayers();
        this.board = board;
        this.hud = hud;

        //TODO: Player choosing which direction to face needs to happen when the game initially starts.

        doBeforeRound();
    }




    public void doBeforeRound(){
        for(Player currentPlayer : players) {
                //Retrieve cards from last round
                retrieveCardsFromPlayer(currentPlayer);

                //Receive new cards
                giveCardsToPlayer(currentPlayer);
        }



        // Show cards
        // Choose cards to use for the round
        // Validate the choice



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
     * @param player which player to give program cards to.
     */
    private void giveCardsToPlayer(Player player) {
        if (player.getNumberOfCardsInHand() == 0) {
            for(int i = 0; i < player.getCardLimit(); i++) {
                if (stackOfProgramCards.isEmpty()){ // in case the game drags on and we run out of cards - Morten
                    reshuffleDeck();
                }
                player.receiveNewCard(stackOfProgramCards.pop());
            }
        } else {
            System.out.println("Error: " + player.getName() + " hasn't given away his/her cards from last round!");
            // ALL REGISTERS MIGHT BE LOCKED - Morten
        }
    }

    private void retrieveCardsFromPlayer(Player player){
        ArrayList<ProgramCard> playerCards = player.returnCardsToHand();
        while (!playerCards.isEmpty())
            returnedProgramCards.push(playerCards.remove(0));
    }

    /**
     * If the stack of cards run out of cards we need to reshuffle all the returned cards and deal them out instead
     * Move this to ProgramCard maybe..
     */
    private void reshuffleDeck(){
        while (!returnedProgramCards.empty())
            stackOfProgramCards.push(returnedProgramCards.pop());
        Collections.shuffle(stackOfProgramCards);
    }

}
