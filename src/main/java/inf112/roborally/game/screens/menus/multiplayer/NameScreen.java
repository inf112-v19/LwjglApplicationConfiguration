package inf112.roborally.game.screens.menus.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class NameScreen extends InputFieldScreen {

    private Screen nextScreen;
    private Screen previousScreen;

    public NameScreen(RoboRallyGame game, Screen previousScreen){
        super(game);
        this.previousScreen = previousScreen;
        text.setText("'Your name'");
    }

    public void setNextScreen(Screen nextScreen){
        this.nextScreen = nextScreen;
    }

    @Override
    public void goToPreviousScreen(){
        game.setScreen(previousScreen);

        //MultiplayerScreen multiplayerScreen = new MultiplayerScreen(game);
        //game.setScreen(multiplayerScreen);
        //dispose();
    }

    @Override
    protected void confirmInput() {
        if (!clicked || text.getText().length() < 3) return;
        System.out.println(text.getText());
        game.setPlayerName(text.getText());
        if(nextScreen != null){
            game.setScreen(nextScreen);
        }
    }
}
