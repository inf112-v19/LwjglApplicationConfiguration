package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.SetupState;
import inf112.roborally.game.tools.AssMan;

public class SelectMapScreen extends SelectScreen {
    public SelectMapScreen(final RoboRallyGame game) {
        super(game, AssMan.getMapChoices().length);
    }

    @Override
    protected void setChoicesBasedOnScreen() {
        choices = new TextureRegionDrawable[numberOfChoices];
        for (int i = 0; i < numberOfChoices; i++) {
            choices[i] = new TextureRegionDrawable(AssMan.getMapChoices()[i]);
        }
        information = "map";
    }

    @Override
    public void completeChoice() {
        mapChoiceIndex = choiceIndex;
        game.setScreen(new PlaceFlagsScreen(game, AssMan.getMapChoices()[mapChoiceIndex], mapChoiceIndex, skinChoiceIndex));
        dispose();
    }
}
