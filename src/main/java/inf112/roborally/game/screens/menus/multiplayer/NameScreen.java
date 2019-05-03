package inf112.roborally.game.screens.menus.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class NameScreen extends InputFieldScreen {

    private Screen previousScreen;

    public NameScreen(RoboRallyGame game, Screen previousScreen){
        super(game);
        this.previousScreen = previousScreen;
        text.setText("'Enter name'");
    }

    @Override
    public void goToPreviousScreen(){
        game.setScreen(previousScreen);
    }

    @Override
    protected void confirmInput() {
        if (!clicked || text.getText().length() < 3) return;
        game.setPlayerName(text.getText());

        MultiplayerScreen nextScreen = new MultiplayerScreen(game, this);
        game.setScreen(nextScreen);
    }
}
