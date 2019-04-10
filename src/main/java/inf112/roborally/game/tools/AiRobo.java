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
        while (!robo.getRegisters().isFull()) {
            ArrayList<ProgramCard> smartMoves = smartMove(robo, board);
            if (smartMoves.contains(robo.getHand().getCard(0))
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

    private static ArrayList<ProgramCard> smartMove(Player robo, Board board) {
        ArrayList<ProgramCard> notTheSmartestCardChoices = new ArrayList<>();
        for (int i = 0; i < robo.getHand().size(); i++) {
            ProgramCard card = robo.getHand().getCard(i);

            if (!safeToMoveInDirection(robo, card, board)) {
                notTheSmartestCardChoices.add(card);
            }
        }

        return notTheSmartestCardChoices;
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
        int randomNumberToCheckForPowerDown = (int) (Math.random() * 50) + 1;
        System.out.println("Random number: " + randomNumberToCheckForPowerDown + " and " + robo.getName() + "'s damage: " + robo.getDamage());
        return randomNumberToCheckForPowerDown > 40 || robo.getDamage() > 8;
    }

}
