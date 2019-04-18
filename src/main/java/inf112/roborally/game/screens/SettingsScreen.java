package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.ButtonFactory;

public class SettingsScreen extends BasicScreen {
    private boolean musicIsMuted = false;
    private final TextButton mute;

    public SettingsScreen(final RoboRallyGame game) {
        super(game);
        back.setVisible(false);
        TextButton back = ButtonFactory.createTextButton("Back", 2);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToPreviousScreen();
            }
        });
        mute = ButtonFactory.createTextButton("Mute", 2);
        mute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                muteMusic();
            }
        });
        final TextButton exit = ButtonFactory.createTextButton("Exit Game", 2);
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

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            goToPreviousScreen();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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
        if (!musicIsMuted) {
            musicIsMuted = game.gameScreen.playMusic(false);
            System.out.println("Muted the music from the settings screen");
            mute.setText("Unmute");
        }
        else {
            musicIsMuted = game.gameScreen.playMusic(true);
            System.out.println("Started the music from the settings screen");
            mute.setText("Mute");
        }
    }

    private void exitGame() {
        Gdx.app.exit();
    }
}
