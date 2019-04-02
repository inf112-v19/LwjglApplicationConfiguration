package inf112.roborally.game.tools;

import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;

public class AiRobo {

    public static void makeDecisions(Player robo) {
        if (robo.outOfLives()) return;

        while(!robo.getRegisters().isFull()) robo.getRegisters().placeCard(0);
        robo.wantsToPowerDown = robo.getDamage() > 5;
        robo.setPlayerState(PlayerState.READY);
    }

    public static void makeDecisionsForRobos(ArrayList<Player> aiRobos){
        for (Player robo : aiRobos){
            makeDecisions(robo);
        }
    }
}
