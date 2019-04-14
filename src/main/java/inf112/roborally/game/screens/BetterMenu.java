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
        int sidePad = 40;
        int buttomPad = 20;

        Label label = ButtonFactory.createLabel("RoboRally", Color.WHITE);
        label.setFontScale(10);
        TextButton single = ButtonFactory.createTextButton("Single player",2);
        single.padBottom(buttomPad).padRight(sidePad).padLeft(sidePad);
        single.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectSkinScreen(game));
            }
        });
        TextButton multi = ButtonFactory.createTextButton("Multiplayer", 2);
        multi.padBottom(buttomPad).padRight(sidePad).padLeft(sidePad);
        TextButton ai = ButtonFactory.createTextButton("AI vs AI", 2);
        ai.padBottom(buttomPad).padRight(sidePad).padLeft(sidePad);
        ai.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.AIvsAI = true;
            }
        });

        Table t = new Table();
        t.setPosition(1920 / 2, 1080 / 2, Align.center);
        t.add(label);
        t.row().padTop(20);
        t.add(single);
        t.row().padTop(20);
        t.add(multi);
        t.row().padTop(20);
        t.add(ai);

        stage.addActor(t);
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
