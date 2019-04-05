package inf112.roborally.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.sound.GameSound;

public class ButtonFactory {

    public static ClickListener clicOnTouchDown(){
        final GameSound sound = new GameSound("assets/soundeffects/buttons_and_clicks/Clic07.mp3");
        return new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                sound.play();
                return true;
            }
        };
    }

    public static ImageButton createArrowRightButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.RIGHT_ARROW.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.RIGHT_ARROW_PRESS.fileName));
        ImageButton arrowRight = new ImageButton(buttonUp, buttonDown);
        arrowRight.setPosition(1920 - arrowRight.getWidth() - 100, 1080 / 2f - arrowRight.getHeight() / 2f);
        arrowRight.addListener(clicOnTouchDown());
        return arrowRight;
    }

    public static ImageButton createArrowLeftButton(){
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.LEFT_ARROW.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.LEFT_ARROW_PRESS.fileName));
        ImageButton arrowLeft = new ImageButton(buttonUp, buttonDown);
        arrowLeft.setPosition(100, 1080 / 2f - arrowLeft.getWidth() / 2f);
        arrowLeft.addListener(clicOnTouchDown());
        return arrowLeft;
    }

    public static ImageButton createConfirmButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.CONFIRM.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.CONFIRM_PRSS.fileName));
        ImageButton confirm = new ImageButton(buttonUp, buttonDown);
        confirm.setPosition(1920 / 2f - confirm.getWidth() / 2, 100);
        confirm.addListener(clicOnTouchDown());
        return confirm;
    }

    public static ImageButton createBackButton(){
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.BACK.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.BACK_PRESS.fileName));
        ImageButton back = new ImageButton(buttonUp, buttonDown);
        back.setPosition(0, 1080 - back.getHeight());
        back.addListener(clicOnTouchDown());
        return back;
    }
}
