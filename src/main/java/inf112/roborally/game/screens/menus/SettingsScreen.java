package inf112.roborally.game.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.BasicScreen;
import inf112.roborally.game.tools.ButtonFactory;

public class SettingsScreen extends BasicScreen {
    private final TextButton mute;

    public SettingsScreen(final RoboRallyGame game) {
        super(game);
        back.setVisible(false); // This is the back icon in the BasicScreen. We don't need it so we just hide it here.
        TextButton back = ButtonFactory.createTextButton("Back", 2);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToPreviousScreen();
            }
        });
        mute = ButtonFactory.createTextButton("Mute", 2); // Mute needs to be a field variable because
        mute.addListener(new ClickListener() {                  // its textfield is changed when the button is clicked.
            @Override
            public void clicked(InputEvent event, float x, float y) {
                muteMusic();
            }
        });
        setMuteText();
        TextButton exit = ButtonFactory.createTextButton("Exit Game", 2);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitGame();
            }
        });
        TextButton menu = ButtonFactory.createTextButton("Main Menu", 2);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.newGame();
            }
        });
        int nButtons = 1;
        back.setY(300);
        mute.setY(back.getY() + (back.getHeight() + 10) * ++nButtons);
        exit.setY(back.getY() + (back.getHeight() + 10) * ++nButtons);
        menu.setY(back.getY() + (back.getHeight() + 10) * ++nButtons);

        stage.addActor(back);
        stage.addActor(menu);
        stage.addActor(mute);
        stage.addActor(exit);
    }

    private void setMuteText() {
        if(game.soundMuted){
            mute.setText("Unmute");
        }
        else{
            mute.setText("Mute");
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            goToPreviousScreen();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            exitGame();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            muteMusic();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            game.newGame();
        }
    }

    private void muteMusic() {
        if (!game.soundMuted) {
            game.soundMuted = game.gameScreen.playMusic(false);
            System.out.println("Muted the music from the settings screen");
            mute.setText("Unmute");
        } else {
            game.soundMuted = game.gameScreen.playMusic(true);
            System.out.println("Started the music from the settings screen");
            mute.setText("Mute");
        }
    }

    private void exitGame() {
        Gdx.app.exit();
    }
}
