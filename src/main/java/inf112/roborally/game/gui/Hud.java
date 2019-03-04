package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Player;

public class Hud {

    private CardsInHandDisplay cardsInHandDisplay;
    ProgramRegisterDisplay programRegisterDisplay;
    private OrthographicCamera camera;
    private FitViewport viewport;

    public Hud(Player player, RoboRallyGame game) {
        cardsInHandDisplay = new CardsInHandDisplay(player, new Stage(game.viewPort, game.batch));
        programRegisterDisplay = new ProgramRegisterDisplay(player);

    }

    public void draw(RoboRallyGame game) {
        game.batch.begin();
        programRegisterDisplay.draw(game.batch, game.camera);
        game.batch.end();

        game.batch.setProjectionMatrix(cardsInHandDisplay.stage.getCamera().combined);
        cardsInHandDisplay.stage.draw();
    }

    public CardsInHandDisplay getCardsInHandDisplay(){
        return cardsInHandDisplay;
    }
}
