package inf112.roborally.game.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.BasicScreen;
import inf112.roborally.game.screens.menus.multiplayer.MultiplayerScreen;
import inf112.roborally.game.tools.ButtonFactory;

public class MenuScreen extends BasicScreen {

    private final RoboRallyGame game;

    public MenuScreen(final RoboRallyGame game) {
        super(game);
        this.game = game;
        back.setVisible(false);

        Label label = ButtonFactory.createLabel("RoboRally", Color.WHITE);
        label.setFontScale(10);
        TextButton quick = ButtonFactory.createTextButton("Quick Play", 2);
        quick.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quickPlay();
            }
        });
        TextButton single = ButtonFactory.createTextButton("Single player", 2);
        single.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                singleplayer();
            }
        });
        TextButton multi = ButtonFactory.createTextButton("Multiplayer", 2);
        multi.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                multiplayer();
            }
        });

        Table menuButtons = new Table();
        menuButtons.setPosition(1920 / 2, 1080 / 2, Align.center);
        menuButtons.add(label);
        menuButtons.row().padTop(10);
        menuButtons.add(quick);
        menuButtons.row().padTop(10);
        menuButtons.add(single);
        menuButtons.row().padTop(10);
        menuButtons.add(multi);

        TextButton gui = ButtonFactory.createTextButton("GuiTest", 2);
        gui.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                testScreen();
            }
        });
        TextButton laser = ButtonFactory.createTextButton("LaserTest", 2);
        laser.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                laserTest();
            }
        });
        TextButton board = ButtonFactory.createTextButton("BoardTest", 2);
        board.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boardTest();
            }
        });
        TextButton ai = ButtonFactory.createTextButton("AI vs AI", 2);
        ai.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aiVSai();
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

    public void setScreen(Screen screen) {
        game.setScreen(screen);
//        dispose();
    }

    private void singleplayer() {
        game.AIvsAI = false;
        //Need to use the selectSkinScreen in game in order to use selected skin choices
//        setScreen(game.selectSkinScreen);
        setScreen(game.selectNumberOfPlayersScreen);
    }

    private void multiplayer() {
        Screen s = new MultiplayerScreen(game);
        setScreen(s);
    }

    private void quickPlay() {
        game.createDefaultGameScreen();
        setScreen(game.gameScreen);
        game.AIvsAI = false;
    }

    private void aiVSai() {
        game.AIvsAI = true;
        game.createDefaultGameScreen();
        setScreen(game.gameScreen);
    }

    private void testScreen() {
        game.AIvsAI = false;
        setScreen(game.testScreen);
    }

    private void laserTest() {
        game.AIvsAI = false;
        setScreen(game.laserTestScreen);
    }

    private void boardTest() {
        game.AIvsAI = false;
        game.createTestBoard();
    }

    @Override
    protected void goToPreviousScreen() {
        // no previous screen..
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            Gdx.app.exit();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            multiplayer();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            quickPlay();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            singleplayer();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            testScreen();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            laserTest();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            aiVSai();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            boardTest();
        }
    }
}
