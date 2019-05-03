package inf112.roborally.game.screens.menus.multiplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;
import inf112.roborally.game.tools.AssMan;

public class LobbyScreen extends InputFieldScreen {
    private final Animation<TextureRegion> animation;
    private Label waiting;
    private Label connectingToServer;
    private String dots;
    private int timer;
    private boolean connected;
    private Screen previousScreen;
    private Image waitingForPlayers;
    private int animationTimer = 0;

    public LobbyScreen(final RoboRallyGame game, Screen previousScreen) {
        super(game);
        this.previousScreen = previousScreen;
        timer = 0;
        connected = false;
        confirm.setVisible(false);
        connectingToServer = new Label("Connecting to server", labelStyle);
        connectingToServer.setPosition(1920 / 2, 1080 / 2 + 50, Align.center);
        connectingToServer.setAlignment(Align.center);
        connectingToServer.setFontScale(2);
        waiting = new Label(dots, labelStyle);
        waiting.setPosition(1920 / 2f, 1080 / 2f - 50, Align.center);
        waiting.setAlignment(Align.center);
        waiting.setFontScale(2.5f);

        stage.addActor(connectingToServer);
        stage.addActor(waiting);

        setFieldVisible(false);
        background.setVisible(false);
        Array<TextureRegion> regions = new Array<>();
        for (int j = 1; j < 6; j++) {
            regions.add(AssMan.manager.get(AssMan.LOBBY_ANIMATION).findRegion(Integer.toString(j)));
        }
        animation = new Animation<>(20, regions);
        waitingForPlayers = new Image(animation.getKeyFrame(timer, true));
        waitingForPlayers.setSize(
                waitingForPlayers.getWidth() * 1.2f, waitingForPlayers.getHeight() * 1.2f);
        waitingForPlayers.setPosition(1920 / 2, 1080 / 2, Align.center);
        waitingForPlayers.setVisible(false);
        stage.addActor(waitingForPlayers);
    }

    @Override
    protected void goToPreviousScreen() {
        game.setScreen(previousScreen);
    }

    @Override
    protected void confirmInput() {
    }

    @Override
    public void render(float v) {
        if (!connected) {
            checkIfConnected();
            waitingToConnect();
        }

        waitingForPlayers.setDrawable(new TextureRegionDrawable(animation.getKeyFrame(animationTimer++, true)));
        super.render(v);
    }

    private void checkIfConnected() {
        if (game.connectedToServer) {
            connected = true;
            waitingForPlayers.setVisible(true);
            waiting.setVisible(false);
            connectingToServer.setVisible(false);
        }
    }

    private void waitingToConnect() {
        timer++;
        if (timer < 20) dots = "";
        else if (timer < 40) dots = ".";
        else if (timer < 60) dots = "..";
        else if (timer < 80) dots = "...";
        else if (timer < 100) timer = 0;

        waiting.setText(dots);
    }
}
