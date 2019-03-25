package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.Position;

public class EndGameScreen extends AbstractScreen {

    private Player winner;
    private Sprite winnerSprite;
    private int rotateTimer;

    public EndGameScreen(RoboRallyGame roborallygame) {
        super(roborallygame, "assets/img/endgamebackground.png");
        rotateTimer = 0;
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

        //Rotate the winner
        updateRotation();
    }

    // Rotate the player, because he is celebrating!
    private void updateRotation() {
        rotateTimer++;
        if(rotateTimer > 30) {
            winner.rotate(Rotate.RIGHT);
            winner.updateSprite();
            winnerSprite = winner.getSprite();
            rotateTimer = 0;
        }

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    public void addWinner(Player winner) {
        this.winner = winner;
        // Scale the sprite of the winning player, to get him big enough
//        winner.scaleSize(10.0f);
        // Set player to the proper position
        //TODO Instead of manually input the x and y here, find a way to use width/2 and height/2 or something like that
        winner.moveToPosition(new Position(22, 15));
        winnerSprite = winner.getSprite();
        winnerSprite.setSize(500, 500);
    }
}
