package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.SetupState;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.screens.setup.SelectScreen;
import inf112.roborally.game.tools.AssMan;

import static inf112.roborally.game.enums.Direction.NORTH;

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

    public void addPlayersToBoard(int robotChoiceIndex) {
        int index = robotChoiceIndex;
        int n = AssMan.getPlayerSkins().length;
        for (int i = 0; i < n; i++) {
            if (index >= n) {
                index = 0;
            }
            game.board.addPlayer(new Player("Player" + (i + 1), AssMan.getPlayerSkins()[index], NORTH, game.board));

            index++;
        }
    }
}
