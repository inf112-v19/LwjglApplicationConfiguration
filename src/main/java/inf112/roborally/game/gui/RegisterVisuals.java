package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.roborally.game.ProgramRegisters;
import inf112.roborally.game.objects.Player;

public class RegisterVisuals {
    private Player player;
    private ProgramRegisters registers;
    private Sprite board;
    private Sprite lifetoken;
    private Sprite damagetoken;
    private Sprite locktoken;
    private Sprite card;

    int lives;
    int damage;

    public RegisterVisuals(Player player) {
        this.player = player;
        registers = player.getRegisters();
        board = new Sprite(new Texture("assets/cards/programregistertemplate.png"));
        lifetoken = new Sprite(new Texture("assets/cards/lifetoken.png"));
        damagetoken = new Sprite(new Texture("assets/cards/damagetoken.png"));
        locktoken = new Sprite(new Texture("assets/cards/locktoken.png"));
        card = new Sprite(new Texture("assets/cards/testcard.png"));
        lives = 3;
        damage = 8;
        update();
    }

    public void update() {

    }

    public void draw(SpriteBatch batch, OrthographicCamera camera, FitViewport port) {
        camera.position.set(board.getWidth() - 1920 / 2, 1080 / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        board.draw(batch);
        drawLifeTokens(batch);
        drawDamageTokens(batch);
        drawLocks(batch);
        drawCardsInRegisters(batch);
    }

    private void drawLifeTokens(SpriteBatch batch) {
        for (int i = 0; i < player.getLives(); i++) {
            lifetoken.setPosition(722 + 80.5f * i, board.getHeight() - 101);
            lifetoken.draw(batch);
        }
    }

    private void drawDamageTokens(SpriteBatch batch) {
        for (int i = 0; i < player.getDamage(); i++) {
            damagetoken.setPosition(883 - 79 * i, board.getHeight() - 175);
            damagetoken.draw(batch);

            if (i > 8) return;
        }
    }

    private void drawLocks(SpriteBatch batch) {
        for (int i = 4; i >= 0; i--) {
            if (registers.isLocked(4 - i)) {
                locktoken.setPosition(880 - 204 * i, board.getHeight() - 290);
                locktoken.draw(batch);
            }
        }
    }

    private void drawCardsInRegisters(SpriteBatch batch) {
        for (int i = 0; i < 5; i++) {
            if (player.getRegisters().getCardInRegister(i) != null) {
                card.setPosition(26 + 205*i, 30);
                card.draw(batch);
            }
        }
    }


}
