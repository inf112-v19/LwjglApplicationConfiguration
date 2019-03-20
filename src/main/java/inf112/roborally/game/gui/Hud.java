package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Player;

public class Hud {

    private CardsInHandDisplay cardsInHandDisplay;
    private ProgramRegisterDisplay programRegisterDisplay;
    private SoundSettings muteBtn;


    public Hud(final Player player, RoboRallyGame game) {
        cardsInHandDisplay = new CardsInHandDisplay(player, new Stage(game.fixedViewPort, game.batch));
        programRegisterDisplay = new ProgramRegisterDisplay(player);
//        muteBtn = new SoundSettings(new Stage(viewport, batch), 1000, 950);

    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        programRegisterDisplay.draw(batch);
        batch.end();
//        muteBtn.stage.draw();
        cardsInHandDisplay.stage.draw();
    }

    public CardsInHandDisplay getCardsInHandDisplay(){
        return cardsInHandDisplay;
    }

    public void dispose(){
        System.out.println("disposing hud");
        cardsInHandDisplay.dispose();
        programRegisterDisplay.dispose();
//        muteBtn.dispose();
    }
}
