package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.Main;
import inf112.roborally.game.ProgramCard;
import inf112.roborally.game.ProgramRegisters;
import inf112.roborally.game.gui.CardVisuals;
import inf112.roborally.game.gui.RegisterVisuals;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.Rotate;

public class TestScreen implements Screen {
    private SpriteBatch batch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private CardVisuals cardVisuals;
    private ProgramCard programCard;
    private RegisterVisuals registerVisuals;
    private Player player;

    public TestScreen() {
        batch = new SpriteBatch();
        batch.enableBlending();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        cardVisuals = new CardVisuals();
        programCard = new ProgramCard(Rotate.NONE,3,1);

        ProgramRegisters programRegisters= new ProgramRegisters();
        player = new Player(0,0);
        registerVisuals = new RegisterVisuals(player);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        float r = 0/255f;
        float g = 20/255f;
        float b = 15/255f;

        Gdx.gl.glClearColor(r,g,b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        registerVisuals.draw(batch, camera, viewport);

//        int i = 1;
//        cardVisuals.drawCard(new ProgramCard(Rotate.NONE, 0, 0),96*i++,32, batch);
//        cardVisuals.drawCard(new ProgramCard(Rotate.NONE, 1, 0),96*i++,32, batch);
//        cardVisuals.drawCard(new ProgramCard(Rotate.NONE, 2, 0),96*i++,32, batch);
//        cardVisuals.drawCard(new ProgramCard(Rotate.NONE, 3, 0),96*i++,32, batch);
//        i = 1;
//        cardVisuals.drawCard(new ProgramCard(Rotate.UTURN, 0, 0),96*i++,148, batch);
//        cardVisuals.drawCard(new ProgramCard(Rotate.LEFT, 0, 0),96*i++,148, batch);
//        cardVisuals.drawCard(new ProgramCard(Rotate.RIGHT, 0, 0),96*i++,148, batch);
        batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            player.takeDamage();
            player.update();
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

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
