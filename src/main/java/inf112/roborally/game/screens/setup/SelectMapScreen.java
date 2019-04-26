package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.graphics.Texture;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

/**
 * Screen for selecting which map to use.
 *
 * {@link #choiceIndex} is the index for which map to use.
 */
public class SelectMapScreen extends SelectScreen {

    protected Texture mapTexture;

    private PlaceFlagsScreen placeFlagsScreen;

    public SelectMapScreen(final RoboRallyGame game) {
        super(game, AssMan.getMapChoices(), AssMan.getMapChoices().length);
        setInformationLabel("map");
    }


    @Override
    public void completeChoice() {
        mapTexture = AssMan.getMapChoices()[choiceIndex];
        game.board.createBoard(game.chosenMap(choiceIndex));
        game.board.findLaserGuns();
        this.placeFlagsScreen = new PlaceFlagsScreen(game);
        game.setScreen(placeFlagsScreen);
        dispose();
    }

    public Texture getMapTexture(){
        return mapTexture;
    }
}
