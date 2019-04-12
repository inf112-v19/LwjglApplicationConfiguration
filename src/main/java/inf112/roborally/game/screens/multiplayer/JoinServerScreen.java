package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class JoinServerScreen extends InputFieldScreen {
    private String ip;
    private String name;

    public JoinServerScreen(final RoboRallyGame game, String name) {
        super(game);
        this.name = name;
        text.setText("'Enter server ip'");
        confirm.setText("Connect");
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
        ip = text.getText();
        game.joinGame(ip, name);
        System.out.println("Trying to connect to: " + ip);
        Screen s = new LobbyScreen(game);
        game.setScreen(s);
        return true;
    }
}
