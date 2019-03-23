package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.objects.Player;

public class Hud {

    private CardsInHandDisplay cardsInHandDisplay;
    private ProgramRegisterDisplay programRegisterDisplay;
    private CardDisplay cardDisplay;
    private Stage stage;
    private Player player;
    private ImageButton submitButton;
    private ImageButton greySubmitButton;
    private ImageButton clearButton;
    private ImageButton settingsButton;


    public Hud(final Player player, RoboRallyGame game) {
        this.player = player;
        stage = new Stage(game.fixedViewPort, game.batch);
        cardsInHandDisplay = new CardsInHandDisplay(player, stage);
        programRegisterDisplay = new ProgramRegisterDisplay(player, stage);
        cardDisplay = new CardDisplay(programRegisterDisplay, cardsInHandDisplay);

        float scale = 0.4f;
        submitButton = new ImageButton(new TextureRegionDrawable(new Texture(
                "assets/cards/buttonSubmit.png")));
        submitButton.setSize(submitButton.getWidth() * scale, submitButton.getHeight() * scale);
        submitButton.setPosition((1920 / 2) + 7, 260);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.playerState = PlayerState.READY;
            }
        });


        greySubmitButton = new ImageButton(new TextureRegionDrawable(new Texture(
                "assets/cards/buttonSubmitGrey.png")));
        greySubmitButton.setSize(submitButton.getWidth(), submitButton.getHeight());
        greySubmitButton.setPosition(submitButton.getX(), submitButton.getY());
        greySubmitButton.addListener(new ClickListener());

        clearButton = new ImageButton(new TextureRegionDrawable(new Texture(
                "assets/cards/buttonClear.png")));
        clearButton.setSize(clearButton.getWidth() * scale, clearButton.getHeight() * scale);
        clearButton.setPosition((1920 / 2) - (clearButton.getWidth()) - 7, 260);
        clearButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.getRegisters().returnCards(player);
                cardDisplay.update();

                // messy but it works:
               /* ((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getHud().getCardsInHandDisplay().
                        updateCardsInHandVisually();*/

                /*((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getHud().getCardsInHandDisplay().
                        updateCardsV();
*/
                //((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getHud().getCardDisplay().update();
                //((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getHud().getCardDisplay().update();
                //((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getHud().getCardDisplay().update();


            }
        });


        settingsButton = new ImageButton(new TextureRegionDrawable(new Texture("assets/img/settingsbtn.png")));
//        settingsButton.setSize(settingsButton.getWidth() * 3, settingsButton.getHeight() * 3);
        settingsButton.setPosition(60, 60);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Also kind of messy
                ((RoboRallyGame) Gdx.app.getApplicationListener()).setScreen(
                        ((RoboRallyGame) Gdx.app.getApplicationListener()).settingsScreen);
            }
        });

        stage.addActor(greySubmitButton);
        stage.addActor(submitButton);
        stage.addActor(clearButton);
        stage.addActor(settingsButton);

    }

    public void draw(SpriteBatch batch) {
        submitButton.setVisible(player.getRegisters().isFull());
        greySubmitButton.setVisible(!submitButton.isVisible());
        //cardDisplay.update();
        batch.begin();
        programRegisterDisplay.draw(batch);
        batch.end();
        //cardDisplay.update();

        if(((RoboRallyGame)Gdx.app.getApplicationListener()).gameScreen.getGameLogic().getState() == GameState.PICKING_CARDS){
            stage.draw();
        }
    }

    public CardsInHandDisplay getCardsInHandDisplay() {
        return cardsInHandDisplay;
    }

    public ProgramRegisterDisplay getProgramRegisterDisplay(){
        return programRegisterDisplay;
    }

    public CardDisplay getCardDisplay(){
        return cardDisplay;
    }
    public void dispose() {
        System.out.println("disposing hud");
        cardsInHandDisplay.dispose();
        programRegisterDisplay.dispose();
    }
}
