package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.SetupState;
import inf112.roborally.game.tools.AssMan;

public class SelectMapScreen extends SelectScreen {
    private int robotSkinIndex;

    public SelectMapScreen(final RoboRallyGame game) {
        super(game, SetupState.PICKINGMAP, AssMan.getMapChoices().length);
        // set the index for the robot choice now before it gets changed in this screen
        robotSkinIndex = choiceIndex;
    }

    @Override
    public void completeChoice() {
        System.out.println("SelectMapScreen completeChoice selected");
        game.setScreen(new PlaceFlagsScreen(game, AssMan.getMapChoices()[choiceIndex], choiceIndex, robotSkinIndex));
        dispose();
//        System.out.println("Shutting down");
//        Gdx.app.exit();
    }
}
