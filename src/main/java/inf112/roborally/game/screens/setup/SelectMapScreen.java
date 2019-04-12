package inf112.roborally.game.screens.setup;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.SetupState;
import inf112.roborally.game.tools.AssMan;

public class SelectMapScreen extends SelectScreen {
    public SelectMapScreen(final RoboRallyGame game) {
        super(game, SetupState.PICKINGMAP, AssMan.getMapChoices().length);
    }

    @Override
    public void completeChoice() {
        game.board = new Board();
        mapChoiceIndex = choiceIndex;
        game.board.createBoard(game.chosenMap(mapChoiceIndex));
        game.board.findLaserGuns();
        game.setScreen(new PlaceFlagsScreen(game, AssMan.getMapChoices()[mapChoiceIndex], mapChoiceIndex, skinChoiceIndex));
        dispose();
    }
}
