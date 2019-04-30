package inf112.roborally.game.screens.menus.setup;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

/**
 * Screen for selecting a player skin.
 *
 * {@link #choiceIndex} is the index for the chosen player skin.
 */
public class SelectSkinScreen extends SelectScreen {

    public SelectSkinScreen(final RoboRallyGame game) {
        super(game, AssMan.getPlayerSkins());
        setInformationLabel("skin");
    }

    @Override
    public void confirmInput() {
        for (Player player : game.board.getPlayers()) {
            player.setSkinTexture(AssMan.getPlayerSkins()[choiceIndex]);
        }

        game.setScreen(game.selectMapScreen);
    }


    public int getChoiceIndex(){
        return choiceIndex;
    }
}
