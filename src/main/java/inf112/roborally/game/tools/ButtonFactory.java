package inf112.roborally.game.tools;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.audio.Mp3;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {

    public static ClickListener soundOnTouchDown(){
        return new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                AssMan.manager.get(AssMan.SOUND_BUTTON_CLICKED).play(0.5f);
                return true;
            }
        };
    }

    public static ImageButton createArrowRightButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.RIGHT_ARROW.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.RIGHT_ARROW_PRESS.fileName));
        ImageButton arrowRight = new ImageButton(buttonUp, buttonDown);
        arrowRight.setPosition(1920 - arrowRight.getWidth() - 100, 1080 / 2f - arrowRight.getHeight() / 2f);
        arrowRight.addListener(soundOnTouchDown());
        return arrowRight;
    }

    public static ImageButton createArrowLeftButton(){
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.LEFT_ARROW.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.LEFT_ARROW_PRESS.fileName));
        ImageButton arrowLeft = new ImageButton(buttonUp, buttonDown);
        arrowLeft.setPosition(100, 1080 / 2f - arrowLeft.getWidth() / 2f);
        arrowLeft.addListener(soundOnTouchDown());
        return arrowLeft;
    }

    public static ImageButton createConfirmButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.CONFIRM.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.CONFIRM_PRSS.fileName));
        ImageButton confirm = new ImageButton(buttonUp, buttonDown);
        confirm.setPosition(1920 / 2f - confirm.getWidth() / 2, 100);
        confirm.addListener(soundOnTouchDown());
        return confirm;
    }

    public static ImageButton createResetButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.RESET.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.RESET_PRESS.fileName));
        ImageButton confirm = new ImageButton(buttonUp, buttonDown);
        confirm.setPosition(1920 / 2f - confirm.getWidth() / 2, 100);
        confirm.addListener(soundOnTouchDown());
        return confirm;
    }

    public static ImageButton createBackButton(){
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.BACK.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.BACK_PRESS.fileName));
        ImageButton back = new ImageButton(buttonUp, buttonDown);
        back.setPosition(0, 1080 - back.getHeight());
        back.addListener(soundOnTouchDown());
        return back;
    }
}
