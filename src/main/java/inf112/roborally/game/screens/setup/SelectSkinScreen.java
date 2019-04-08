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
        System.out.println("SelectSkinScreen completeChoice() selected, switching to select map");
        System.out.println("Selected the choice at index " + choiceIndex);
        game.setScreen(game.selectMapScreen);
        dispose();
    }
}
