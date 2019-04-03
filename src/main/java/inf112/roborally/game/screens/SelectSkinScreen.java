package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

public class SelectSkinScreen implements Screen {
    private final RoboRallyGame game;

    private Stage stage;
    private TextureRegionDrawable[] skins;
    private int skinIndex;
    private Image currentSkin;
    private Boolean clicked = false;

    public SelectSkinScreen(final RoboRallyGame game) {
        this.game = game;
        this.stage = new Stage(game.fixedViewPort, game.batch);
        //Add the background:
        Image background = new Image(new TextureRegionDrawable(new Texture(AssMan.SELECT_SCREEN.fileName)));
        stage.addActor(background);
        //Create buttons:
        TextureRegionDrawable button = new TextureRegionDrawable(new Texture(AssMan.RIGHT_ARROW.fileName));
        TextureRegionDrawable buttonPressed = new TextureRegionDrawable(new Texture(AssMan.RIGHT_ARROW_PRESS.fileName));
        ImageButton next = new ImageButton(button, buttonPressed);
        next.setPosition(1920 - next.getWidth() - 100, 1080 / 2f - next.getHeight() / 2f);
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                skinIndex = (++skinIndex) % 8;
                clicked = true;
            }
        });
        button = new TextureRegionDrawable(new Texture(AssMan.LEFT_ARROW.fileName));
        buttonPressed = new TextureRegionDrawable(new Texture(AssMan.LEFT_ARROW_PRESS.fileName));
        ImageButton previous = new ImageButton(button, buttonPressed);
        previous.setPosition(100, 1080 / 2f - previous.getWidth() / 2f);
        previous.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                skinIndex = (8 + --skinIndex) % 8;
                clicked = true;
            }
        });
        button = new TextureRegionDrawable(new Texture(AssMan.CONFIRM.fileName));
        buttonPressed = new TextureRegionDrawable(new Texture(AssMan.CONFIRM_PRSS.fileName));
        ImageButton confirm = new ImageButton(button, buttonPressed);
        confirm.setPosition(1920 / 2f - confirm.getWidth() / 2, 100);
        confirm.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                game.setScreen(game.setupScreen);
            }
        });
        stage.addActor(next);
        stage.addActor(previous);
        stage.addActor(confirm);
        // Robot skins:
        skins = new TextureRegionDrawable[8];
        for (int i = 0; i < 8; i++) {
            skins[i] = new TextureRegionDrawable(new Texture(AssMan.getPlayerSkins()[i]));
        }
        skinIndex = 0;
        currentSkin = new Image(skins[skinIndex]);
        currentSkin.setPosition(1920 / 2f - currentSkin.getWidth() / 2, 1080 / 2f - currentSkin.getHeight() / 2);
        stage.addActor(currentSkin);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        if (clicked) update(); // don't update unless we have to

        Gdx.gl.glClearColor(0 / 255f, 20 / 255f, 15 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            Gdx.app.exit();
        }
    }

    private void update() {
        clicked = false;
        currentSkin.setDrawable(skins[skinIndex]);
    }

    @Override
    public void resize(int width, int height) {
        game.fixedViewPort.update(width, height);
        game.dynamicViewPort.update(width, height);
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
