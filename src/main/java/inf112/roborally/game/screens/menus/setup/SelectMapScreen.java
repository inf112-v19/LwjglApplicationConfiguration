package inf112.roborally.game.screens.menus.setup;

import com.badlogic.gdx.Screen;
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

    private Screen previousScreen;

    public SelectMapScreen(final RoboRallyGame game, Screen previousScreen) {
        super(game, AssMan.getMapChoices());
        this.previousScreen = previousScreen;
        setInformationLabel("map");
    }


    @Override
    public void confirmInput() {
        mapTexture = AssMan.getMapChoices()[choiceIndex];
        game.board.createBoard(game.chosenMap(choiceIndex));
        game.board.findLaserGuns();
        this.placeFlagsScreen = new PlaceFlagsScreen(game);
        game.setScreen(placeFlagsScreen);
        dispose();
    }

    @Override
    public void goToPreviousScreen(){
        game.setScreen(previousScreen);
    }

    public Texture getMapTexture(){
        return mapTexture;
    }
}
