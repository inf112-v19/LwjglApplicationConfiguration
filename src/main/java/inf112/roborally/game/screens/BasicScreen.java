package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.tools.ButtonFactory;

/**
 * A basic screen that has and renders a stage. Subclasses simply add actors to the stage.
 */
public abstract class BasicScreen implements Screen {
    protected final RoboRallyGame game;
    protected final Stage stage;
    protected final Label.LabelStyle labelStyle;
    protected ImageButton back;
    protected Group background;

    public BasicScreen(RoboRallyGame game) {
        this.game = game;
        stage = new Stage(game.fixedViewPort, game.batch);
        labelStyle = new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), Color.WHITE);
        background = new Group();
        back = ButtonFactory.createBackButton();
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToPreviousScreen();
            }
        });

        stage.addActor(background);
        stage.addActor(back);
    }

    protected void goToPreviousScreen(){
        game.setScreen(game.getScreenBefore());
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
        stage.act();
        stage.draw();
    }

    protected abstract void handleInput();

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
