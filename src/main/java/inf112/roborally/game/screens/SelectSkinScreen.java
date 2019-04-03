package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

public class SelectSkinScreen implements Screen {
    private final RoboRallyGame game;

    private Stage stage;
    private ImageButton next;
    private ImageButton previous;
    private ImageButton confirm;
    private int selectedSkin;
    private TextureRegionDrawable[] skins;
    private Image currentSkin;
    private Boolean clicked = false;

    public SelectSkinScreen(RoboRallyGame game) {
        this.game = game;
        this.stage = new Stage(game.fixedViewPort, game.batch);

//        next = new ImageButton();
//        previous = new ImageButton();
//        confirm = new ImageButton();
//        stage.addActor(next);
//        stage.addActor(previous);
//        stage.addActor(confirm);

        selectedSkin = 0;
        skins = new TextureRegionDrawable[8];
        for (int i = 0; i < 8; i++) {
            skins[i] = new TextureRegionDrawable(new Texture(AssMan.getPlayerSkins()[i]));
        }
        currentSkin = new Image(skins[selectedSkin]);
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            clicked = true;
            selectedSkin++;
            selectedSkin = selectedSkin % 8;
        }

        Gdx.gl.glClearColor(0 / 255f, 20 / 255f, 15 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    private void update() {
        clicked = false;
        currentSkin.setDrawable(skins[selectedSkin]);
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
