package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.graphics.Texture;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.SetupState;
import inf112.roborally.game.tools.AssMan;

public class SelectMapScreen extends SelectScreen {

    protected int mapChoiceIndex;
    protected Texture mapTexture;

    private PlaceFlagsScreen placeFlagsScreen;

    public SelectMapScreen(final RoboRallyGame game) {
        super(game, AssMan.getMapChoices(), AssMan.getMapChoices().length);
    }

    @Override
    public void completeChoice() {
        mapChoiceIndex = choiceIndex;
        mapTexture = AssMan.getMapChoices()[mapChoiceIndex];
        game.board.createBoard(game.chosenMap(mapChoiceIndex));
        game.board.findLaserGuns();
        this.placeFlagsScreen = new PlaceFlagsScreen(game);
        game.setScreen(placeFlagsScreen);
        dispose();
    }

    public int getMapIndex(){
        return mapChoiceIndex;
    }

    public Texture getMapTexture(){
        return mapTexture;
    }
}
