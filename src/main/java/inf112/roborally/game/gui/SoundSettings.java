package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;

public class SoundSettings {

    public Stage stage;
    private MuteButton muteBtn;
    private SoundSettingsVisuals muteBtnVisuals;

    public SoundSettings(Stage stage) {
        this.stage = stage;
        muteBtnVisuals = new SoundSettingsVisuals();

        Gdx.input.setInputProcessor(stage);
        stage.addListener(((RoboRallyGame)Gdx.app.getApplicationListener()).cameraListener);

        muteBtn = new MuteButton(new TextureRegionDrawable((muteBtnVisuals.muteBtnTexture)), this);

//        muteBtn.setScale(2.f);
        muteBtn.setPosition(950, 900);
        stage.addActor(muteBtn);


    }

    public void testCallThisWhenClicked() {
        System.out.println("MuteButton clicked, method called - 2");
    }

    public void dispose() {
        System.out.println("disposing soundsettings");
        stage.dispose();
        muteBtnVisuals.dispose();
    }

    public Texture getTexture() {
        return muteBtnVisuals.muteBtnTexture;
    }

}
