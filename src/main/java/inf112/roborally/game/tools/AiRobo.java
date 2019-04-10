package inf112.roborally.game.tools;

import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;

public class AiRobo {

    public static void makeDecisionsForRobos(ArrayList<Player> aiRobos) {
        for (Player robo : aiRobos) {
            makeDecisions(robo);
        }
    }

    private static void makeDecisions(Player robo) {
        if (robo.outOfLives()) return;

        moveDummy(robo);

        robo.wantsToPowerDown = wantsToPowerDown(robo);
        robo.setPlayerState(PlayerState.READY);
    }

    private static void moveDummy(Player robo) {
        if (robo.getRegisters().isFull()) return;
        Player dummy = robo.getDummy();
        Player successDummy = robo.getDummy();
        for (int i = 0; i < robo.getHand().size(); i++) {
            ProgramCard card = robo.getHand().getCard(i);
            dummy.rotate(card.getRotate());
            dummy.move(card.getMoveDistance());
            if (dummy.isDestroyed()) {
                dummy = successDummy.getDummy();
            } else {
                robo.getRegisters().placeCard(i);
                successDummy = dummy.getDummy();
            }
        }
        while (!robo.getRegisters().isFull()) {
            robo.getRegisters().placeCard(0);
        }
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
