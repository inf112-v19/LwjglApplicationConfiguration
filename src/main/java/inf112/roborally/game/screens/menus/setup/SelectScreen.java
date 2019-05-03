package inf112.roborally.game.screens.menus.setup;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.screens.InputFieldScreen;
import inf112.roborally.game.tools.AssMan;

public abstract class SelectScreen extends InputFieldScreen {
    private TextureRegionDrawable[] choices;
    protected int choiceIndex;
    private Image currentChoice;
    private Boolean clicked = false;
    private final int nOptions;

    public SelectScreen(final RoboRallyGame game, Texture[] textures) {
        super(game);
        text.setVisible(false);
        setArrowsVisible(true);
        this.nOptions = textures.length;
        choices = new TextureRegionDrawable[nOptions];
        for (int i = 0; i < nOptions; i++) {
            choices[i] = new TextureRegionDrawable(textures[i]);
        }
        choiceIndex = 0;
        currentChoice = new Image(choices[choiceIndex]);
        currentChoice.setPosition(1920 / 2f - currentChoice.getWidth() / 2,
                1080 / 2f - currentChoice.getHeight() / 2);
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
    public void render(float v) {
        if (clicked) update(); // don't update unless we have to
        super.render(v);
    }

    @Override
    protected void goLeft() {
        choiceIndex = (nOptions + --choiceIndex) % nOptions;
        clicked = true;
    }

    @Override
    protected void goRight() {
        choiceIndex = (++choiceIndex) % nOptions;
        clicked = true;
    }

    private void update() {
        clicked = false;
        currentChoice.setDrawable(choices[choiceIndex]);
    }
}
