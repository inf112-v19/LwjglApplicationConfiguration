package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class HostScreen extends InputFieldScreen {
    private String ip;
    private String name;
    private Label label;
    private boolean nameIsEntered = false;

    public HostScreen(RoboRallyGame game) {
        super(game);
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        label = new Label("Server Ip: " + ip, labelStyle);
        label.setPosition(1920 / 2, 1080 / 2, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(2);
        text.setText("'Enter name'");
        label.setVisible(false);
        stage.addActor(label);
        name = "";


    }

    @Override
    protected void goToPreviousScreen() {
        Screen previous = new MultiplayerScreen(game);
        game.setScreen(previous);
        dispose();
    }

    @Override
    protected boolean confirmInput() {
        if (!nameIsEntered) {
            name = text.getText();
            label.setVisible(true);
            nameIsEntered = true;
            confirm.setText("Start Game");
            text.setVisible(false);
        }
        else {
            System.out.println(name + " wants to start the game.");
        }
        return true;
    }
}
