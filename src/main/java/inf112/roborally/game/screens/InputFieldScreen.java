package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.tools.ButtonFactory;

public abstract class InputFieldScreen extends BasicScreen {
    protected TextField text;
    protected boolean clicked = false;
    protected TextButton confirm;
    protected ImageButton left, right;

    public InputFieldScreen(RoboRallyGame game) {
        super(game);
        BitmapFont font = AssMan.manager.get(AssMan.FONT_GROTESKIA);
        font.getData().setScale(4);
        TextureRegionDrawable textFieldBg = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_FIELD_BG));
        TextureRegionDrawable cursor = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_FIELD_CURSOR));
        text = new TextField("'Enter something here'",
                new TextField.TextFieldStyle(font, Color.WHITE, cursor, null, textFieldBg));
        text.setSize(1000, 200);
        text.setPosition(1920 / 2, 600, Align.center);
        text.setAlignment(Align.center);
        text.setMaxLength(20);
        text.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!clicked) text.setText("");
                clicked = true;
            }
        });
        confirm = ButtonFactory.createTextButton("Confirm");
        confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmInput();
            }
        });
        left = ButtonFactory.createArrowLeftButton();
        right = ButtonFactory.createArrowRightButton();

        stage.addActor(confirm);
        stage.addActor(text);
        stage.addActor(left);
        stage.addActor(right);

        setArrowsVisible(false);
    }

    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) confirmInput();
    }
    protected  void setFieldVisible(boolean visible){
        text.setVisible(visible);
    }

    protected void setArrowsVisible(boolean visible){
        left.setVisible(visible);
        right.setVisible(visible);
    }

    protected abstract boolean confirmInput();

}
