package inf112.roborally.game.screens.input;

import inf112.roborally.game.RoboRallyGame;

public class ServerIpScreen extends InputFieldScreen {

    public ServerIpScreen(RoboRallyGame game) {
        super(game);
        text.setText("'ServerIp'");
        confirm.setText("Connect");
    }

    @Override
    boolean confirmInput() {
        if (!clicked || text.getText().length() < 3) return false;
        System.out.println("Trying to connect to: " + text.getText());
        return true;
    }
}
