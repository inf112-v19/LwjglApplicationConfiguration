package inf112.roborally.game.screens.menus.multiplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;
import inf112.roborally.game.tools.AssMan;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class HostServerScreen extends InputFieldScreen {
    private String ip;
    private Label label;
    private Label connectedPlayers;
    private int currentConnected = 1;
    private StringBuilder playerNamesBuilder;
    private Screen previousScreen;

    public HostServerScreen(RoboRallyGame game, Screen previousScreen) {
        super(game);
        background.addActor(new Image(AssMan.manager.get(AssMan.LOBBY_BACKGROUND)));
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
        connectedPlayers = new Label("", labelStyle);
        connectedPlayers.setPosition(1920 / 2, 400, Align.center);
        connectedPlayers.setAlignment(Align.center);
        connectedPlayers.setFontScale(2);

        playerNamesBuilder = new StringBuilder();
        playerNamesBuilder.append("\n"); // Start on a new line inside the label
        playerNamesBuilder.append(game.playerName);

        text.setVisible(false);
        confirm.setText("Start Game");
        stage.addActor(label);
        stage.addActor(connectedPlayers);
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
        game.client.sendMessage("SET_NUMBER_OF_PLAYERS " + game.playerNames.size());
        game.client.sendMessage("MULTI " + "True");
        System.out.println("[SERVER] Connected players " + game.playerNames);
        game.client.sendMessage("LIST " + game.playerNames.size());
        for (String s : game.playerNames) {
            game.client.sendMessage("START " + s + " " + id);
            id++;
        }
        System.out.println(game.playerName + " wants to start the game.");
        game.client.sendMessage("SET_MAP ");

    }

    @Override
    public void render(float v) {
        super.render(v);
        handleTextUpdate();
    }

    private void handleTextUpdate() {
        int totalConnected = game.playerNames.size();
        if(totalConnected > currentConnected) {
            playerNamesBuilder.append(", ");
            playerNamesBuilder.append(game.playerNames.get(totalConnected-1));
            currentConnected++;
        }
        connectedPlayers.setText("Connected players: " + game.playerNames.size() + playerNamesBuilder.toString());
    }

}
