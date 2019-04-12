package inf112.roborally.game.screens.multiplayer;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class NameScreen extends InputFieldScreen {

    public NameScreen(RoboRallyGame game) {
        super(game);
        text.setText("'Your name'");
    }

    @Override
    protected void goToPreviousScreen() {
    }

    @Override
    protected boolean confirmInput() {
        if (!clicked || text.getText().length() < 3) return false;
        System.out.println(text.getText());
        return true;
    }
}
