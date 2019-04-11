package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;


public class HostScreen extends InputFieldScreen {
    private String ip = "111.111.11";

    public HostScreen(RoboRallyGame game) {
        super(game);
        text.setVisible(false);
        confirm.setText("Start Game");
        Label label = new Label("Server Ip: " + ip, labelStyle);
        label.setPosition(1920 / 2, 1080 / 2, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(2);
        stage.addActor(label);
    }

    @Override
    protected boolean confirmInput() {
        return false;
    }
}
