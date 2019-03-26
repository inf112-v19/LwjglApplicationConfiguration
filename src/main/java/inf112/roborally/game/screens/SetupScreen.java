package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;

public class SetupScreen extends AbstractScreen {
    private Stage stage;
    private ImageButton[] skins;
    private Sprite selectSkinText;
    private int robotChoiceIndex;

    public SetupScreen(RoboRallyGame game, String[] possibleFilepaths) {
        super(game, "assets/backgrounds/setupscreen.png");

        stage = new Stage(game.fixedViewPort, game.batch);
        Gdx.input.setInputProcessor(stage);
//        stage.addListener(game.cameraListener);

        int nSkins = possibleFilepaths.length;
        selectSkinText = new Sprite(new Texture("assets/img/selectskintext.png"));
        selectSkinText.setPosition(Gdx.graphics.getWidth() / 2 + 125, 500);

        skins = new ImageButton[nSkins];

        float scale = 0.4f;
        int x1 = 450; // The left x value
        int x2 = 1050; // The right x value
        // Starting Y value, this will change after 2 buttons are added to get the next to below the first two
        int y = 350;
        for (int i = 1; i < skins.length+1; i++) {
            int index = i-1;
            skins[index] = new ImageButton(new TextureRegionDrawable(new Texture(possibleFilepaths[index])));
            ImageButton skin = skins[index];
            skin.setSize(skin.getWidth() * scale, skin.getHeight() * scale);

            // If i is odd, place it on the left side
            if(i % 2 != 0) {
                skin.setPosition(x1, y);
            }
            else {
                skin.setPosition(x2, y);
                y = y/2 - 100; // next time, add below
            }

            final int finalIndex = index;
            skin.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.printf("Clicked skin %d%n", finalIndex+1);
                    setRobotChoice(finalIndex);
                }
            });

            stage.addActor(skin);
        }
    }

    @Override
    public void render(float v) {
        super.render(v);

        batch.setProjectionMatrix(game.fixedCamera.combined);
        batch.begin();
        background.draw(batch);
        selectSkinText.draw(batch);
        batch.end();
        handleInput();

        stage.draw();
    }

    private void handleInput() {

        if(Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            System.out.println("Key T pressed in SetupScreen, moving further");
            game.createGameScreen(0); // Choose the standard skin index
            game.setScreen(game.gameScreen);
            dispose();
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    private void setRobotChoice(int index) {
        robotChoiceIndex = index;
        System.out.println("The robot choice is robot at index " + index + "!");
        game.createGameScreen(robotChoiceIndex);
        game.setScreen(game.gameScreen);
        dispose();
    }

}
