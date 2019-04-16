package inf112.roborally.game.screens.setup;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

import static inf112.roborally.game.enums.Direction.NORTH;


/**
 * Screen for selecting a player skin.
 *
 * {@link #choiceIndex} is the index for the chosen player skin.
 */
public class SelectSkinScreen extends SelectScreen {

    public SelectSkinScreen(final RoboRallyGame game) {
        super(game, AssMan.getPlayerSkins(), AssMan.getPlayerSkins().length);
    }

    @Override
    public void completeChoice() {
        for (Player player : game.board.getPlayers()) {
            player.setSkinTexture(AssMan.getPlayerSkins()[choiceIndex]);
        }

        game.setScreen(game.selectMapScreen);
        dispose();
    }

    public void addPlayersToBoard() {
        int index = choiceIndex;
        int numberOfSkins = AssMan.getPlayerSkins().length;
        for (int i = 0; i < numberOfSkins; i++) {
            if (index >= numberOfSkins) {
                index = 0;
            }
            Player player = new Player("Player" + (i + 1), AssMan.getPlayerSkins()[index], NORTH, game.board);
            game.board.addPlayer(player);
            index++;
        }
    }
}
