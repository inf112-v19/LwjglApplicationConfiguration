package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.RoboRallyGame;

public class SettingsScreen extends AbstractScreen {

//    private RoboRallyGame game;
//    private SpriteBatch batch;
//    private Sprite background;

    public SettingsScreen(RoboRallyGame roborallygame) {
//        this.game = roborallygame;
//        batch = new SpriteBatch();

        super(roborallygame, "assets/img/settingsbackground.png");
//        background.setPosition(Gdx.graphics.getWidth() / 2 - background.getWidth() / 2, Gdx.graphics.getHeight() - background.getHeight());
//        game.fixedViewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

    }

    /**
     * In this case, we first want to run the shared render method on the superclass, but after that we
     * want to call the handleinput method from this spesific class. If we put the handleInput() method in the
     * superclass, then we wouldn't get the "SettiingsScreen" implementation of it
     */
    @Override
    public void render(float v) {
        super.render(v);
        handleInput();
    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.B) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("Key B or Escape is pressed, going back to the GameScreen");
            getGame().setScreen(getGame().gameScreen);
            dispose();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            dispose();
            getGame().newGame();
        }
    }

}
