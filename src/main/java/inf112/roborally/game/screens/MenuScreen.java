package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.tools.AssMan;

public class MenuScreen implements Screen {

    private RoboRallyGame roboRallyGame;
    private SpriteBatch batch;
    private Sprite background;
    private Viewport viewport;
    private int stateTimer;

    private Stage stage;

    public MenuScreen(RoboRallyGame roboRallyGame) {
        this.roboRallyGame = roboRallyGame;
        batch = new SpriteBatch();
        batch.enableBlending();

        viewport = new FitViewport(1920, 1080);

        background = new Sprite(AssMan.manager.get(AssMan.MENUSCREEN_CHOICES));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(roboRallyGame.fixedViewPort, roboRallyGame.batch);
        stateTimer = 0;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        float r = 0 / 255f;
        float g = 20 / 255f;
        float b = 15 / 255f;

        Gdx.gl.glClearColor(r, g, b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        background.setAlpha(stateTimer / 255f);
        if (stateTimer < 255) stateTimer++;
        batch.begin();
        background.draw(batch);
        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            roboRallyGame.createGameScreen();
            roboRallyGame.setScreen(roboRallyGame.gameScreen);
            roboRallyGame.AIvsAI = false;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            roboRallyGame.AIvsAI = false;
            roboRallyGame.createSetupScreen();
            roboRallyGame.setScreen(roboRallyGame.setupScreen);
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            roboRallyGame.AIvsAI = false;
            dispose();
            roboRallyGame.setScreen(roboRallyGame.testScreen);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            roboRallyGame.AIvsAI = false;
            dispose();
            roboRallyGame.setScreen(roboRallyGame.laserTestScreen);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            roboRallyGame.AIvsAI = true;
            roboRallyGame.createGameScreen();
            roboRallyGame.setScreen(roboRallyGame.gameScreen);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {

            roboRallyGame.AIvsAI = false;
            roboRallyGame.setLauncTestMap(true);
            roboRallyGame.createGameScreen();
            roboRallyGame.setScreen(roboRallyGame.gameScreen);
        }

    }

    @Override
    public void resize(int w, int h) {
        viewport.update(w, h);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
    }
}
