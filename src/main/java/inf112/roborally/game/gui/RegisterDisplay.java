package inf112.roborally.game.gui;

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
import inf112.roborally.game.player.Player;
import inf112.roborally.game.player.ProgramCard;
import inf112.roborally.game.player.ProgramRegisters;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;

public class RegisterDisplay {
    private Group registerGui;
    private float scale = .5f;
    private float h;
    private Player player;
    private ProgramRegisters registers;

    private Image programBoard;
    private Image wires;
    private Image flag;
    private ArrayList<Image> damageTokens;
    private ArrayList<Image> lifeTokens;
    private ArrayList<Image> lockTokens;
    private ArrayList<TextureRegionDrawable> wireTextures;
    private ImageButton powerDown;

    /**
     * Draws the program register of a given player. Shows cards in the
     * players register slots. If a register is locked a small lock icon will
     * appear. It also shows lives, damage taken and the power button.
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
        addLockTokens(lockGui); // Locks needs to be on top of the cards
        addPowerDown();
        addPlayerSkin();
        addTargetFlag();
    }

    private void addTargetFlag() {
        flag = new Image(AssMan.getFlagAtlasRegion(player.getTargetFlag()));
        flag.setPosition(448 - 20, -10);
        flag.setScale(.4f);
        registerGui.addActor(flag);
    }

    private void addPlayerSkin() {
        Image skin = new Image(player.getFrontRegion());
        skin.setPosition(programBoard.getX() - skin.getWidth() - 10, -skin.getHeight() * 0.3f);
        skin.setColor(skin.getColor().r, skin.getColor().g, skin.getColor().b, .8f);
        registerGui.addActor(skin);
    }

    private void addPowerDown() {
        TextureRegionDrawable normal = new TextureRegionDrawable(AssMan.manager.get(AssMan.POWER_DOWN));
        TextureRegionDrawable press = new TextureRegionDrawable(AssMan.manager.get(AssMan.POWER_DOWN_PRESS));
        TextureRegionDrawable pressed = new TextureRegionDrawable(AssMan.manager.get(AssMan.POWER_DOWN_PRESSED));
        powerDown = new ImageButton(normal, press, pressed);
        int pdSize = 160;
        powerDown.setSize(pdSize * scale, pdSize * scale);
        powerDown.setPosition(programBoard.getX() + 180 * scale, 380 * scale, Align.center);
        powerDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.wantsToPowerDown = !player.wantsToPowerDown;
                if (player.wantsToPowerDown) System.out.println(player.getName() + " wants to power down");
                else System.out.println(player.getName() + " changed his/her mind about powering down");
            }
        });
        registerGui.addActor(powerDown);
    }

    private void addProgramBoard() {
        programBoard = new Image(AssMan.manager.get(AssMan.REGISTER_PROGRAM_REGISTER));
        programBoard.setSize(programBoard.getWidth() * scale, programBoard.getHeight() * scale);
        programBoard.setPosition(1920 / 2, programBoard.getHeight() / 2, Align.center);
        registerGui.addActor(programBoard);
        h = programBoard.getHeight();
        programBoard.setColor(programBoard.getColor().r, programBoard.getColor().g, programBoard.getColor().b, .8f);
    }

    private void addDamageTokens() {
        float startX = 1920 / 2 + 397 * scale; // start x
        float startY = h - 70 * scale;
        float space = 70 * scale; // space from one token to the next

        damageTokens = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Image damageImage = new Image(AssMan.manager.get(AssMan.REGISTER_DAMAGE_TOKEN));
            damageImage.setPosition(startX - space * i, startY, Align.center);
            damageImage.setSize(50 * scale, 50 * scale);
            registerGui.addActor(damageImage);
            damageTokens.add(damageImage);
        }
    }

    private void addLifeTokens() {
        float startX = 1920 / 2f + 488 * scale;
        float startY = h - 170 * scale;
        float space = 105 * scale;

        lifeTokens = new ArrayList<>();
        for (int i = player.getLives(); i > 0; i--) {
            Image lifeImage = new Image(AssMan.manager.get(AssMan.REGISTER_LIFE_TOKEN));
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

        Texture texture = AssMan.manager.get(AssMan.REGISTER_WIRES);
        wireTextures = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            wireTextures.add(new TextureRegionDrawable(
                    new TextureRegion(texture, 0, 481 * i, 1024, 481)));
        }
        wires.setDrawable(wireTextures.get(0));
    }

    private void addLockTokens(Group lockGui) {
        float startX = 1920 / 2f + 400 * scale;
        float startY = h - 235 * scale;
        float space = 200 * scale;
        lockTokens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Image lockImage = new Image(AssMan.manager.get(AssMan.REGISTER_LOCK_TOKEN));
            lockImage.setSize(lockImage.getWidth() * scale, lockImage.getHeight() * scale);
            lockImage.setPosition(startX - space * i, startY, Align.center);
            lockTokens.add(lockImage);
            lockGui.addActor(lockImage);
        }
    }

    public void update() {
        flag.setDrawable(new TextureRegionDrawable(AssMan.getFlagAtlasRegion(player.getTargetFlag())));
        int wireIndex = (5 - registers.getNumUnlockedRegisters()) % wireTextures.size();
        wires.setDrawable(wireTextures.get(wireIndex));
        for (int i = 0; i < lifeTokens.size(); i++) {
            lifeTokens.get(i).setVisible(player.getLives() >= 3 - i);
        }
        for (int i = 0; i < damageTokens.size(); i++) {
            damageTokens.get(i).setVisible(player.getDamage() > i);
        }
        for (int i = 0; i < lockTokens.size(); i++) {
            lockTokens.get(4 - i).setVisible(player.getRegisters().isLocked(i));
        }
    }

    void drawCardsInProgramRegister(final Hud hud) {
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
                            registers.returnCard(index);
                            hud.updateCardButtons();
                        }
                    }
                });
                registerGui.addActor(cardInRegisterButton);
            }
        }
    }

    ImageButton getPowerDown() {
        return powerDown;
    }
}
