package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.roborally.game.RoboRallyGame;

public class SetupScreen extends AbstractScreen {

    public SetupScreen(RoboRallyGame game) {
        super(game, "assets/backgrounds/setupscreen.png");
    }

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

        if(Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            System.out.println("Key T pressed in SetupScreen, moving further");
            game.setScreen(game.gameScreen);
            dispose();
        }

    }


}
