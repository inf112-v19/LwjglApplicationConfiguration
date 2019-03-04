package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.board.ProgramRegisters;
import inf112.roborally.game.objects.Player;

public class ProgramRegisterDisplay {
    private final CardVisuals cardVisual;
    private Player player;
    private ProgramRegisters registers;
    private Sprite board;
    private Sprite lifetoken;
    private Sprite damagetoken;
    private Sprite locktoken;
    private Sprite card;

    float scale = .5f;

    /**
     * Draws the program register of a given player.
     * Shows cards in the players register slots. If a register is locked a small lock icon will appear.
     * It also shows lives and damage.
     * @param player
     */
    public ProgramRegisterDisplay(Player player, OrthographicCamera camera) {
        this.player = player;
        registers = player.getRegisters();

        board = new Sprite(new Texture("assets/cards/programregistertemplate.png"));
        board.setSize(board.getWidth()*scale, board.getHeight()*scale);

        lifetoken = new Sprite(new Texture("assets/cards/tokens/lifetoken.png"));
        lifetoken.setSize(lifetoken.getWidth()*scale, lifetoken.getHeight()*scale);

        damagetoken = new Sprite(new Texture("assets/cards/tokens/damagetoken.png"));
        damagetoken.setSize(damagetoken.getWidth()*scale, damagetoken.getHeight()*scale);

        locktoken = new Sprite(new Texture("assets/cards/tokens/locktoken.png"));
        locktoken.setSize(locktoken.getWidth()*scale, locktoken.getHeight()*scale);

        card = new Sprite();
        float cardScale = 0.77f;
        card.setSize(238*cardScale*scale, 300*cardScale*scale);

        cardVisual = new CardVisuals();

        camera.position.set(board.getWidth()/ 2 , 1080 / 2 , 0); // center

    }

    public void draw(SpriteBatch batch, Camera camera) {
//        camera.position.set(board.getWidth() - 1920 / 2 + 200*scale, 1080 / 2 - 100*scale, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        board.draw(batch);
        drawLifeTokens(batch);
        drawDamageTokens(batch);
        drawCardsInRegisters(batch);
        drawLocks(batch);
    }

    private void drawLifeTokens(SpriteBatch batch) {
        for (int i = player.getLives(); i > 0; i--) {
            lifetoken.setPosition(920*scale - 80.5f*scale * i, board.getHeight() - 200*scale);
            lifetoken.draw(batch);
        }
    }

    private void drawDamageTokens(SpriteBatch batch) {
        for (int i = 0; i < player.getDamage(); i++) {
            damagetoken.setPosition(790*scale - 79.5f*scale * i, board.getHeight() - 125*scale);
            damagetoken.draw(batch);

            if (i > 8) return;
        }
    }

    private void drawLocks(SpriteBatch batch) {
        for (int i = 4; i >= 0; i--) {
            if (registers.isLocked(4 - i)) {
                locktoken.setPosition(830*scale - 204*scale * i, board.getHeight() - 365*scale);
                locktoken.draw(batch);
            }
        }
    }

    private void drawCardsInRegisters(SpriteBatch batch) {
        for (int i = 0; i < 5; i++) {
            if (player.getRegisters().getCardInRegister(i) != null) {
                card.setPosition(19*scale + 200*scale * i, 10*scale);
                card.setRegion(cardVisual.getRegion(registers.getCardInRegister(i)));
                card.draw(batch);
            }
        }
    }

}
