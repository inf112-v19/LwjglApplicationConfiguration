package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Player;

public class EndGameScreen extends AbstractScreen {

    private Player winner;
    private Sprite winnerSprite;

    public EndGameScreen(RoboRallyGame roborallygame) {
        super(roborallygame, "assets/img/background2.png");
    }

    @Override
    public void render(float v) {
        float r = 0 / 255f;
        float g = 20 / 255f;
        float b = 15 / 255f;

        Gdx.gl.glClearColor(r, g, b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(game.fixedCamera.combined);
        batch.begin();
        background.draw(batch);
        winnerSprite.draw(batch);
        batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    public void addWinner(Player winner) {
        this.winner = winner;
        winnerSprite = winner.getSprite();
    }
}
