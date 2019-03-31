package inf112.roborally.game.board;

import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.objects.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import static java.util.Collections.*;

@SuppressWarnings("Duplicates")
public class BoardLogic {

    protected int phase;
    protected GameState state;

    protected ArrayList<Player> players;
    private ArrayList<Player> airobots;

    protected Stack<ProgramCard> returnedProgramCards;
    protected Stack<ProgramCard> stackOfProgramCards;

    public BoardLogic(ArrayList<Player> players) {
        this.players = players;
        airobots = new ArrayList<>();
        for (int i = 1; i < players.size(); i++)
            airobots.add(players.get(i));

        phase = 0;
        state = GameState.BETWEEN_ROUNDS;
        stackOfProgramCards = ProgramCard.makeProgramCardDeck();
        returnedProgramCards = new Stack<>();
    }

    public void executeLogic() {
        switch (state) {
            case BETWEEN_ROUNDS:
                doBeforeRound();
                break;
            case PICKING_CARDS:
                checkIfReady();
                break;
            case ROUND:
                doPhase();
                break;
            case BOARD_MOVES:
                boardMoves();
                break;
            case GAME_OVER:
                endGame();
        }
    }

    protected void doBeforeRound() {
        System.out.println("set up before round");
        cleanBoard();
        powerUpRobots();
        powerDownRobots();

        for (Player player : players) {
            if (!player.isPoweredDown()) {
                retrieveCardsFromPlayer(player);
            }
            if (!player.outOfLives() && player.isOperational()) {
                giveCardsToPlayer(player);
            }
        }

        System.out.println("players choosing cards");
        state = GameState.PICKING_CARDS;
    }

    private void removeDeadRobots() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.playerState == PlayerState.GAME_OVER) {
                players.remove(player);
                airobots.remove(player);
                System.out.println(player.getName() + " was removed.");
            }
        }
    }

    private void powerUpRobots() {
        for (Player player : players) {
            player.powerUp();
        }
    }

    protected void powerDownRobots() {
        for (Player player : players) {
            player.powerDown();
        }
    }

    private boolean allPlayersReady() {
        for (Player player : players) {
            if (!player.isReady()) return false;
        }
        return true;
    }

    public void checkIfReady() {
        if (allPlayersReady()) {
            for (Player player : players) {
                if (player.playerState == PlayerState.READY) //true if submit button is pressed
                    player.playerState = PlayerState.OPERATIONAL;
            }
            state = GameState.ROUND;
        }
    }

    protected void doPhase() {
        removeDeadRobots();
        if (phase >= 5) {
            phase = 0;
            state = GameState.BETWEEN_ROUNDS;
            System.out.println("round over");
            return;
        }

        System.out.println("executing phase " + phase);

        aiRobotsChooseCards();
        // sort players after phase priority
        for (Player player : players) {
            player.setPhase(phase);
        }
        try {
            System.out.println(players.toString());
            Collections.sort(players);
        } catch (NullPointerException e) {
            throw new NullPointerException("AIRobots: "
                    + airobots.size()
                    + "\n Players: "
                    + players.size());
        }

        executeCards();

        if (!players.get(0).isDebuggingActive()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        checkIfAPlayerHasWon();
        phase++;
    }

    private void executeCards() {
        for (Player player : players) {
            player.getRegisters().executeCard(phase);
        }
    }

    protected void boardMoves() {
        state = GameState.ROUND;
    }


    protected void cleanBoard() {
        System.out.println("Cleaning board");
    }

    protected Player checkIfAPlayerHasWon() {
        if (players.size() == 1) {
            System.out.printf("%s just won the game by outliving their opponents!!%n", players.get(0).getName());
            state = GameState.GAME_OVER;
            return players.get(0);
        }

        for (Player player : players) {
            if (player.hasWon()) {
                System.out.printf("%s just won the game by collecting all the flags!!%n", player.getName());
                state = GameState.GAME_OVER;
                return player;
            }
        }
        state = GameState.BOARD_MOVES;
        return null;
    }

    protected void endGame() {
        System.out.println("Game Over! " + checkIfAPlayerHasWon().getName() + " won the game.");
    }


    protected void updatePlayers() {
        for (Player player : players) {
            player.update();
        }
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
            player.getHand().receiveCard(stackOfProgramCards.pop());
        }
    }

    private void retrieveCardsFromPlayer(Player player) {
        ArrayList<ProgramCard> playerCards = player.returnCards();
        while (!playerCards.isEmpty()) {
            returnedProgramCards.push(playerCards.remove(0));
        }
    }

    private void aiRobotsChooseCards() {
        for (Player ai : airobots) {
            if (ai.outOfLives()) continue;
            while (!ai.getRegisters().isFull()) {
                ai.getRegisters().placeCard(0);
            }
            System.out.println(ai.getHand().size());
        }
    }

    /**
     * If the stack of cards run out of cards we need to reshuffle all the returned cards and deal them out instead
     * Move this to ProgramCard maybe..
     */
    private void reshuffleDeck() {
        while (!returnedProgramCards.empty())
            stackOfProgramCards.push(returnedProgramCards.pop());
        shuffle(stackOfProgramCards);
    }

    public GameState getState() {
        return state;
    }
}
