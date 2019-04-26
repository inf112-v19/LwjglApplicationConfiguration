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
        MultiplayerScreen multiplayerScreen = new MultiplayerScreen(game);
        game.setScreen(multiplayerScreen);
        dispose();
    }

    @Override
    protected void confirmInput() {
        if (!clicked || text.getText().length() < 3) return;
        System.out.println(text.getText());
        game.setPlayerName(text.getText());
    }
}
