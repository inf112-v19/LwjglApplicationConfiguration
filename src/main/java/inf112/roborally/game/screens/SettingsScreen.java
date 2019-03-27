package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.gui.AssMan;
import org.lwjgl.Sys;

public class SettingsScreen extends AbstractScreen {

    // Just a local variable to let the settings screen keep track of whether or
    // not the music is muted
    private boolean musicIsMuted;
    private RoboRallyGame game;

    //SettingsScreen has its own Stage for holding actors, buttons etc.
    public Stage stage;

    private Screen screenBefore;

    public SettingsScreen(RoboRallyGame game) {
        super(game, AssMan.BACKGROUND_SETTINGS.fileName);
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
        handleInput();
    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.B) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("Key B or Escape is pressed, going back to the GameScreen");
            game.settingsScreen.dispose();

            if(game.getScreenBefore() == game.gameScreen){
                game.setScreen(game.gameScreen);
            }
            else if(game.getScreenBefore() == game.testScreen){ ;
                game.setScreen(game.testScreen);
            }
            Gdx.input.setInputProcessor(game.gameScreen.getHud().stage);
        }

        else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (!musicIsMuted) {
                game.gameScreen.getMusic().mute();
                game.gameScreen.getBoard().killTheSound();
                musicIsMuted = true;
                System.out.println("Muted the music from the settings screen");
            }
            else {
                game.gameScreen.getMusic().play();
                game.gameScreen.getBoard().restartTheSound();
                musicIsMuted = false;
                System.out.println("Started the music from the settings screen");
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            game.newGame();
        }
    }

    public void setScreenBefore(Screen screen){
        if(screen != null) {
            this.screenBefore = screen;
        }
    }

    /**
     * Can return null.
     * @return screen used before switching to settingsScreen.
     */
    public Screen getScreenBefore(){
        return this.screenBefore;
    }

    public void dispose(){

    }

}
