package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

public class SelectSkinScreen implements Screen {
    private final RoboRallyGame game;

    private Stage stage;
    private int selectedSkin;
    private TextureRegionDrawable[] skins;
    private ImageButton next;
    private ImageButton previous;
    private ImageButton confirm;
    private Image currentSkin;
    private Boolean clicked = false;

    public SelectSkinScreen(RoboRallyGame game) {
        this.game = game;
        this.stage = new Stage(game.fixedViewPort, game.batch);

        selectedSkin = 0;
        skins = new TextureRegionDrawable[8];
        for (int i = 0; i < 8; i++) {
            skins[i] = new TextureRegionDrawable(new Texture(AssMan.CAPTAIN_BOT.fileName));
        }
        currentSkin = new Image(skins[selectedSkin]);
        currentSkin.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);

        stage.addActor(next);
        stage.addActor(previous);
        stage.addActor(confirm);
        stage.addActor(currentSkin);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        if (clicked) update(); // don't update unless we have to
        stage.draw();
    }

    private void update() {
        clicked = false;
        currentSkin.setDrawable(skins[selectedSkin]);
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
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
