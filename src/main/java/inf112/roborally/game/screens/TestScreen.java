package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.animations.Animation;
import inf112.roborally.game.animations.RepairAnimation;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.gui.ProgramRegisterDisplay;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.objects.Position;

import java.util.ArrayList;
import java.util.List;

public class TestScreen implements Screen {
    private final RoboRallyGame game;
    private ProgramRegisterDisplay programRegisterDisplay;
    private Player player;
    private Sprite background;
    private List<Animation> testAnimation;
    private ImageTextButton btn;
    private Stage stage;


    public TestScreen(RoboRallyGame game) {
        this.game = game;
        player = new Player(0, 0);
        programRegisterDisplay = new ProgramRegisterDisplay(player, stage);
        for (int i = 0; i < 9; i++) {
            player.receiveCard(new ProgramCard(Rotate.NONE, 2, 0, "testCard"));
        }

        background = new Sprite(new Texture("assets/img/testscreen.png"));
        game.fixedViewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        ProgramCard card = new ProgramCard(Rotate.NONE, 1, 100, "move1");

        testAnimation = new ArrayList<>();
        TextureAtlas atlas = new TextureAtlas("assets/cards/imageButton.atlas");
        Skin skin = new Skin();
        skin.addRegions(atlas);
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = new BitmapFont();
        style.up = skin.getDrawable(card.getType());
//        style.down = skin.getDrawable("back");
//        style.checked = skin.getDrawable("back");
        btn = new ImageTextButton("", style);

        Label label = new Label(card.getPriority() + "", new Label.LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1)));
        label.setFontScale(2.7f);
        btn.setLabel(label);
        btn.getCell(label).padRight(160).padBottom(250);

        stage = new Stage(game.fixedViewPort, game.batch);

        stage.addActor(btn);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        float r = 0 / 255f;
        float g = 20 / 255f;
        float b = 15 / 255f;

        Gdx.gl.glClearColor(r, g, b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(game.fixedCamera.combined);
        game.batch.begin();
        background.draw(game.batch);

        for (Animation a : testAnimation) {
            if (a.hasFinished()) {
                //do nothing
            }
            else
                a.draw(game.batch);
        }

        programRegisterDisplay.draw(game.batch);
        game.batch.end();
        stage.draw();
        handleInput();
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
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            player.returnCards();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            testAnimation.add(new RepairAnimation(new Position(8, 8)));
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
