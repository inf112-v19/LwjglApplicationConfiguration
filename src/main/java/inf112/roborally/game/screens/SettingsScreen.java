package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

public class SettingsScreen extends AbstractScreen {

    //Stage for holding actors.
    public Stage stage;
    // Just a local variable to let the settings screen keep track of whether or
    // not the music is muted
    private boolean musicIsMuted;
    private RoboRallyGame game;


    public SettingsScreen(RoboRallyGame game) {
        super(game, AssMan.SETTINGS_BACKGROUND.fileName);
        this.game = game;
        musicIsMuted = false;
        stage = new Stage(game.fixedViewPort, game.batch);
    }

    /**
     * In this case, we first want to run the shared render method on the superclass, but after that we
     * want to call the handleInput method from this specific class. If we put the handleInput() method in the
     * superclass, then we wouldn't get the "SettingsScreen" implementation of it.
     */
    @Override
    public void render(float v) {
        super.render(v);

        batch.setProjectionMatrix(game.fixedCamera.combined);
        batch.begin();
        background.draw(batch);
        batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.B) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.settingsScreen.dispose();

            if (game.getScreenBefore() == game.gameScreen) {
                game.setScreen(game.gameScreen);
                System.out.println("Key B or Escape is pressed, going back to the GameScreen");
            }
            else if (game.getScreenBefore() == game.testScreen) {
                game.setScreen(game.testScreen);
                System.out.println("Key B or Escape is pressed, going back to the TestScreen");
            }
            else if (game.getScreenBefore() == game.laserTestScreen) {
                game.setScreen(game.laserTestScreen);
                System.out.println("Key B or Escape is pressed, going back to the LaserTestScreen");
            }

        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (!musicIsMuted) {
                musicIsMuted = game.gameScreen.playMusic(false);
                System.out.println("Muted the music from the settings screen");
            }
            else {
                musicIsMuted = game.gameScreen.playMusic(true);
                System.out.println("Started the music from the settings screen");
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            game.setLaunchTestMap(false);
            game.newGame();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void dispose() {
        stage.dispose();
    }

}
