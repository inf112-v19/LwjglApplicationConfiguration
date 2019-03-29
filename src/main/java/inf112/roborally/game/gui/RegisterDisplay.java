package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.GameLogic;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.board.ProgramRegisters;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.objects.Player;

import java.util.ArrayList;


public class RegisterDisplay {
    private Player player;
    public Group registerGui;
    private ProgramRegisters registers;

    private Image programBoard;
    private Image wires;
    private ArrayList<Image> damageTokens;
    private ArrayList<Image> lifeTokens;
    private ArrayList<Image> lockTokens;
    private ArrayList<TextureRegionDrawable> wireTextures;



    float scale = .5f;
    float h;

    /**
     * Draws the program register of a given player.
     * Shows cards in the players register slots. If a register is locked a small lock icon will appear.
     * It also shows lives and damage.
     *
     * @param player
     */
    public RegisterDisplay(Player player, Group registerGui, Group lockGui) {
        this.player = player;
        this.registerGui = registerGui;
        registers = player.getRegisters();

        //Items on a registerGui or in a group are drawn in the order they are added.
        addProgramBoard();
        addDamageTokens();
        addLifeTokens();
        addWires();
        addLockTokens(lockGui); // Locks needs to be update on top of the cards

        addPowerDown();
    }

    private void addPowerDown() {
        ImageButton powerDown = new ImageButton(new TextureRegionDrawable(new Texture("assets/cards/powerDown.png")));
        powerDown.setSize(powerDown.getWidth() * scale, powerDown.getHeight() * scale);
        powerDown.setPosition(programBoard.getX() + 180 * scale, 370 * scale, Align.center);
        powerDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameLogic logic = ((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getGameLogic();
                if (logic.getState() == GameState.PICKING_CARDS && player.playerState == PlayerState.OPERATIONAL) {
                    System.out.println(player.getName() + " wants to power down");
                    player.wantsToPowerDown = true;
                }
            }
        });
        registerGui.addActor(powerDown);
    }

    private void addProgramBoard() {
        programBoard = new Image(new Texture(AssMan.REGISTER_PROGRAM_REGISTER.fileName));
        programBoard.setSize(programBoard.getWidth() * scale, programBoard.getHeight() * scale);
        programBoard.setPosition(1920 / 2, programBoard.getHeight() / 2, Align.center);
        registerGui.addActor(programBoard);
        h = programBoard.getHeight();
    }

    private void addDamageTokens() {
        float startX = 1920 / 2 + 397 * scale; // start x
        float startY = h - 70 * scale;
        float space = 70 * scale; // space from one token to the next

        damageTokens = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Image damageImage = new Image(new Texture(AssMan.REGISTER_DAMAGE_TOKEN.fileName));
            damageImage.setPosition(startX - space * i, startY, Align.center);
            damageImage.setSize(50 * scale, 50 * scale);
            registerGui.addActor(damageImage);
            damageTokens.add(damageImage);
        }
    }

    private void addLifeTokens() {
        float startX = 1920 / 2 + 488 * scale;
        float startY = h - 170 * scale;
        float space = 105 * scale;

        lifeTokens = new ArrayList<>();
        for (int i = player.getLives(); i > 0; i--) {
            Image lifeImage = new Image(new Texture(AssMan.REGISTER_LIFE_TOKEN.fileName));
            lifeImage.setPosition(startX - space * i, startY, Align.center);
            lifeImage.setSize(80 * scale, 80 * scale);
            lifeTokens.add(lifeImage);
            registerGui.addActor(lifeImage);
        }
    }

    private void addWires() {
        wires = new Image();
        wires.setSize(programBoard.getWidth(), programBoard.getHeight());
        wires.setPosition(programBoard.getX(), programBoard.getY());
        registerGui.addActor(wires);

        Texture texture = new Texture(AssMan.REGISTER_WIRES.fileName);
        wireTextures = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            wireTextures.add(new TextureRegionDrawable(new TextureRegion(texture, 0, 481 * i, 1024, 481)));
        }
        wires.setDrawable(wireTextures.get(0));
    }

    private void addLockTokens(Group lockGui) {
        float startX = 1920 / 2 + 400 * scale;
        float startY = h - 235 * scale;
        float space = 200 * scale;
        lockTokens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Image lockImage = new Image(new Texture(AssMan.REGISTER_LOCK_TOKEN.fileName));
            lockImage.setSize(lockImage.getWidth() * scale, lockImage.getHeight() * scale);
            lockImage.setPosition(startX - space * i, startY, Align.center);
            lockTokens.add(lockImage);
            lockGui.addActor(lockImage);
        }
    }

    public void update() {
        updateWires();
        updateLocks();
        updateDamageTokens();
        updateLifeTokens();
    }

    private void updateWires() {
        int wireIndex = (5 - registers.getNumUnlockedRegisters()) % wireTextures.size();
        wires.setDrawable(wireTextures.get(wireIndex));
    }

    private void updateLifeTokens() {
        for (int i = 0; i < lifeTokens.size(); i++) {
            lifeTokens.get(i).setVisible(player.getLives() >= 3 - i);
        }
    }

    private void updateDamageTokens() {
        for (int i = 0; i < damageTokens.size(); i++) {
            damageTokens.get(i).setVisible(player.getDamage() > i);
        }
    }

    private void updateLocks() {
        for (int i = 0; i < lockTokens.size(); i++) {
            lockTokens.get(4 - i).setVisible(player.getRegisters().isLocked(i));
        }
    }


    public void drawCardsInProgramRegister(final Hud hud) {
        for (int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS; i++) {
            ProgramCard card = player.getRegisters().getCard(i);
            if (card != null) {
                card.setUpSkin();
                final ProgramCardButton cardInRegisterButton = new ProgramCardButton(card);
                cardInRegisterButton.setTransform(true);
                cardInRegisterButton.setScale(scale * 0.8f);
                cardInRegisterButton.setPosition(programBoard.getX() +
                        19 * scale + 200 * scale * i, 10 * scale);
                final int index = i;
                cardInRegisterButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (!registers.isLocked(index)) {
                            registers.returnCard(player, index);
                            hud.clearAllCards();
                            hud.updateCards();
                        }
                    }
                });

                hud.registerGui.addActor(cardInRegisterButton);
            }
        }
    }
}
