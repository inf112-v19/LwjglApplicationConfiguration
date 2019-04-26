package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class LobbyScreen extends InputFieldScreen {
    private Label waiting;
    private String dots;
    private int timer;

    private Screen previousScreen;

    public LobbyScreen(final RoboRallyGame game, Screen previousScreen) {
        super(game);
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
}
