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

        while (!robo.getRegisters().isFull()) {
            ArrayList<ProgramCard> smartMoves = smartMove(robo, board);
            if (smartMoves.contains(robo.getHand().getCard(0)) && robo.getHand().size() > 1) {
                robo.getRegisters().placeCard(1);
            } else {
                robo.getRegisters().placeCard(0);
            }
        }
        robo.wantsToPowerDown = robo.getDamage() > 5;
        robo.setPlayerState(PlayerState.READY);
    }

    private static ArrayList<ProgramCard> smartMove(Player robo, Board board) {
        ArrayList<ProgramCard> smartestCardChoices = new ArrayList<>();
        for (int i = 0; i < robo.getHand().size(); i++) {
            ProgramCard card = robo.getHand().getCard(i);
            System.out.println(robo.getX() + " " + card.getMoveDistance());

            /*
             *  If the aiRobos are positioned near the end of the board,
             *  a smart move will be not to go a certain amount of steps
             *  that will get the aiRobo out of the map and loose a life.
             */
            if ((robo.getX() - card.getMoveDistance() < 0
                    && robo.getDirection().equals(Direction.WEST)) ||
                    (robo.getX() - card.getMoveDistance() < 0
                            && robo.getDirection().equals(Direction.EAST)) ||
                    (robo.getY() - card.getMoveDistance() < 0
                            && robo.getDirection().equals(Direction.NORTH)) ||
                    (robo.getY() - card.getMoveDistance() < 0
                            && robo.getDirection().equals(Direction.SOUTH)) ||
                    (robo.getX() + card.getMoveDistance() > board.getWidth()
                            && robo.getDirection().equals(Direction.EAST)) ||
                    (robo.getX() + card.getMoveDistance() > board.getWidth()
                            && robo.getDirection().equals(Direction.WEST)) ||
                    (robo.getY() + card.getMoveDistance() > board.getHeight()
                            && robo.getDirection().equals(Direction.SOUTH)) ||
                    (robo.getY() + card.getMoveDistance() > board.getHeight()
                            && robo.getDirection().equals(Direction.NORTH))) {
                smartestCardChoices.add(card);
            }
        }

        System.out.println(robo.getX() + " " + robo.getY());
        return smartestCardChoices;
    }
}
