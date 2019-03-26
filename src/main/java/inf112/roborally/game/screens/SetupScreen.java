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
    private String robotChoice;
    private Sprite selectSkinText;
    private ImageButton[] skins;

    public SetupScreen(RoboRallyGame game) {
        super(game, "assets/backgrounds/setupscreen.png");

        stage = new Stage(game.fixedViewPort, game.batch);
        Gdx.input.setInputProcessor(stage);
//        stage.addListener(game.cameraListener);

        selectSkinText = new Sprite(new Texture("assets/img/selectskintext.png"));
        selectSkinText.setPosition(Gdx.graphics.getWidth() / 2 + 125, 500);

        skins = new ImageButton[4];
        skins[0] = new ImageButton(new TextureRegionDrawable(new Texture("assets/robot/bartenderclaptrap.png")));
        skins[1] = new ImageButton(new TextureRegionDrawable(new Texture("assets/robot/claptrapRefined.png")));
        skins[2] = new ImageButton(new TextureRegionDrawable(new Texture("assets/robot/butlerRefined.png")));
        skins[3] = new ImageButton(new TextureRegionDrawable(new Texture("assets/robot/claptrap3000.png")));

        float scale = 0.4f;
        int x1 = 450; // The left x value
        int x2 = 1050; // The right x value
        int y = 350; // Starting Y value, this will change after 2 buttons are added to get them on different levels
        for (int i = 1; i < skins.length+1; i++) {
            ImageButton skin = skins[i-1];

            skin.setSize(skin.getWidth() * scale, skin.getHeight() * scale);

            // If i is odd, place it on the left side
            if(i % 2 != 0) {
                skin.setPosition(x1, y);
            }
            else {
                skin.setPosition(x2, y);
                y = y/2 - 100; // next time, add below
            }

            final int finalI = i;
            skin.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.printf("Clicked skin %d%n", finalI);
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
            game.createGameScreen();
            game.setScreen(game.gameScreen);
            dispose();
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }


}
