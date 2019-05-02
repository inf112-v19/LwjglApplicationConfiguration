package inf112.roborally.game.screens.menus.multiplayer;

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
    private Screen previousScreen;

    public HostServerScreen(RoboRallyGame game, Screen previousScreen) {
        super(game);
        this.previousScreen = previousScreen;
        game.startServer();
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        game.joinGame(ip);
        label = new Label("Server Ip: " + ip, labelStyle);
        label.setPosition(1920 / 2, 1080 / 2, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(2);
        text.setVisible(false);
        confirm.setText("Start Game");
        stage.addActor(label);

        System.out.printf("Done with the constructor in HostServerScreen, number of players from game: %d%n", game.numberOfChosenPlayers);
    }

    @Override
    protected void goToPreviousScreen() {
        game.setScreen(previousScreen);
    }

    @Override
    protected void confirmInput() {
        if(game.client.getChannel() == null){
            return;
        }
        int id = 0;
        game.multiPlayer = true;
        System.out.println("ConfirmInput(). Number of Chosen players = " + game.numberOfChosenPlayers);
        game.client.sendMessage("SET_NUMBER_OF_PLAYERS " + game.numberOfChosenPlayers);
        game.client.sendMessage("MULTI " + "True");
        System.out.println("[SERVER] Connected players " + game.playerNames);
        game.client.sendMessage("LIST " + game.playerNames.size());
        for (String s :
                game.playerNames) {
            game.client.sendMessage("START " + s + " " + id);
            id++;
        }
        System.out.println(game.playerName + " wants to start the game.");
        game.client.sendMessage("SET_MAP ");

    }

    @Override
    public void render(float v) {
        super.render(v);
        //System.out.println(game.players);
    }

}
