package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class NameScreen extends InputFieldScreen {
    private Screen nextScreen;

    public NameScreen(RoboRallyGame game){
        super(game);
        text.setText("'Your name'");
    }

    @Override
    public void goToPreviousScreen(){
        MultiplayerScreen multiplayerScreen = new MultiplayerScreen(game);
        game.setScreen(multiplayerScreen);
        dispose();
    }

    @Override
    protected void confirmInput() {
        if (!clicked || text.getText().length() < 3) return;
        System.out.println(text.getText());
        game.setPlayerName(text.getText());
    }

    @Override
    public void dispose(){
        super.dispose();
        if(nextScreen != null) {
            nextScreen.dispose();
        }
    }

    public void setNextScreen(Screen nextScreen){
        this.nextScreen = nextScreen;
    }
}
