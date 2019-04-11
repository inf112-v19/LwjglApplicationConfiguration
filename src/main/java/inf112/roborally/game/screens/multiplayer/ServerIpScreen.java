package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class ServerIpScreen extends InputFieldScreen {

    public ServerIpScreen(final RoboRallyGame game) {
        super(game);
        text.setText("'ServerIp'");
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
        System.out.println("Trying to connect to: " + text.getText());
        Screen s = new LobbyScreen(game);
        game.setScreen(s);
        return true;
    }
}
