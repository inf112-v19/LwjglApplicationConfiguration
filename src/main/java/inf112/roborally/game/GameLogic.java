package inf112.roborally.game;

import inf112.roborally.game.objects.Player;
import inf112.roborally.game.world.Board;

import java.util.ArrayList;
import java.util.Stack;


public class GameLogic {

    private Hud hud;
    private Board board;

    private Stack<ProgramCard> stackOfProgramCards;
    private ArrayList<Player> players;

    public GameLogic(Board board, Hud hud){
        stackOfProgramCards = ProgramCard.makeStack();
        this.players = board.getPlayers();
        this.board = board;
        this.hud = hud;
    }


    //Player choosing which direction to face needs to happen when the game initially starts.


    public void doBeforeRound(){
        //Receive new cards
        for(Player currentPlayer : players) {
                giveCardsToPlayer(currentPlayer);
        }



        // Show cards
        // Choose cards to use for the round
        // Validate the choice



    }


    /*
     * Let the player(s) choose which direction to face.
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
        if (player.getCardsInHand().size() == 0) {
            for(int i = 0; i < player.getCardLimit(); i++){
                player.receiveNewCard(stackOfProgramCards.pop());
            }
        } else {
            System.out.println("Error: " + player.getName() + " hasn't given away his/her cards from last round!");
        }
    }


}
