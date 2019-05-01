package inf112.roborally.game.screens.menus.setup;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.menus.MenuScreen;
import inf112.roborally.game.screens.InputFieldScreen;
import inf112.roborally.game.screens.menus.multiplayer.HostServerScreen;
import inf112.roborally.game.tools.AssMan;


public class SelectNumPlayers extends InputFieldScreen {
    private final int minPlayers = 2;
    private final int maxPlayers = 8;
    private Label number;
    private int nPlayers;

    private Screen nextScreen;
    private Screen previousScreen;

    public SelectNumPlayers(RoboRallyGame game) {
        super(game);
        nPlayers = 4;
        text.setVisible(false);
        setArrowsVisible(true);
        Label.LabelStyle style = new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA_ITALIC), Color.WHITE);
        number = new Label(Integer.toString(nPlayers), style);
        number.setFontScale(5);
        Table table = new Table();
        table.add(number);
        table.setPosition(1920 / 2, 1080 / 2, Align.center);
        stage.addActor(table);

        Label label = new Label("Select number of players", labelStyle);
        label.setFontScale(3);
        Table t2 = new Table();
        t2.setPosition(1920/2, 880, Align.center);
        t2.add(label);
        stage.addActor(t2);
    }


    public void setNextScreen(Screen nextScreen){
        this.nextScreen = nextScreen;
    }

    @Override
    protected void confirmInput() {
        game.setNumberOfChosenPlayers(nPlayers);
        game.client.sendMessage("SET_NUMBER_OF_PLAYERS " + nPlayers);
        if(nextScreen != null){
            game.setScreen(nextScreen);
        }

        if(nextScreen == null){
            game.setScreen(new HostServerScreen(game, this));
        }
    }

    @Override
    protected void goRight() {
        if (++nPlayers > maxPlayers) nPlayers = minPlayers;
        updateLabel();
    }

    @Override
    protected void goLeft() {
        if (--nPlayers < minPlayers) nPlayers = maxPlayers;
        updateLabel();
    }

    private void updateLabel() {
        number.setText(Integer.toString(nPlayers));
    }

    public void setPreviousScreen(Screen previousScreen){
        this.previousScreen = previousScreen;
    }

    @Override
    protected void goToPreviousScreen() {
        if(previousScreen != null) {
            game.setScreen(previousScreen);
        }else{
            game.setScreen(new MenuScreen(game));
        }
    }
}
