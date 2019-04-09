package inf112.roborally.game.tools;

import inf112.roborally.game.board.Board;
import inf112.roborally.game.board.BoardLogic;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.player.PlayerHand;

import java.util.ArrayList;

public class AiRobo {

    public static void makeDecisionsForRobos(ArrayList<Player> aiRobos, Board board) {
        for (Player robo : aiRobos) {
            makeDecisions(robo, board);
        }
    }

    private static void makeDecisions(Player robo, Board board) {
        if (robo.outOfLives()) return;

        ArrayList<ProgramCard> smartMoves = smartMove(robo, board);
        while (!robo.getRegisters().isFull()) {
            if(smartMoves.contains(robo.getHand().getCard(0)) && robo.getHand().size()>1) {
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
            if (robo.getX() - card.getMoveDistance() <= 0 ||
                    robo.getY() - card.getMoveDistance() <= 0 ||
                    robo.getX() + card.getMoveDistance() > board.getWidth() ||
                    robo.getY() + card.getMoveDistance() > board.getHeight()) {
                smartestCardChoices.add(card);
            }
        }

        System.out.println(robo.getX() + " " + robo.getY());
        return smartestCardChoices;
    }
}
