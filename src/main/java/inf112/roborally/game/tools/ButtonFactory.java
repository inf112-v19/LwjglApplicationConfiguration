package inf112.roborally.game.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

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
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(AssMan.manager.get(AssMan.RIGHT_ARROW));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(AssMan.manager.get(AssMan.RIGHT_ARROW_PRESS));
        ImageButton arrowRight = new ImageButton(buttonUp, buttonDown);
        arrowRight.setPosition(1920 - arrowRight.getWidth() - 100, 1080 / 2f - arrowRight.getHeight() / 2f);
        arrowRight.addListener(soundOnTouchDown());
        return arrowRight;
    }

    public static ImageButton createArrowLeftButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(AssMan.manager.get(AssMan.LEFT_ARROW));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(AssMan.manager.get(AssMan.LEFT_ARROW_PRESS));
        ImageButton arrowLeft = new ImageButton(buttonUp, buttonDown);
        arrowLeft.setPosition(100, 1080 / 2f - arrowLeft.getWidth() / 2f);
        arrowLeft.addListener(soundOnTouchDown());
        return arrowLeft;
    }

    public static ImageButton createBackButton() {
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(AssMan.manager.get(AssMan.BACK));
        TextureRegionDrawable buttonDown = new TextureRegionDrawable(AssMan.manager.get(AssMan.BACK_PRESS));
        ImageButton back = new ImageButton(buttonUp, buttonDown);
        back.setPosition(0, 1080 - back.getHeight());
        back.addListener(soundOnTouchDown());
        return back;
    }

    public static TextButton createTextButton(String text, float scale) {
        BitmapFont font = AssMan.manager.get(AssMan.FONT_GROTESKIA);
        TextureRegionDrawable up = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_UP));
        TextureRegionDrawable down = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_PRESS));
        final TextButton button = new TextButton(text, new TextButton.TextButtonStyle(up, down, up, font));
        button.setColor(Color.BLACK);
        button.addListener(new ClickListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.setColor(Color.BLACK);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setColor(Color.WHITE);
            }
        });
        button.setTransform(true);
        button.setSize((button.getWidth() + 50) * scale, button.getHeight() * scale);
        button.getLabel().setFontScale(scale);
        button.setOrigin(Align.center);
        button.setPosition(1920 / 2f - button.getWidth() / 2f, 140);
        button.addListener(soundOnTouchDown());
        button.pad(10 * scale);
        return button;
    }

    public static Label createLabel(String text, Color color) {
        return new Label(text, new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), color));
    }
}
