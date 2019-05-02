package inf112.roborally.game.screens.menus.multiplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;
import inf112.roborally.game.tools.AssMan;

public class LobbyScreen extends InputFieldScreen {
    private Label waiting;
    private String dots;
    private int timer;
    private Label waitingForPlayers1;
    private Label waitingForPlayers2;
    private Label waitingForPlayers3;
    private Label waitingForPlayers4;

    private Screen previousScreen;

    public LobbyScreen(final RoboRallyGame game, Screen previousScreen) {
        super(game);
        background.addActor(new Image(AssMan.manager.get(AssMan.LOBBY_BACKGROUND)));
        this.previousScreen = previousScreen;
        timer = 0;
        confirm.setVisible(false);
        Label label = new Label("Connecting to server", labelStyle);
        label.setPosition(1920 / 2, 1080 / 2 + 50, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(2);
        waiting = new Label(dots, labelStyle);
        waiting.setPosition(1920 / 2f, 1080 / 2f - 50, Align.center);
        waiting.setAlignment(Align.center);
        waiting.setFontScale(2.5f);

        stage.addActor(label);
        stage.addActor(waiting);

        setFieldVisible(false);
        background.setVisible(false);

        String s = "Waiting for host to start the game";
        int i = 0;

        waitingForPlayers1 = new Label(s, labelStyle);
        waitingForPlayers1.setVisible(false);
        waitingForPlayers1.setPosition(1920 / 2, 1080 / 2, Align.center);
        waitingForPlayers2 = new Label(s, labelStyle);
        waitingForPlayers2.setPosition(waitingForPlayers1.getX(), waitingForPlayers1.getY() - 10 * ++i);
        waitingForPlayers2.setVisible(false);
        waitingForPlayers3 = new Label(s, labelStyle);
        waitingForPlayers3.setVisible(false);
        waitingForPlayers3.setPosition(waitingForPlayers1.getX(), waitingForPlayers1.getY() - 10 * ++i);
        waitingForPlayers4 = new Label(s, labelStyle);
        waitingForPlayers4.setVisible(false);
        waitingForPlayers4.setPosition(waitingForPlayers1.getX(), waitingForPlayers1.getY() - 10 * ++i);

        stage.addActor(waitingForPlayers4);
        stage.addActor(waitingForPlayers3);
        stage.addActor(waitingForPlayers2);
        stage.addActor(waitingForPlayers1);
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
        waitingToConnect();
        super.render(v);
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

    private void connected() {
        background.setVisible(true);
        waitingForPlayers1.setVisible(true);
        waitingForPlayers2.setVisible(true);
        waitingForPlayers3.setVisible(true);
        waitingForPlayers4.setVisible(true);

    }
}
