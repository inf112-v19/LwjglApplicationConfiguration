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
        mapChoiceIndex = choiceIndex;
        game.setScreen(new PlaceFlagsScreen(game, AssMan.getMapChoices()[mapChoiceIndex], mapChoiceIndex, skinChoiceIndex));
        dispose();
    }
}
