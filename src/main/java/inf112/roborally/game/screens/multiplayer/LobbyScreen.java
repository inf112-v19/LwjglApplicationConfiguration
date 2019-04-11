package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;
import inf112.roborally.game.tools.AssMan;

public class LobbyScreen extends InputFieldScreen {

    public LobbyScreen(RoboRallyGame game) {
        super(game);
        text.setVisible(false);
        confirm.setVisible(false);
        Label label = new Label("Connecting to server..",
                new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), Color.WHITE));
        label.setPosition(1920 / 2, 1080 / 2, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(2);
        stage.addActor(label);
    }

    @Override
    protected boolean confirmInput() {
        return false;
    }

    @Override
    public void render(float v) {
        waitingToConnect();
        super.render(v);
    }

    private void waitingToConnect() {
    }
}
