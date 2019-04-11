package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class ServerIpScreen extends InputFieldScreen {

    public ServerIpScreen(RoboRallyGame game) {
        super(game);
        text.setText("'ServerIp'");
        confirm.setText("Connect");
    }

    @Override
    protected boolean confirmInput() {
        if (!clicked || text.getText().length() < 3) return false;
        System.out.println("Trying to connect to: " + text.getText());
        Screen s = new LobbyScreen(game);
        game.setScreen(s);
        return true;
    }
}
