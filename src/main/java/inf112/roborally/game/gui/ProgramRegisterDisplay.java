package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.board.ProgramRegisters;
import inf112.roborally.game.objects.Player;

import java.util.ArrayList;

public class ProgramRegisterDisplay {
    private Player player;
    public Stage stage;
    private ProgramRegisters registers;
    private Sprite programBoard;
    private Sprite lifeToken;
    private Sprite damageToken;
    private Sprite lockToken;
    private Sprite card;
    private Sprite wires;
    private ArrayList<TextureRegion> wireTextures;


    float scale = .5f;

    /**
     * Draws the program register of a given player.
     * Shows cards in the players register slots. If a register is locked a small lock icon will appear.
     * It also shows lives and damage.
     *
     * @param player
     */
    public ProgramRegisterDisplay(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        registers = player.getRegisters();

        programBoard = new Sprite(new Texture("assets/cards/programregisters.png"));
        programBoard.setSize(programBoard.getWidth() * scale, programBoard.getHeight() * scale);
        programBoard.setOriginCenter();
        programBoard.setOriginBasedPosition(1920 / 2, programBoard.getHeight() / 2);

        wires = new Sprite(new Texture("assets/cards/wires.png"));
        wires.setSize(programBoard.getWidth(), programBoard.getHeight());
        wires.setPosition(programBoard.getX(), programBoard.getY());

        wireTextures = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            wireTextures.add(new TextureRegion(wires.getTexture(), 0, 481 * i, 1024, 481));
        }

        lifeToken = new Sprite(new Texture("assets/cards/tokens/lifeToken.png"));
        lifeToken.setSize(80 * scale, 80 * scale);

        damageToken = new Sprite(new Texture("assets/cards/tokens/damageToken.png"));
        damageToken.setSize(50 * scale, 50 * scale);

        lockToken = new Sprite(new Texture("assets/cards/tokens/lockToken.png"));
        lockToken.setSize(lockToken.getWidth() * scale, lockToken.getHeight() * scale);

        card = new Sprite();
        float cardScale = 0.77f;
        card.setSize(238 * cardScale * scale, 300 * cardScale * scale);
    }

    public void draw(SpriteBatch batch) {
        updateWires();
        programBoard.draw(batch);
        wires.draw(batch);
        drawLifeTokens(batch);
        drawDamageTokens(batch);
        drawLocks(batch);
    }

    private void updateWires() {
        int wireIndex = 5 - registers.getNumUnlockedRegisters();
        wires.setRegion(wireTextures.get(wireIndex % wireTextures.size()));

    }

    private void drawLifeTokens(SpriteBatch batch) {
        lifeToken.setOriginCenter();
        final float startX = 1920 / 2 + 515 * scale; // start x
        final float space = 105 * scale; // space from one token to the next
        for (int i = player.getLives(); i > 0; i--) {
            lifeToken.setOriginBasedPosition(startX - space * i, programBoard.getHeight() - 140 * scale);
            lifeToken.draw(batch);
        }
    }

    private void drawDamageTokens(SpriteBatch batch) {
        damageToken.setOriginCenter();
        final float startX = 1920 / 2 + 415 * scale; // start x
        final float space = 70 * scale; // space from one token to the next
        for (int i = 0; i < player.getDamage(); i++) {
            damageToken.setOriginBasedPosition(startX - space * i, programBoard.getHeight() - 53 * scale);
            damageToken.draw(batch);

            if (i > 8) return;
        }
    }

    private void drawLocks(SpriteBatch batch) {
        lockToken.setOriginCenter();
        float startX = 1920 / 2 + 400 * scale;
        float space = 200 * scale;
        for (int i = 4; i >= 0; i--) {
            if (registers.isLocked(4 - i)) {
                lockToken.setOriginBasedPosition(startX - space * i, programBoard.getHeight() - 235 * scale);
                lockToken.draw(batch);
            }
        }
    }

    public void drawCardsInProgramRegister(final CardDisplay cardDisplay){
        for (int i = 0; i < ProgramRegisters.NUMBER_OF_REGISTERS; i++) {
            ProgramCard card = player.getRegisters().getCard(i);
            if(card != null) {
                ImageTextButton cardInRegisterButton = new ProgramCardButton().makeImageTextButton(card);
                cardInRegisterButton.setTransform(true);
                cardInRegisterButton.setScale(scale * 0.8f);
                cardInRegisterButton.setPosition(programBoard.getX() +
                        19 * scale + 200 * scale * i, 10 * scale);

                final int index = i;
                cardInRegisterButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(!registers.isLocked(index)) {
                            registers.returnCard(player, index);
                            cardDisplay.clearAllCards();
                            cardDisplay.clearAllCards();
                            cardDisplay.clearAllCards();
                            cardDisplay.clearAllCards();
                            cardDisplay.updateCards();
                        }
                    }
                });
                stage.addActor(cardInRegisterButton);
            }
        }
    }


    public void dispose() {
        System.out.println("disposing ProgramRegisterDisplay");
        programBoard.getTexture().dispose();
        wires.getTexture().dispose();
        lifeToken.getTexture().dispose();
        damageToken.getTexture().dispose();
        lockToken.getTexture().dispose();
    }
}
