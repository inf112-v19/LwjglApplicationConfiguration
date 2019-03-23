package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.objects.Player;

import java.util.ArrayList;

public class ProgramCardButton{

    private ImageTextButton btn;
    private Stage stage;


    public ProgramCardButton(){ }


    public ImageTextButton makeImageTextButton(ProgramCard card){
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

        return btn;
        //stage.addActor(btn);
        //Gdx.input.setInputProcessor(stage);
    }
}
