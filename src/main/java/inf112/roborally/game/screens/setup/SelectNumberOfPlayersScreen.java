package inf112.roborally.game.screens.setup;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;
import org.lwjgl.Sys;

public class SelectNumberOfPlayersScreen extends InputFieldScreen {

    public SelectNumberOfPlayersScreen(RoboRallyGame game) {
        super(game);
        text.setText("Number of players");
    }

    @Override
    protected boolean confirmInput() {
        if(!clicked) {
            done(0);
            return true;
        }
        else if(!isNumericAndInScope(text.getText())) {
            return false;
        }

        int chosenNumber = Integer.parseInt(text.getText());
        done(chosenNumber);
        return true;
    }

    private boolean isNumericAndInScope(String input) {
        try {
            int number = Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Invalid number");
            return false;
        }

        double validNumber = Integer.parseInt(input);
        // If we get here, it's an integer, so we check if it's a valid number to use in the game
        return (validNumber >= 0 && validNumber <= game.MAX_PLAYERS);
    }

    private void done(int chosenNumber) {
        game.setNumberOfChosenPlayers(chosenNumber);
        game.setScreen(game.selectSkinScreen);
        dispose();
    }
}
