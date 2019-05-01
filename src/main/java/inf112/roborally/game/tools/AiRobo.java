package inf112.roborally.game.tools;

import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;
import java.util.List;

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
                                                          int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++)
                System.out.print(data.get(j)+" ");
            System.out.println("");
            return null;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data.add(index, arr.get(i));
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
        return arr;
    }

    private static void smartAI(Player robo) {
        int r = robo.getRegisters().getNumUnlockedRegisters();
        ArrayList<ProgramCard> arr = robo.getHand().getCardsInHand();
        int n = arr.size();
        ArrayList<ProgramCard> data = new ArrayList<>();
        ArrayList<ProgramCard> allPossibleCombinations = combinationUtil(arr, data, 0, n-1, 0, r);

        assert allPossibleCombinations != null;
        for(ProgramCard i: allPossibleCombinations) {
            System.out.println(i);
        }
    }

    private static void moveTestPilot(Player robo) {
        smartAI(robo);
        if (robo.getRegisters().isFull()) return;

        Player testPilot = robo.createTestPilot();
        Player successfulTestPilot = robo.createTestPilot();

        for (int i = 0; i < robo.getHand().size(); i++) {
            ProgramCard card = robo.getHand().getCard(i);
            testPilot.rotate(card.getRotate());
            testPilot.move(card.getMoveDistance());
            if (!testPilot.isDestroyed()
                    && shorterDistToFlag(successfulTestPilot, testPilot)) {
                robo.getRegisters().placeCard(i);
                successfulTestPilot = testPilot.createTestPilot();
            } else {
                testPilot = successfulTestPilot.createTestPilot();
            }
        }

        for (int i = 0; i < robo.getHand().size(); i++) {
            ProgramCard card = robo.getHand().getCard(i);
            testPilot.rotate(card.getRotate());
            testPilot.move(card.getMoveDistance());
            if (!testPilot.isDestroyed()) {
                robo.getRegisters().placeCard(i);
                successfulTestPilot = testPilot.createTestPilot();
            } else {
                testPilot = successfulTestPilot.createTestPilot();
            }
        }

        while (!robo.getRegisters().isFull())
            robo.getRegisters().placeCard(0);
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
