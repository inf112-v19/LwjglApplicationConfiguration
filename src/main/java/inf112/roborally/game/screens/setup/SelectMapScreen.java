package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.SetupState;
import inf112.roborally.game.tools.AssMan;

public class SelectMapScreen extends SelectScreen {

    public SelectMapScreen(final RoboRallyGame game) {
        super(game, SetupState.PICKINGMAP, AssMan.getMapChoices().length);
    }

    @Override
    public void completeChoice() {
        System.out.println("SelectMapScreen completeChoice selected");
        System.out.println("Selected the map at index " + choiceIndex);
        dispose();
        System.out.println("Shutting down");
        Gdx.app.exit();
    }
}
