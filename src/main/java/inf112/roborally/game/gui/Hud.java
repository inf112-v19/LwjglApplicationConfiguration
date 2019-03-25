package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.objects.Player;

public class Hud {

    public Stage stage;
    public Group registerGui;
    public Group lockGui;
    public Group cardsGui;

    private CardsInHandDisplay cardsInHandDisplay;
    private ProgramRegisterDisplay programRegisterDisplay;
    private Player player;
    private ImageButton submitButton;
    private ImageButton greySubmitButton;
    private ImageButton clearButton;
    private ImageButton settingsButton;


    private final RoboRallyGame game;


    public Hud(final Player player, final RoboRallyGame game) {
        this.player = player;
        this.game = game;
        stage = new Stage(game.fixedViewPort, game.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addListener(game.cameraListener);

        registerGui = new Group();
        cardsGui = new Group();
        lockGui = new Group();

        stage.addActor(registerGui);
        stage.addActor(cardsGui);
        stage.addActor(lockGui);



        cardsInHandDisplay = new CardsInHandDisplay(player, this);
        programRegisterDisplay = new ProgramRegisterDisplay(player, registerGui, lockGui);

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
                clearAllCards();
                clearAllCards();
                clearAllCards();
                clearAllCards();
                updateCards();
            }
        });


        settingsButton = new ImageButton(new TextureRegionDrawable(new Texture("assets/img/settingsbtn.png")));
//        settingsButton.setSize(settingsButton.getWidth() * 3, settingsButton.getHeight() * 3);
        settingsButton.setPosition(60, 60);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.settingsScreen);
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
        batch.begin();
        programRegisterDisplay.draw(batch);
        batch.end();

        stage.draw();

        if(!(game.getGameScreen().getGameLogic().getState() == GameState.PICKING_CARDS)){
            clearAllCards();
            clearAllCards();
            clearAllCards();
            programRegisterDisplay.drawCardsInProgramRegister(this);
            stage.draw();
        }else{
            stage.draw();
        }
    }


    /**
     * Remove all program card buttons.
     * Might need to call this function several times to actually remove all buttons. (Weird bug)
     */
    public void clearAllCards() {
        for (Actor button : cardsGui.getChildren()) {
            if (button instanceof ProgramCardButton) {
                button.remove();
            }
        }
    }

    /**
     * Updates program cards in hand and program cards in register visually.
     */
    @SuppressWarnings("Duplicates")
    public void updateCards() {
        clearAllCards();
        cardsInHandDisplay.updateCardsInHand(this);
        programRegisterDisplay.drawCardsInProgramRegister(this);
    }
}
