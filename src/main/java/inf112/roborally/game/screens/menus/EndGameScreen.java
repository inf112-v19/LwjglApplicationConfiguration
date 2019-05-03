package inf112.roborally.game.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.screens.BasicScreen;
import inf112.roborally.game.tools.AssMan;


public class EndGameScreen extends BasicScreen {

    private Label winnerLabel;
    private Player winner;
    private Sprite winnerSprite;
    private int rotateTimer;

    public EndGameScreen(RoboRallyGame game) {
        super(game);
        rotateTimer = 0;
        winnerLabel = new Label("", labelStyle);
        Table table = new Table();
        table.add(winnerLabel);
        table.setPosition(1920/2,1080/2, Align.center);
        stage.addActor(table);
        back.setVisible(false);
    }

    @Override
    public void render(float v) {
        super.render(v);
        game.batch.setProjectionMatrix(game.fixedCamera.combined);
        game.batch.begin();
        winnerSprite.draw(game.batch);
        game.batch.end();
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

    @Override
    protected void handleInput() {
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
        winner.moveToPosition(new Position(22, 15));
        winner.updateSprite();
        winnerSprite = winner.getSprite();
        winnerSprite.setSize(500, 500);

        Label label = new Label("WINNER!",
                new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), Color.WHITE));
        label.setPosition(1920 / 2, 350, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(6);
        stage.addActor(label);

        Label label2 = new Label("Press N to start a new game, or Esc to exit",
                new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), Color.WHITE));
        label2.setPosition(1920 / 2, 200, Align.center);
        label2.setAlignment(Align.center);
        label2.setFontScale(2);
        stage.addActor(label2);
    }
}
