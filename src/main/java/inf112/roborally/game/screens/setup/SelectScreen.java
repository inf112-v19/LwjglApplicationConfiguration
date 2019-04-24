package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.BetterMenu;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.tools.ButtonFactory;

public abstract class SelectScreen implements Screen {
    protected final RoboRallyGame game;

    private Stage stage;
    private TextureRegionDrawable[] choices;
    protected int choiceIndex;
    private Image currentChoice;
    private Boolean clicked = false;
    private final int numberOfChoices;

    public SelectScreen(final RoboRallyGame game, Texture[] textures, final int numberOfChoices) {
        this.game = game;
        this.numberOfChoices = numberOfChoices;
        this.stage = new Stage(game.fixedViewPort, game.batch);
        //Add the background:
        Image background = new Image(new TextureRegionDrawable(AssMan.manager.get(AssMan.SETUP_BLACK_BACKGROUND)));
        stage.addActor(background);
        // Create buttons
        ImageButton next = ButtonFactory.createArrowRightButton();
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextClicked();
            }
        });
        ImageButton previous = ButtonFactory.createArrowLeftButton();
        previous.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                previousClicked();
            }
        });
        TextButton confirm = ButtonFactory.createTextButton("Confirm", 3);
        confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                completeChoice();
            }
        });

        ImageButton back = ButtonFactory.createBackButton();
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BetterMenu(game));
            }
        });

        stage.addActor(next);
        stage.addActor(previous);
        stage.addActor(confirm);
        stage.addActor(back);


        choices = new TextureRegionDrawable[numberOfChoices];
        for (int i = 0; i < numberOfChoices; i++) {
            choices[i] = new TextureRegionDrawable(textures[i]);
        }

        choiceIndex = 0;
        currentChoice = new Image(choices[choiceIndex]);
        currentChoice.setPosition(1920 / 2f - currentChoice.getWidth() / 2, 1080 / 2f - currentChoice.getHeight() / 2);
        stage.addActor(currentChoice);

    }

    // This will be used in the label to display whether we are picking skin or map
    protected void setInformationLabel(String information) {
        Label label = new Label("Select your " + information + ":",
                new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), Color.WHITE));
        label.setPosition(1920 / 2, 960, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(4);
        stage.addActor(label);
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        if (clicked) update(); // don't update unless we have to

        Gdx.gl.glClearColor(0 / 255f, 20 / 255f, 15 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        handleInput();
    }

    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            game.createDefaultGameScreen();
            game.setScreen(game.gameScreen);
            dispose();
        }


        // Make it so the player can also choose using the keyboard
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            confirmClicked();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            nextClicked();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            previousClicked();
        }
    }

    /**
     * Instead of having the 3 next methods in their buttons, they are out here so that
     * player can also choose with the keyboard
     */
    private void previousClicked() {
        choiceIndex = (numberOfChoices + --choiceIndex) % numberOfChoices;
        clicked = true;
    }

    private void nextClicked() {
        choiceIndex = (++choiceIndex) % numberOfChoices;
        clicked = true;
    }

    private void confirmClicked() {
        completeChoice();
    }

    private void update() {
        clicked = false;
        currentChoice.setDrawable(choices[choiceIndex]);
    }

    @Override
    public void resize(int width, int height) {
        game.fixedViewPort.update(width, height);
        game.dynamicViewPort.update(width, height);
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
        stage.dispose();
    }

    public abstract void completeChoice();

}
