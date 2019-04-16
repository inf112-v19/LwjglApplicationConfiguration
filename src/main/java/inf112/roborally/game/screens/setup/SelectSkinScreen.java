package inf112.roborally.game.screens.setup;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * Creates a list of players. The chosen skin is set here.
     * The players can be added to the board with {@link Board#addPlayersToBoard(List)}.
     *
     * @return all players who should be added to the board.
     */
    public List<Player> createCustomPlayers() {
        List<Player> players = new ArrayList<>();
        int index = choiceIndex;
        int numberOfSkins = AssMan.getPlayerSkins().length;
        for (int i = 0; i < numberOfSkins; i++) {
            if (index >= numberOfSkins) {
                index = 0;
            }
            Player player = new Player("Player" + (i + 1), AssMan.getPlayerSkins()[index], NORTH, game.board);
            players.add(player);
            index++;
        }
        return players;
    }
}
