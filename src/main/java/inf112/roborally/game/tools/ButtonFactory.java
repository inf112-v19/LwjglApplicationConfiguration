package inf112.roborally.game.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {

    public static ClickListener soundOnTouchDown() {
        return new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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

    public static ImageButton createArrowLeftButton() {
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

    public static ImageButton createBackButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.BACK.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.BACK_PRESS.fileName));
        ImageButton back = new ImageButton(buttonUp, buttonDown);
        back.setPosition(0, 1080 - back.getHeight());
        back.addListener(soundOnTouchDown());
        return back;
    }

    public static TextButton createTextButton(String text) {
        BitmapFont font = AssMan.manager.get(AssMan.FONT_GROTESKIA);
        font.getData().setScale(2);
        TextureRegionDrawable up = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_UP));
        TextureRegionDrawable down = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_PRESS));
        TextButton button = new TextButton(text, new TextButton.TextButtonStyle(up, down, up, font));
        button.setTransform(true);
        button.setWidth(480);
        button.setPosition(1920 / 2f - button.getWidth() / 2f, 200);
        button.addListener(soundOnTouchDown());
        return button;
    }

    public static Label createLabel(String text, Color color){
        return new Label(text, new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), color));
    }
}
