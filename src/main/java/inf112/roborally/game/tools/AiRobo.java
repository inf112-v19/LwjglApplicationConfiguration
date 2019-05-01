package inf112.roborally.game.tools;

import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;

public class AiRobo {

    public static void makeDecisionsForRobos(ArrayList<Player> aiRobos) {
        for (Player robo : aiRobos) {
            if (robo.outOfLives()) return;
            moveTestPilot(robo);
            robo.wantsToPowerDown = wantsToPowerDown(robo);
            robo.setPlayerState(PlayerState.READY);
        }
    }

    private static ArrayList<ProgramCard> combinationUtil(ArrayList<ProgramCard> arr, ArrayList<ProgramCard> data, int start,
                                                          int end, int index, int r) {
        if (index == r) {
            for (int j = 0; j < r; j++)
                System.out.print(data.get(j) + " ");
            System.out.println();
            return null;
        }
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data.add(index, arr.get(i));
            combinationUtil(arr, data, i + 1, end, index + 1, r);
        }
        return data;
    }

    private static ArrayList<ProgramCard> getAllPossibleCombinations(Player robo) {
        int r = robo.getRegisters().getNumUnlockedRegisters();
        ArrayList<ProgramCard> arr = robo.getHand().getCardsInHand();
        int n = arr.size();
        ArrayList<ProgramCard> data = new ArrayList<>();
        return combinationUtil(arr, data, 0, n - 1, 0, r);
    }

    private static void moveTestPilot(Player robo) {
        if (robo.getRegisters().isFull()) return;

        smartAI(robo);

        while (!robo.getRegisters().isFull())
            robo.getRegisters().placeCard(0);
    }

    private static void smartAI(Player robo) {
        Player successfulTestPilot = robo.createTestPilot();
        ArrayList<ProgramCard> list = getAllPossibleCombinations(robo);
        ArrayList<ProgramCard> bestList = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            Player testPilot = robo.createTestPilot();
            ArrayList<ProgramCard> l = new ArrayList<>();

            for (int k = 0; k < robo.getRegisters().getNumUnlockedRegisters(); k++) {
                ProgramCard card = list.get(k);
                testPilot.rotate(card.getRotate());
                testPilot.move(card.getMoveDistance());
                l.add(card);
            }
            if(shorterDistToFlag(successfulTestPilot, testPilot)) {
                successfulTestPilot = testPilot;
                bestList = l;
            }
        }
        if(shorterDistToFlag(robo, successfulTestPilot)) {

            for(int i = 0; i < bestList.size(); i++) {
                if(robo.getHand().getCard(i).equals(bestList.get(i))) {
                    robo.getRegisters().placeCard(i);
                }
            }

        }

    }

    private static boolean shorterDistToFlag(Player robo, Player testPilot) {
        return (Math.abs(robo.getTargetFlagPos().getX() - testPilot.getX())
                + Math.abs(robo.getTargetFlagPos().getY() - testPilot.getY())
                < Math.abs(robo.getTargetFlagPos().getX() - robo.getX())
                + Math.abs(robo.getTargetFlagPos().getY() - robo.getY()));
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
