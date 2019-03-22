package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.RoboRallyGame;

public class SettingsScreen extends AbstractScreen {

    public SettingsScreen(RoboRallyGame roborallygame) {
        super(roborallygame, "assets/img/settingsbackground.png");

    }

    /**
     * In this case, we first want to run the shared render method on the superclass, but after that we
     * want to call the handleInput method from this specific class. If we put the handleInput() method in the
     * superclass, then we wouldn't get the "SettingsScreen" implementation of it
     */
    @Override
    public void render(float v) {
        super.render(v);
        handleInput();
    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.B) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("Key B or Escape is pressed, going back to the GameScreen");
            game.setScreen(game.gameScreen);
            dispose();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            dispose();
            game.newGame();
        }
    }

}
