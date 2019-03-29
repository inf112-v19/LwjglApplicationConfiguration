package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
    public Group registerGui; // All register elements except locks
    public Group lockGui; // Lock are register elements, but need a separate group so the can be drawn on top of cards
    public Group handGui; // Cards in player hand. Needs a separate so the cards can be hidden during phases.

    private HandDisplay handDisplay;
    private RegisterDisplay registerDisplay;
    private Player player;
    private ImageButton submitButton;
    private ImageButton greySubmitButton;
    private ImageButton clearButton;
    private ImageButton settingsButton;


    private final RoboRallyGame game;

    private AssMan assMan;
    private float scale = 0.4f;

    public Hud(final Player player, final RoboRallyGame game) {
        this.player = player;
        this.game = game;
        this.assMan = game.getAssMan();
        stage = new Stage(game.fixedViewPort, game.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addListener(game.cameraListener);

        registerGui = new Group();
        lockGui = new Group();
        handGui = new Group();

        stage.addActor(registerGui);
        stage.addActor(lockGui);
        stage.addActor(handGui);


        handDisplay = new HandDisplay(player, this);
        registerDisplay = new RegisterDisplay(player, registerGui, lockGui);

    }


    public boolean createSubmitButton() {
        if (assMan.manager.isLoaded(AssMan.BUTTON_SUBMIT.fileName)) {
            submitButton = new ImageButton(new TextureRegionDrawable(assMan.manager.get
                    (AssMan.BUTTON_SUBMIT.fileName, Texture.class)));
            submitButton.setSize(submitButton.getWidth() * scale, submitButton.getHeight() * scale);
            submitButton.setPosition((1920 / 2) + 7, 260);
            submitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (player.getRegisters().isFull() && player.isAlive()) {
                        player.playerState = PlayerState.READY;
                    }
                }
            });
            return true;
        }
        else {
            return false;
        }
    }


    public boolean createSubmitButtonGrey() {
        if (assMan.manager.isLoaded(AssMan.BUTTON_SUBMIT_GREY.fileName)) {
            greySubmitButton = new ImageButton(new TextureRegionDrawable(assMan.manager.get
                    (AssMan.BUTTON_SUBMIT_GREY.fileName, Texture.class)));
            greySubmitButton.setSize(submitButton.getWidth(), submitButton.getHeight());
            greySubmitButton.setPosition(submitButton.getX(), submitButton.getY());
            greySubmitButton.addListener(new ClickListener());
            return true;
        }
        else {
            return false;
        }
    }

    public boolean createButtonClear() {
        if (assMan.manager.isLoaded(AssMan.BUTTON_CLEAR.fileName)) {
            clearButton = new ImageButton(new TextureRegionDrawable(assMan.manager.get(
                    AssMan.BUTTON_CLEAR.fileName, Texture.class)));
            clearButton.setSize(clearButton.getWidth() * scale, clearButton.getHeight() * scale);
            clearButton.setPosition((1920 / 2) - (clearButton.getWidth()) - 7, 260);
            clearButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(player.isAlive()) {
                        player.getRegisters().returnCards(player);
                        clearAllCards();
                        updateCards();
                    }
                }
            });
            return true;
        }
        else {
            return false;
        }
    }

    public boolean createSettingsButton() {
        settingsButton = new ImageButton(new TextureRegionDrawable(assMan.manager.get
                (AssMan.BUTTON_SETTINGS.fileName, Texture.class)));
        settingsButton.setPosition(60, 60);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.settingsScreen);
                Gdx.input.setInputProcessor(game.settingsScreen.stage);
            }
        });
        if (settingsButton != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public void createButtons() {
        if (createSubmitButton() && createSubmitButtonGrey() && createButtonClear() && createSettingsButton()) {
            addButtonsToStage();
        }
    }

    private void addButtonsToStage() {
        if (greySubmitButton != null && submitButton != null && clearButton != null && settingsButton != null) {
            stage.addActor(greySubmitButton);
            stage.addActor(submitButton);
            stage.addActor(clearButton);
            stage.addActor(settingsButton);
        }

    }

    public void draw() {
        submitButton.setVisible(player.getRegisters().isFull());
        greySubmitButton.setVisible(!submitButton.isVisible());
        registerDisplay.update();

        handGui.setVisible(game.getGameScreen().getGameLogic().getState() == GameState.PICKING_CARDS);

        stage.draw();
    }

    /**
     * Remove all program card buttons.
     */
    public void clearAllCards() {
        for(int i = 0; i < 4; i++) {
            for (Actor button : registerGui.getChildren()) {
                if (button instanceof ProgramCardButton) {
                    ((ProgramCardButton) button).dispose();
                    button.remove();
                }
            }
            for (Actor button : handGui.getChildren()) {
                if (button instanceof ProgramCardButton) {
                    ((ProgramCardButton) button).dispose();
                    button.remove();
                }
            }
        }
    }

    public AssMan getAssMan() {
        return assMan;
    }


    /**
     * Updates program cards in hand and program cards in register visually.
     */
    @SuppressWarnings("Duplicates")
    public void updateCards() {
        clearAllCards();
        handDisplay.updateCardsInHand(this);
        registerDisplay.drawCardsInProgramRegister(this);
    }
}
