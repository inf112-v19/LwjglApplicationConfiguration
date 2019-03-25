package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.objects.Player;

import java.util.Stack;

public class ImprovedTestScreen implements Screen {
    private final RoboRallyGame game;
    private Player player;
    private Sprite background;
    private Hud hud;
    private Stack<ProgramCard> stack;

    public ImprovedTestScreen(RoboRallyGame game) {
        this.game = game;
        game.fixedViewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        background = new Sprite(new Texture("assets/img/testscreen.png"));

        player = new Player(0, 0);
        stack = ProgramCard.makeProgramCardDeck();
        for(int i = 0; i < 9; i++){
            player.receiveCard(stack.pop());
        }

        hud = new Hud(player, game);
        hud.clearAllCards();
        hud.clearAllCards();
        hud.clearAllCards();
        hud.updateCards();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        handleInput();
        player.update();

        float r = 0 / 255f;
        float g = 20 / 255f;
        float b = 15 / 255f;

        Gdx.gl.glClearColor(r, g, b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(game.fixedCamera.combined);
        game.batch.begin();
        background.draw(game.batch);
        game.batch.end();
        hud.draw();

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.takeDamage();
            player.update();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player.getRegisters().placeCard(0);
            hud.clearAllCards();
            hud.clearAllCards();
            hud.clearAllCards();
            hud.updateCards();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            player.returnCards();
            hud.clearAllCards();
            hud.clearAllCards();
            hud.clearAllCards();
            hud.updateCards();
        }
    }

    @Override
    public void resize(int w, int h) {
        game.fixedViewPort.update(w, h);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
