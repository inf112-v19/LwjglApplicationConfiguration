package inf112.roborally.game.tools;

import inf112.roborally.game.board.Board;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;

public class AiRobo {

    public static void makeDecisionsForRobos(ArrayList<Player> aiRobos, Board board) {
        for (Player robo : aiRobos) {
            makeDecisions(robo, board);
        }
    }

    private static void makeDecisions(Player robo, Board board) {
        if (robo.outOfLives()) return;

        int shuffleCounter = 0;

        ArrayList<ProgramCard> badMoves = new ArrayList<>();

        while (!robo.getRegisters().isFull()) {
            badMoves = badMoves(robo, board);

            if (badMoves.contains(robo.getHand().getCard(0))
                    && robo.getHand().size() > 1
                    && shuffleCounter < 2) {
                robo.getHand().shuffle();
                shuffleCounter++;
            } else {
                robo.getRegisters().placeCard(0);
                shuffleCounter = 0;
            }
        }
        robo.wantsToPowerDown = wantsToPowerDown(robo);
        robo.setPlayerState(PlayerState.READY);
    }

    private static ArrayList<ProgramCard> badMoves(Player robo, Board board) {
        ArrayList<ProgramCard> badMoves = new ArrayList<>();
        for (int i = 0; i < robo.getHand().size(); i++) {
            ProgramCard card = robo.getHand().getCard(i);

            if (!safeToMoveInDirection(robo, card, board)) {
                badMoves.add(card);
            }
        }

        return badMoves;
    }

    /*
     *  If the aiRobos are positioned near the end of the board,
     *  a smart move will be not to go a certain amount of steps
     *  that will get the aiRobo out of the map and loose a life.
     */
    private static boolean safeToMoveInDirection(Player robo, ProgramCard card, Board board) {
        return (robo.getX() - card.getMoveDistance() >= 0
                || !robo.getDirection().equals(Direction.WEST)) &&
                (robo.getX() - card.getMoveDistance() >= 0
                        || !robo.getDirection().equals(Direction.EAST)) &&
                (robo.getY() - card.getMoveDistance() >= 0
                        || !robo.getDirection().equals(Direction.NORTH)) &&
                (robo.getY() - card.getMoveDistance() >= 0
                        || !robo.getDirection().equals(Direction.SOUTH)) &&
                (robo.getX() + card.getMoveDistance() <= board.getWidth()
                        || !robo.getDirection().equals(Direction.EAST)) &&
                (robo.getX() + card.getMoveDistance() <= board.getWidth()
                        || !robo.getDirection().equals(Direction.WEST)) &&
                (robo.getY() + card.getMoveDistance() <= board.getHeight()
                        || !robo.getDirection().equals(Direction.SOUTH)) &&
                (robo.getY() + card.getMoveDistance() <= board.getHeight()
                        || !robo.getDirection().equals(Direction.NORTH));
    }

    private static boolean wantsToPowerDown(Player robo) {
        int randomNumber = (int) (Math.random() * 50 + 1);
        int aiRoboDamage = robo.getDamage();
        return (aiRoboDamage == 0 && randomNumber > 45) ||
                (aiRoboDamage == 1 && randomNumber > 40) ||
                (aiRoboDamage == 2 && randomNumber > 35) ||
                (aiRoboDamage == 3 && randomNumber > 30) ||
                (aiRoboDamage == 4 && randomNumber > 25) ||
                (aiRoboDamage == 5 && randomNumber > 20) ||
                (aiRoboDamage == 6 && randomNumber > 15) ||
                (aiRoboDamage == 7 && randomNumber > 10) ||
                (aiRoboDamage == 8 && randomNumber > 5) ||
                (aiRoboDamage == 9 && randomNumber > 0);
    }

}
