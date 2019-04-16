package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.ButtonFactory;

public abstract class AbstractSelectionScreen implements Screen {
    protected RoboRallyGame game;
    protected Stage stage;
    protected Button next;
    protected Button previous;
    protected Button confirm;
    protected Button back;
    protected int index;
    protected int maxIndex;

    public AbstractSelectionScreen(RoboRallyGame game) {
        this.game = game;
        stage = new Stage(game.fixedViewPort, game.batch);
        next = ButtonFactory.createArrowRightButton();
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = ++index % maxIndex;
            }
        });
        previous = ButtonFactory.createArrowLeftButton();
        previous.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = (--index + maxIndex) % maxIndex;
            }
        });
        confirm = ButtonFactory.createTextButton("Confirm", 4);
        back = ButtonFactory.createBackButton();
        stage.addActor(next);
        stage.addActor(previous);
        stage.addActor(confirm);
        stage.addActor(back);
        index = 0;
        maxIndex = 1; // increase this in subclass if it has more than 2 options.
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

    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void resize(int h, int w) {
        game.fixedViewPort.update(h, w);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // Unimplemented methods


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
