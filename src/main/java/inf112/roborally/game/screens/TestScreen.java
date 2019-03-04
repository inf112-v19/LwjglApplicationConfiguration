package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.gui.CardVisuals;
import inf112.roborally.game.gui.ProgramRegisterDisplay;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.enums.Rotate;

public class TestScreen implements Screen {
    private SpriteBatch batch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private CardVisuals cardVisuals;
    private ProgramCard programCard;
    private ProgramRegisterDisplay programRegisterDisplay;
    private Player player;
    private Sprite background;


    public TestScreen() {
        batch = new SpriteBatch();
        batch.enableBlending();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        cardVisuals = new CardVisuals();
        programCard = new ProgramCard(Rotate.NONE,3,1);

        player = new Player(0,0);
        programRegisterDisplay = new ProgramRegisterDisplay(player, camera);
        for(int i = 0; i < 9; i++){
            player.receiveCard(new ProgramCard(Rotate.NONE,2,0));
        }

        background = new Sprite(new Texture("assets/img/testscreen.png"));
        background.setPosition(background.getX()-705, background.getY());
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
        background.draw(batch);
        programRegisterDisplay.draw(batch, camera);
        batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            player.takeDamage();
            player.update();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)){
            player.getRegisters().pickCard(0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)){
            player.returnCards();
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
