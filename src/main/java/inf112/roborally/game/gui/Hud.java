package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.roborally.game.objects.Player;

public class Hud {

    private CardsInHandDisplay cardsInHandDisplay;
    private ProgramRegisterDisplay programRegisterDisplay;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch batch;

    public Hud(final Player player) {
        camera = new OrthographicCamera();
        camera.update();
        viewport = new FitViewport(1920, 1080, camera);
        batch = new SpriteBatch();
        cardsInHandDisplay = new CardsInHandDisplay(player, new Stage(viewport, batch));
        programRegisterDisplay = new ProgramRegisterDisplay(player, camera);
        batch.setProjectionMatrix(cardsInHandDisplay.stage.getCamera().combined);
    }

    public void draw() {
        batch.begin();
        programRegisterDisplay.draw(batch, camera);
        batch.end();
        cardsInHandDisplay.stage.draw();
    }

    public CardsInHandDisplay getCardsInHandDisplay(){
        return cardsInHandDisplay;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
