package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;


public class HostScreen extends InputFieldScreen {
    private String ip = "111.111.11";
    private String name;
    private Label label;
    private boolean nameIsEntered = false;

    public HostScreen(RoboRallyGame game) {
        super(game);
        text.setText("'Your name'");
        label = new Label("Server Ip: " + ip, labelStyle);
        label.setPosition(1920 / 2, 1080 / 2, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(2);
        label.setVisible(false);
        stage.addActor(label);
        name = "";
    }

    @Override
    protected boolean confirmInput() {
        if (!nameIsEntered) {
            name = text.getText();
            label.setVisible(true);
            nameIsEntered = true;
            confirm.setText("Start Game");
            text.setVisible(false);
        } else {
            System.out.println(name + " wants to start the game.");
        }
        return true;
    }
}
