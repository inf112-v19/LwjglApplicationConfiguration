package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.roborally.game.board.ProgramCard;

public class ProgramCardButton {

    private ImageTextButton btn;


    public ProgramCardButton(){}


    public ImageTextButton makeImageTextButton(ProgramCard card){
        TextureAtlas atlas = new TextureAtlas("assets/cards/imageButton.atlas");
        Skin skin = new Skin();
        skin.addRegions(atlas);
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = new BitmapFont();
        style.up = skin.getDrawable(card.getType());
        btn = new ImageTextButton("", style);

        Label label = new Label(card.getPriority() + "", new Label.LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1)));
        label.setFontScale(2.7f);
        btn.setLabel(label);
        btn.getCell(label).padRight(160).padBottom(250);

        return btn;
    }
}
