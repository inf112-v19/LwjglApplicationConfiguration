package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

public class PlaceFlagsScreen implements Screen {
    protected final RoboRallyGame game;

    private Stage stage;

    private Image map;
    // Width and heigth AFTER "scaling"
    private float mapWidth;
    private float mapHeight;

    // Choices from the last screens
    private String mapFilepath;
    private String robotFilepath;


    public PlaceFlagsScreen(final RoboRallyGame game, String mapFilepath, String robotFilepath) {
        this.game = game;
        this.stage = new Stage(game.fixedViewPort, game.batch);
        this.mapFilepath = mapFilepath;
        this.robotFilepath = robotFilepath;

        Image background = new Image(new TextureRegionDrawable(new Texture(AssMan.GAMESCREEN_BACKGROUND2.fileName)));
        stage.addActor(background);

        map = new Image(new TextureRegionDrawable(new Texture(mapFilepath)));
        mapWidth = map.getWidth() * 2.5f;
        mapHeight = map.getHeight() * 2.5f;
        map.setSize(mapWidth, mapHeight);
        map.setPosition(1920 / 2f - map.getWidth() / 2, 1080 / 2f - map.getHeight() / 2);
        stage.addActor(map);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0 / 255f, 20 / 255f, 15 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            Gdx.app.exit();
        }

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
