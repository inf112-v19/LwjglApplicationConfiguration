package inf112.roborally.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import inf112.roborally.game.player.ProgramCard;

/**
 * Visual program card.
 */
public class ProgramCardButton extends ImageTextButton {

    private ProgramCard card;

    ProgramCardButton(ProgramCard card) {
        super("", card.getStyle());
        this.card = card;
        Label label = getLabel();
        label.setText(card.getPriority() + "");
        label.setFontScale(2.7f);
        setLabel(label);
        getCell(label).padRight(160).padBottom(250);
    }

    public void dispose() {
        card.dispose();
    }
}
