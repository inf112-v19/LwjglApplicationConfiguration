package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

/**
 * Screen for selecting a player skin.
 *
 * {@link #choiceIndex} is the index for the chosen player skin.
 */
public class SelectSkinScreen extends SelectScreen {

    public SelectSkinScreen(final RoboRallyGame game) {
        super(game, AssMan.getPlayerSkins().length);
    }

    @Override
    protected void setChoicesBasedOnScreen() {
        choices = new TextureRegionDrawable[numberOfChoices];
        for (int i = 0; i < numberOfChoices; i++) {
            choices[i] = new TextureRegionDrawable(AssMan.getPlayerSkins()[i]);
        }
        information = "skin";
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
