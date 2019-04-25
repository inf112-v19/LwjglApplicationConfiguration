package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Screen;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;

public class NameScreenJoin extends InputFieldScreen {

    private Screen nextScreen;

    public NameScreenJoin(RoboRallyGame game) {
        super(game);
        this.nextScreen = new JoinServerScreen(game, this);
        text.setText("'Your name'");
    }

    @Override
    protected void goToPreviousScreen() {
        MultiplayerScreen multiplayerScreen = new MultiplayerScreen(game);
        game.setScreen(multiplayerScreen);
        dispose();
    }

    @Override
    protected boolean confirmInput() {
        if (!clicked || text.getText().length() < 3) return false;
        System.out.println(text.getText());
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
