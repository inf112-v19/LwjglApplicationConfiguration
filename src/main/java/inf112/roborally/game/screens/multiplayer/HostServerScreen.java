package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class HostServerScreen extends InputFieldScreen {
    private String ip;
    private Label label;

    public HostServerScreen(RoboRallyGame game) {
        super(game);
        game.startServer();
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        game.joinGame("127.0.0.1"); //Replace with "ip" after
        label = new Label("Server Ip: " + ip, labelStyle);
        label.setPosition(1920 / 2, 1080 / 2, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(2);
        text.setVisible(false);
        confirm.setText("Start Game");
        stage.addActor(label);
    }

    @Override
    protected void goToPreviousScreen() {
        Screen previous = new MultiplayerScreen(game);
        game.setScreen(previous);
        dispose();
    }

    @Override
    protected boolean confirmInput() {
            System.out.println(game.name + " wants to start the game.");
            return true;
    }

    @Override
    public void render(float v) {
        super.render(v);
        //System.out.println(game.players);
    }
}
