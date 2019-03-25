package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private CardsInHandDisplay cardsInHandDisplay;
    private ProgramRegisterDisplay programRegisterDisplay;
    private CardDisplay cardDisplay;
    private Stage stage;
    private Player player;
    private ImageButton submitButton;
    private ImageButton greySubmitButton;
    private ImageButton clearButton;
    private ImageButton settingsButton;

    private final RoboRallyGame game;

    private AssMan assMan;
    private float scale = 0.4f;

    public Hud(final Player player, final RoboRallyGame game, AssMan assMan) {
        this.player = player;
        this.game = game;
        this.assMan = assMan;
        stage = new Stage(game.fixedViewPort, game.batch);
        cardsInHandDisplay = new CardsInHandDisplay(player, stage);
        programRegisterDisplay = new ProgramRegisterDisplay(player, stage);
        cardDisplay = new CardDisplay(programRegisterDisplay, cardsInHandDisplay);


    }


    public boolean createSubmitButton() {
        if (assMan.manager.isLoaded(AssMan.buttonSubmit.fileName)) {
            submitButton = new ImageButton(new TextureRegionDrawable(assMan.manager.get
                    (AssMan.buttonSubmit.fileName, Texture.class)));
            submitButton.setSize(submitButton.getWidth() * scale, submitButton.getHeight() * scale);
            submitButton.setPosition((1920 / 2) + 7, 260);
            submitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.playerState = PlayerState.READY;
                }
            });
            return true;
        } else {
            return false;
        }
    }

    public boolean createSubmitButtonGrey() {
        if (assMan.manager.isLoaded(AssMan.buttonSubmitGrey.fileName)) {
            greySubmitButton = new ImageButton(new TextureRegionDrawable(assMan.manager.get
                    (AssMan.buttonSubmitGrey.fileName, Texture.class)));
            greySubmitButton.setSize(submitButton.getWidth(), submitButton.getHeight());
            greySubmitButton.setPosition(submitButton.getX(), submitButton.getY());
            greySubmitButton.addListener(new ClickListener());
            return true;
        } else {
            return false;
        }
    }

    public boolean createButtonClear() {
        if (assMan.manager.isLoaded(AssMan.buttonClear.fileName)) {
            clearButton = new ImageButton(new TextureRegionDrawable(assMan.manager.get(
                    AssMan.buttonClear.fileName, Texture.class)));
            clearButton.setSize(clearButton.getWidth() * scale, clearButton.getHeight() * scale);
            clearButton.setPosition((1920 / 2) - (clearButton.getWidth()) - 7, 260);
            clearButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.getRegisters().returnCards(player);
                    cardDisplay.clearAllCards();
                    cardDisplay.clearAllCards();
                    cardDisplay.clearAllCards();
                    cardDisplay.clearAllCards();
                    cardDisplay.updateCards();
                }
            });
            return true;
        } else {
            return false;
        }
    }

    //Weird bug... Checking if the AssMan has loaded the file didn't work properly so it's not done here.
    public boolean createSettingsButton() {
        settingsButton = new ImageButton(new TextureRegionDrawable(assMan.manager.get
                (AssMan.settingsButton.fileName, Texture.class)));
        settingsButton.setPosition(60, 60);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.settingsScreen);
            }
        });
        if(settingsButton != null) {
            return true;
        }else{
            return false;
        }
    }

    public void createButtons() {
        if(createSubmitButton() && createSubmitButtonGrey() && createButtonClear() && createSettingsButton()){
            addButtonsToStage();
        }
    }

    public void addButtonsToStage() {
        if (greySubmitButton != null && submitButton != null && clearButton != null && settingsButton != null) {
            System.out.println("Added them!");
            stage.addActor(greySubmitButton);
            stage.addActor(submitButton);
            stage.addActor(clearButton);
            stage.addActor(settingsButton);
        }

    }

    public void draw(SpriteBatch batch) {
        submitButton.setVisible(player.getRegisters().isFull());
        greySubmitButton.setVisible(!submitButton.isVisible());
        batch.begin();
        programRegisterDisplay.draw(batch);
        batch.end();

        stage.draw();

        if (!(game.getGameScreen().getGameLogic().getState() == GameState.PICKING_CARDS)) {
            cardDisplay.clearAllCards();
            cardDisplay.clearAllCards();
            cardDisplay.clearAllCards();
            programRegisterDisplay.drawCardsInProgramRegister(cardDisplay);
            stage.draw();
        } else {
            stage.draw();
        }
    }

    public AssMan getAssMan() {
        return assMan;
    }

    public CardDisplay getCardDisplay() {
        return cardDisplay;
    }

    public void dispose() {
        System.out.println("disposing hud");
        cardsInHandDisplay.dispose();
        programRegisterDisplay.dispose();
        assMan.dispose();
    }
}
