package inf112.roborally.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {

    public static ImageButton createArrowRightButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.RIGHT_ARROW.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.RIGHT_ARROW_PRESS.fileName));
        ImageButton arrowRight = new ImageButton(buttonUp, buttonDown);
        arrowRight.setPosition(1920 - arrowRight.getWidth() - 100, 1080 / 2f - arrowRight.getHeight() / 2f);
        return arrowRight;
    }

    public static ImageButton createArrowLeftButton(){
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.LEFT_ARROW.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.LEFT_ARROW_PRESS.fileName));
        ImageButton arrowLeft = new ImageButton(buttonUp, buttonDown);
        arrowLeft.setPosition(100, 1080 / 2f - arrowLeft.getWidth() / 2f);
        return arrowLeft;
    }

    public static ImageButton createConfirmButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.CONFIRM.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.CONFIRM_PRSS.fileName));
        ImageButton confirm = new ImageButton(buttonUp, buttonDown);
        confirm.setPosition(1920 / 2f - confirm.getWidth() / 2, 100);
        return confirm;
    }

    public static ImageButton createBackButton(){
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(new Texture(AssMan.BACK.fileName));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(new Texture(AssMan.BACK_PRESS.fileName));
        ImageButton back = new ImageButton(buttonUp, buttonDown);
        back.setPosition(0, 1080 - back.getHeight());
        return back;
    }
}
