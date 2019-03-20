package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    private SoundSettings muteBtn;
//    private Texture muteBtnTest;

    public Hud(final Player player) {
        camera = new OrthographicCamera();
        camera.update();
        viewport = new FitViewport(1920, 1080, camera);
        batch = new SpriteBatch();
        muteBtn = new SoundSettings(new Stage(viewport, batch), 1000, 950);
        cardsInHandDisplay = new CardsInHandDisplay(player, new Stage(viewport, batch));
        programRegisterDisplay = new ProgramRegisterDisplay(player, camera);
//        batch.setProjectionMatrix(cardsInHandDisplay.stage.getCamera().combined);

        // Use this next line?
//        batch.setProjectionMatrix(muteBtn.stage.getCamera().combined);

//        muteBtnTest = new Texture("assets/img/mute.png");
    }

    public void draw() {
        batch.begin();
        programRegisterDisplay.draw(batch, camera);
//        batch.draw(muteBtn.getTexture(), (1920/2) - (muteBtnTest.getWidth()/2), 1080/2);
        batch.end();
        muteBtn.stage.draw();
        cardsInHandDisplay.stage.draw();
    }

    public CardsInHandDisplay getCardsInHandDisplay(){
        return cardsInHandDisplay;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void dispose(){
        System.out.println("disposing hud");
        batch.dispose();
        cardsInHandDisplay.dispose();
        programRegisterDisplay.dispose();
        muteBtn.dispose();
    }
}
