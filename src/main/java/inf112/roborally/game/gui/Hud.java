package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.objects.Player;

public class Hud {

    private CardsInHandDisplay cardsInHandDisplay;
    private ProgramRegisterDisplay programRegisterDisplay;
    private Stage stage;
    private Player player;

    public Hud(final Player player, RoboRallyGame game) {
        this.player = player;
        stage = new Stage(game.fixedViewPort, game.batch);
        cardsInHandDisplay = new CardsInHandDisplay(player, new Stage(game.fixedViewPort, game.batch));
        programRegisterDisplay = new ProgramRegisterDisplay(player);
        ImageButton submitButton = new ImageButton(new TextureRegionDrawable(new Texture(
                "assets/cards/buttons.png")));
        submitButton.setSize(submitButton.getWidth()/2, submitButton.getHeight()/2);
        submitButton.setPosition((1920/2)-(submitButton.getWidth()/2), 260);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.playerState = PlayerState.READY;
            }
        });
        stage.addActor(submitButton);

    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        programRegisterDisplay.draw(batch);
        batch.end();
        cardsInHandDisplay.stage.draw();
        if(player.getRegisters().isFull()) {
            Gdx.input.setInputProcessor(stage);
            stage.draw();
        } else {
            Gdx.input.setInputProcessor(cardsInHandDisplay.stage);
        }
    }

    public CardsInHandDisplay getCardsInHandDisplay(){
        return cardsInHandDisplay;
    }

    public void dispose(){
        System.out.println("disposing hud");
        cardsInHandDisplay.dispose();
        programRegisterDisplay.dispose();
    }
}
