package inf112.roborally.game.gui;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import inf112.roborally.game.board.ProgramCard;

public class ProgramCardButton extends ImageTextButton {

    ProgramCard card;
    private Label label;

    public ProgramCardButton(ProgramCard card){
        super("", card.getStyle());
        this.card = card;
        label = getLabel();
        label.setText(card.getPriority() + "");
        label.setFontScale(2.7f);
        setLabel(label);
        getCell(label).padRight(160).padBottom(250);
    }
    public void dispose(){
        card.dispose();
    }
}
