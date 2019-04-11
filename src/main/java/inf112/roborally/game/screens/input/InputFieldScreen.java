package inf112.roborally.game.screens.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

public abstract class InputFieldScreen implements Screen {
    final RoboRallyGame game;
    Stage stage;
    TextField text;
    boolean clicked = false;

    public InputFieldScreen(RoboRallyGame game) {
        this.game = game;

        BitmapFont font = AssMan.manager.get(AssMan.FONT_GROTESKIA);
        font.getData().setScale(4);
        TextureRegionDrawable textFieldBg = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_FIELD_BG));
        TextureRegionDrawable cursor = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_FIELD_CURSOR));
        text = new TextField("'Your input'",
                new TextField.TextFieldStyle(font, Color.WHITE, cursor, null, textFieldBg));
        text.setSize(1000, 200);
        text.setPosition(1920 / 2, 600, Align.center);
        text.setAlignment(Align.center);
        text.setMaxLength(15);
        text.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!clicked) text.setText("");
                clicked = true;
            }
        });
        TextureRegionDrawable up = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_UP));
        TextureRegionDrawable press = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_PRESS));
        TextureRegionDrawable checked = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_CHECKED));
        TextButton confirm = new TextButton("Confirm", new TextButton.TextButtonStyle(up, press, checked, font));
        confirm.setTransform(true);
        confirm.setWidth(confirm.getWidth() * 1.5f);
        confirm.setPosition(1920 / 2f - confirm.getWidth() / 2f, 200);
        confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmInput();
            }
        });
        stage = new Stage(game.fixedViewPort, game.batch);
        stage.addActor(text);
        stage.addActor(confirm);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        handleInput();
        Gdx.gl.glClearColor(0 / 255f, 20 / 255f, 15 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) confirmInput();
    }

    abstract boolean confirmInput();

    @Override
    public void resize(int i, int i1) {
        game.fixedViewPort.update(i, i1);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
