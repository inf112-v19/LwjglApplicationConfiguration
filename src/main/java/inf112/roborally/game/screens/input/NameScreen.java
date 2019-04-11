package inf112.roborally.game.screens.input;

import inf112.roborally.game.RoboRallyGame;

public class NameScreen extends InputFieldScreen {

    public NameScreen(RoboRallyGame game) {
        super(game);
        text.setText("'Your name'");
    }

    @Override
    boolean confirmInput() {
        if (!clicked || text.getText().length() < 3) return false;
        System.out.println(text.getText());
        return true;
    }
}
