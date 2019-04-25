package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

/**
 * Screen for choosing player name when hosting a server.
 */
public class NameScreenHost extends InputFieldScreen {

    private Screen nextScreen;

    public NameScreenHost(RoboRallyGame game){
        super(game);
        text.setText("'Your name'");
        nextScreen = new HostServerScreen(game, this);
    }

    @Override
    public void goToPreviousScreen(){
        MultiplayerScreen multiplayerScreen = new MultiplayerScreen(game);
        game.setScreen(multiplayerScreen);
        dispose();
    }



    @Override
    protected boolean confirmInput() {
        if (!clicked || text.getText().length() < 3) return false;
        game.setPlayerName(text.getText());
        System.out.println("PlayerName: " + game.playerName);

        game.setScreen(nextScreen);
        return true;
    }

    @Override
    public void dispose(){
        super.dispose();
        nextScreen.dispose();
    }
}
