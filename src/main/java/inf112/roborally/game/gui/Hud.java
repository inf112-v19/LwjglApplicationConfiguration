package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
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
    public ImageButton submitButton;
    public ImageButton greySubmitButton;
    public ImageButton clearButton;

    public Hud(final Player player, RoboRallyGame game) {
        this.player = player;
        stage = new Stage(game.fixedViewPort, game.batch);
        cardsInHandDisplay = new CardsInHandDisplay(this, player, stage);
        programRegisterDisplay = new ProgramRegisterDisplay(player);

        submitButton = new ImageButton(new TextureRegionDrawable(new Texture(
                "assets/cards/buttonSubmit.png")));
        submitButton.setSize(submitButton.getWidth()/2, submitButton.getHeight()/2);
        submitButton.setPosition((1920/2), 260);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.playerState = PlayerState.READY;
            }
        });

        greySubmitButton = new ImageButton(new TextureRegionDrawable(new Texture(
                "assets/cards/buttonSubmitGrey.png")));
        greySubmitButton.setSize(greySubmitButton.getWidth()/2, greySubmitButton.getHeight()/2);
        greySubmitButton.setPosition((1920/2), 260);
        greySubmitButton.addListener(new ClickListener());

        clearButton = new ImageButton(new TextureRegionDrawable(new Texture(
                "assets/cards/buttonClear.png")));
        clearButton.setSize(clearButton.getWidth()/2, clearButton.getHeight()/2);
        clearButton.setPosition((1920/2)-(clearButton.getWidth()), 260);
        clearButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.getRegisters().returnCards(player.getCardsInHand());
                // messy but it works:
                ((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getHud().getCardsInHandDisplay().
                        updateCardsInHandVisually();
            }
        });


        stage.addActor(greySubmitButton);
        stage.addActor(submitButton);
        stage.addActor(clearButton);

    }

    public void draw(SpriteBatch batch) {
        submitButton.setVisible(player.getRegisters().isFull());
        greySubmitButton.setVisible(!submitButton.isVisible());
        batch.begin();
        programRegisterDisplay.draw(batch);
        batch.end();
        stage.draw();
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
