package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

public class EndGameScreen extends AbstractScreen {

    //Stage for holding actors.
    public Stage stage;
    private Player winner;
    private Sprite winnerSprite;
    private int rotateTimer;

    public EndGameScreen(RoboRallyGame game) {
        super(game, AssMan.ENDGAME_BACKGROUND.fileName);
        rotateTimer = 0;
        stage = new Stage(game.fixedViewPort, game.batch);
    }

    @Override
    public void render(float v) {
        super.render(v);

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
        if (rotateTimer > 30) {
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
        else if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            game.newGame();
        }
    }

    public void addWinner(Player winner) {
        this.winner = winner;

        // Set player to the proper position
        //TODO Instead of manually multiplayer the x and y here, find a way to use width/2 and height/2 or something like that
        winner.moveToPosition(new Position(22, 15));
        winnerSprite = winner.getSprite();
        winnerSprite.setSize(500, 500);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

}
