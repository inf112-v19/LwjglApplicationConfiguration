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
        Screen nameScreen = new NameScreen(game);
        game.setScreen(nameScreen);
        dispose();
    }

    @Override
    protected void confirmInput() {
        System.out.println(game.playerNames.size());
        System.out.println(game.playerNames);
        for (String s :
                game.playerNames) {
            game.client.sendMessage("START " + s);
        }
        System.out.println(game.playerName + " wants to start the game.");

    }

    @Override
    public void render(float v) {
        super.render(v);
        //System.out.println(game.players);
    }

}
