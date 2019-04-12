package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class ServerIpScreen extends InputFieldScreen {
    private String ip;

    public ServerIpScreen(final RoboRallyGame game) {
        super(game);
        text.setText("'Enter server ip'");
        confirm.setText("Connect");
        game.joinGame(text.getText());
    }

    @Override
    protected void goToPreviousScreen() {
        Screen previousScreen = new MultiplayerScreen(game);
        game.setScreen(previousScreen);
        dispose();
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
