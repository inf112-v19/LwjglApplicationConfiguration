package inf112.roborally.game.screens.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

public class MultiplayerScreen implements Screen {
    private final Stage stage;
    private final RoboRallyGame game;

    public MultiplayerScreen(final RoboRallyGame game) {
        this.game = game;
        stage = new Stage(game.fixedViewPort, game.batch);

        BitmapFont font = AssMan.manager.get(AssMan.FONT_GROTESKIA);
        font.getData().setScale(4);
        TextureRegionDrawable up = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_UP));
        TextureRegionDrawable down = new TextureRegionDrawable(AssMan.manager.get(AssMan.TEXT_BUTTON_PRESS));
        TextButton join = new TextButton("Join Session", new TextButton.TextButtonStyle(up, down, up, font));
        join.setTransform(true);
        join.setWidth(join.getWidth() * 1.5f);
        join.setPosition(1920 / 2f - join.getWidth() / 2f, 400);
        join.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screen screen = new ServerIpScreen(game);
                game.setScreen(screen);
                dispose();
            }
        });
        TextButton create = new TextButton("Create Session", new TextButton.TextButtonStyle(up, down, up, font));
        create.setTransform(true);
        create.setWidth(create.getWidth() * 1.5f);
        create.setPosition(1920 / 2f - create.getWidth() / 2f, 200);
        create.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screen screen = new HostScreen(game);
                game.setScreen(screen);
                dispose();
            }
        });
        stage.addActor(join);
        stage.addActor(create);
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) ;
    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
