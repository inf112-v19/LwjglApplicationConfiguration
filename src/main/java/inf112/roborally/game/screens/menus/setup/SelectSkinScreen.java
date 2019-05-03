package inf112.roborally.game.screens.menus.setup;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

/**
 * Screen for selecting a player skin.
 *
 * {@link #choiceIndex} is the index for the chosen player skin.
 */
public class SelectSkinScreen extends SelectScreen {

    private Screen previousScreen;

    public SelectSkinScreen(final RoboRallyGame game, Screen previousScreen) {
        super(game, AssMan.getPlayerSkins());
        this.previousScreen = previousScreen;
        setInformationLabel("skin");
    }

    @Override
    public void confirmInput() {
        for (Player player : game.board.getPlayers()) {
            player.setSkinTexture(AssMan.getPlayerSkins()[choiceIndex]);
        }

        game.setScreen(game.selectMapScreen);
    }

    @Override
    public void goToPreviousScreen(){
        game.setScreen(previousScreen);
    }

    public int getChoiceIndex(){
        return choiceIndex;
    }
}
