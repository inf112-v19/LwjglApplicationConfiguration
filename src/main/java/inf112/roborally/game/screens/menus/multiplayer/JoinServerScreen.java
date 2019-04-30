package inf112.roborally.game.screens.menus.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

/**
 * Screen for choosing player name when joining a server.
 */
public class JoinServerScreen extends InputFieldScreen {
    private String ip;

    private Screen previousScreen;

    public JoinServerScreen(final RoboRallyGame game, Screen previousScreen) {
        super(game);
        this.previousScreen = previousScreen;
        text.setText("'Enter server ip'");
        confirm.setText("Connect");
    }

    @Override
    protected void goToPreviousScreen() {
        game.setScreen(previousScreen);
    }

    @Override
    protected void confirmInput() {
        if (!clicked || text.getText().length() < 3) return;
        ip = text.getText();
        game.joinGame(ip);
        System.out.println("Trying to connect to: " + ip);
        Screen nextScreen = new LobbyScreen(game, this);
        game.setScreen(nextScreen);
    }

    @Override
    public void dispose(){
        super.dispose();
        //dispose...
    }
}
