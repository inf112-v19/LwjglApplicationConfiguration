package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.setup.SelectSkinScreen;
import inf112.roborally.game.tools.ButtonFactory;

public class BetterMenu extends BasicScreen {

    public BetterMenu(final RoboRallyGame game) {
        super(game);
        back.setVisible(false);

        Label label = ButtonFactory.createLabel("RoboRally", Color.WHITE);
        label.setFontScale(10);
        TextButton single = ButtonFactory.createTextButton("Single player", 2);
        single.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectSkinScreen(game));
            }
        });
        TextButton multi = ButtonFactory.createTextButton("Multiplayer", 2);

        Table menuButtons = new Table();
        menuButtons.setPosition(1920 / 2, 1080 / 2, Align.center);
        menuButtons.add(label);
        menuButtons.row().padTop(20);
        menuButtons.add(single);
        menuButtons.row().padTop(20);
        menuButtons.add(multi);

        TextButton gui = ButtonFactory.createTextButton("GuiTest", 2);
        TextButton laser = ButtonFactory.createTextButton("LaserTest", 2);
        TextButton board = ButtonFactory.createTextButton("BoardTest", 2);
        TextButton ai = ButtonFactory.createTextButton("AI vs AI", 2);
        ai.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.AIvsAI = true;
            }
        });

        Table debuggingButtons = new Table();
        debuggingButtons.setPosition(1920 / 2,
                (ai.getHeight() + ai.getPadBottom() + ai.getPadTop()) / 2, Align.center);
        debuggingButtons.add(gui);
        debuggingButtons.add(laser);
        debuggingButtons.add(ai);
        debuggingButtons.add(board);


        stage.addActor(debuggingButtons);
        stage.addActor(menuButtons);
    }

    @Override
    protected void goToPreviousScreen() {
        // no previous screen..
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }
}
