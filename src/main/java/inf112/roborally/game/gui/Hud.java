package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;
import java.util.List;


public class Hud {

    private final RoboRallyGame game;
    public Group registerGui; // All register elements except locks
    public Group lockGui; // Lock are register elements, but need a separate group so the can be drawn on top of cards
    public Group handGui; // Cards in player hand. Needs a separate so the cards can be hidden during phases.
    private HandDisplay handDisplay;
    private RegisterDisplay registerDisplay;
    private PlayerStatusDisplay playerStatusDisplay;
    public Stage stage;
    private Player player;
    private ImageButton submitButton;
    private ImageButton greySubmitButton;
    private ImageButton clearButton;
    private ImageButton settingsButton;
    private float scale = 0.4f;

    public Hud(final Player player, final RoboRallyGame game) {
        this.player = player;
        this.game = game;
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

    public void addPlayerStatusDisplay(List<Player> players) {
        playerStatusDisplay = new PlayerStatusDisplay(player, players, this);
    }


    public boolean createSubmitButton() {
        if (AssMan.manager.isLoaded(AssMan.BUTTON_SUBMIT.fileName)) {
            submitButton = new ImageButton(new TextureRegionDrawable(AssMan.manager.get(AssMan.BUTTON_SUBMIT)));
            submitButton.setSize(submitButton.getWidth() * scale, submitButton.getHeight() * scale);
            submitButton.setPosition((1920 / 2) + 7, 260);
            submitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (player.getRegisters().isFull() && !player.outOfLives()) {
                        player.setPlayerState(PlayerState.READY);
                        setButtonTouchable(false);
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
        if (AssMan.manager.isLoaded(AssMan.BUTTON_SUBMIT_GREY.fileName)) {
            greySubmitButton = new ImageButton(new TextureRegionDrawable(AssMan.manager.get(AssMan.BUTTON_SUBMIT_GREY)));
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
        if (AssMan.manager.isLoaded(AssMan.BUTTON_CLEAR.fileName)) {
            clearButton = new ImageButton(new TextureRegionDrawable(AssMan.manager.get(AssMan.BUTTON_CLEAR)));
            clearButton.setSize(clearButton.getWidth() * scale, clearButton.getHeight() * scale);
            clearButton.setPosition((1920 / 2) - (clearButton.getWidth()) - 7, 260);
            clearButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!player.outOfLives()) {
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
        settingsButton = new ImageButton(new TextureRegionDrawable(AssMan.manager.get(AssMan.BUTTON_SETTINGS)));
        settingsButton.setPosition(60, 60);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.settingsScreen);
                Gdx.input.setInputProcessor(game.settingsScreen.stage);
            }
        });
        return settingsButton != null;
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
        playerStatusDisplay.update();
        submitButton.setVisible(player.getRegisters().isFull());
        greySubmitButton.setVisible(!submitButton.isVisible());
        registerDisplay.update();

        stage.draw();
    }

    /**
     * Remove all program card buttons.
     */
    public void clearAllCards() {
        for (int i = 0; i < 4; i++) {
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

    /**
     * Updates program cards in hand and program cards in register visually.
     */
    @SuppressWarnings("Duplicates")
    public void updateCards() {
        clearAllCards();
        handDisplay.updateCardsInHand(this);
        registerDisplay.drawCardsInProgramRegister(this);
    }

    public void setButtonTouchable(boolean canTouch) {
        if (canTouch && !player.isPoweredDown()) {
            registerDisplay.getPowerDown().setTouchable(Touchable.enabled);
            submitButton.setTouchable(Touchable.enabled);
            clearButton.setTouchable(Touchable.enabled);
        }
        else {
            registerDisplay.getPowerDown().setTouchable(Touchable.disabled);
            submitButton.setTouchable(Touchable.disabled);
            clearButton.setTouchable(Touchable.disabled);
        }
    }

    public void resetPowerDown() {
        if (registerDisplay.getPowerDown().isChecked()) registerDisplay.getPowerDown().toggle();
    }
}
