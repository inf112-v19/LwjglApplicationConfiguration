package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.BetterMenu;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.tools.ButtonFactory;

public abstract class SelectScreen implements Screen {
    protected final RoboRallyGame game;

    private Stage stage;
    protected TextureRegionDrawable[] choices;
    protected int choiceIndex;
    private Image currentChoice;
    private Boolean clicked = false;
    protected final int numberOfChoices;
    protected String information;

    protected int skinChoiceIndex;
    protected int mapChoiceIndex;

    public SelectScreen(final RoboRallyGame game, final int numberOfChoices) {
        this.game = game;
        this.numberOfChoices = numberOfChoices;
        this.stage = new Stage(game.fixedViewPort, game.batch);
        //Add the background:
        Image background = new Image(new TextureRegionDrawable(new Texture(AssMan.SELECT_SCREEN.fileName)));
        stage.addActor(background);
        // Create buttons
        ImageButton next = ButtonFactory.createArrowRightButton();
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceIndex = (++choiceIndex) % numberOfChoices;
                clicked = true;
            }
        });
        ImageButton previous = ButtonFactory.createArrowLeftButton();
        previous.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceIndex = (numberOfChoices + --choiceIndex) % numberOfChoices;
                clicked = true;
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

        information = "";

        // Set the different texture for the possible choices
        setChoicesBasedOnScreen();

        // Information text:
        Label label = new Label("Select your " + information + ":",
                new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), Color.WHITE));
        label.setPosition(1920 / 2, 960, Align.center);
        label.setAlignment(Align.center);
        label.setFontScale(4);
        stage.addActor(label);

        choiceIndex = 0;
        currentChoice = new Image(choices[choiceIndex]);
        currentChoice.setPosition(1920 / 2f - currentChoice.getWidth() / 2, 1080 / 2f - currentChoice.getHeight() / 2);
        stage.addActor(currentChoice);

    }

    /**
     * Set the choice textures in each subclass,
     * for one it's the robot skins, for the other it's the different maps
     */
    protected abstract void setChoicesBasedOnScreen();

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            game.createDefaultGameScreen();
            game.setScreen(game.gameScreen);
            dispose();
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
