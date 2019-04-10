package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;


public class NameScreen implements Screen {
    private final RoboRallyGame game;
    private Stage stage;
    private TextField text;
    private boolean clicked = false;

    public NameScreen(RoboRallyGame game) {
        this.game = game;
        stage = new Stage(game.fixedViewPort, game.batch);
        BitmapFont font = new BitmapFont();
        font.getData().scale(10);
        text = new TextField("'Your name'", new TextField.TextFieldStyle(font, Color.WHITE, null, null,
                new TextureRegionDrawable(AssMan.manager.get(AssMan.GAMESCREEN_BACKGROUND2))));
        text.setSize(1000, 200);
        text.setPosition(1920 / 2, 600, Align.center);
        text.setAlignment(Align.center);
        text.setMaxLength(10);
        text.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!clicked) text.setText("");
                clicked = true;
            }
        });
        stage.addActor(text);
        ImageButton confirm = new ImageButton(new TextureRegionDrawable(AssMan.manager.get(AssMan.BUTTON_SUBMIT)));
        confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!clicked || text.getText().length() < 3) return;
                String name = text.getText();
                System.out.println(name);
            }
        });
        confirm.setScale(2);
        confirm.setPosition(1920 / 2f - confirm.getWidth() / 2f, 200);
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

    private void handleInput() {
    }

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
