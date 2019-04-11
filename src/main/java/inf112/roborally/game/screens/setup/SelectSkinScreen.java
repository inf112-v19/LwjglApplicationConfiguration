package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.SetupState;
import inf112.roborally.game.screens.setup.SelectScreen;
import inf112.roborally.game.tools.AssMan;

public class SelectSkinScreen extends SelectScreen {

    public SelectSkinScreen(final RoboRallyGame game) {
        super(game, SetupState.PICKINGSKIN, AssMan.getPlayerSkins().length);
    }

    @Override
    public void completeChoice() {
        skinChoiceIndex = choiceIndex;
        game.setScreen(game.selectMapScreen);
        // Since we are creating a new subclass of the SelectScreen abstract class, we need to
        // update the new one with the skin choice we just picked
        game.selectMapScreen.setSkinChoiceIndex(skinChoiceIndex);
        dispose();
    }
}
