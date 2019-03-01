package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.roborally.game.ProgramCard;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.Rotate;

import java.util.ArrayList;


public class CardsInHandDisplay {

    public Stage stage;
    private Viewport viewport;

    private Integer lives;
    private Integer cards;
    private Integer damage;
    Label livesLabel;
    Label damageLabel;
    final Player player;

    Table hud;
    CardVisuals cv;

    ImageButton button;
    TextureRegionDrawable buttonTextureDrawable;

    public CardsInHandDisplay(SpriteBatch sb, final Player player) {
        this.player = player;
        lives = player.getLives();
        damage = player.getDamage();
        cards = 0;


        viewport = new FitViewport(1920, 1080, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        hud = new Table();
        hud.top().align(Align.topRight);
        hud.setFillParent(true);


        livesLabel = new Label("Lives: " + lives, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        damageLabel = new Label("Damage taken: " + damage, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel.setFontScale(3);
        damageLabel.setFontScale(3);


        cv = new CardVisuals();
        //Adding buttons:
        float scale = 0.5f;
        int j = 0;
        int posX = 1300;
        for (int i = 0; i < player.getRegisters().getCardsInHand().size(); i++) {
            // TODO: GET TEXTURE FROM CARD AT ith POSITION:
            buttonTextureDrawable = new TextureRegionDrawable(cv.getRegion(player.getRegisters().getCardsInHand().get(i)));
            button = new ImageButton(buttonTextureDrawable);
            button.setTransform(true);
            button.setScale(scale);
            button.setPosition(posX + 100 * (i % 5), 200 - 150 * j);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //System.out.println(player.getRegisters().getCardsInHand().get(0));
                    player.getRegisters().pickCard(0);
                }
            });

            stage.addActor(button);

            if (i % 5 == 4) {
                j++;
                posX += 50;
            }


            hud.add(livesLabel).width(200).padRight(100);
            hud.add(damageLabel).width(200).padRight(200);
            stage.addActor(hud);
            Gdx.input.setInputProcessor(stage);
        }
    }

    public void testUpdate() {
        float scale = 0.5f;
        int j = 0;
        int posX = 1300;

        for (int i = 0; i < player.getRegisters().getCardsInHand().size(); i++) {
            // TODO: GET TEXTURE FROM CARD AT ith POSITION:
            buttonTextureDrawable = new TextureRegionDrawable(cv.getRegion(player.getRegisters().getCardsInHand().get(i)));
            button = new ImageButton(buttonTextureDrawable);
            button.setTransform(true);
            button.setScale(scale);
            button.setPosition(posX + 100 * (i % 5), 200 - 150 * j);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.getRegisters().pickCard(0);
                }
            });

            stage.addActor(button);

            if (i % 5 == 4) {
                j++;
                posX += 50;
            }


            hud.add(livesLabel).width(200).padRight(100);
            hud.add(damageLabel).width(200).padRight(200);
            stage.addActor(hud);
            Gdx.input.setInputProcessor(stage);
        }
    }

    public void update(Player player) {
        lives = player.getLives();
        damage = player.getDamage();

        livesLabel.setText("Lives: " + lives);
        damageLabel.setText("Damage taken: " + damage);
    }

    //Just for testing
    public void printCards(Player player) {
        ArrayList<ProgramCard> cards = player.getRegisters().getCardsInHand();
        for (int i = 0; i < cards.size(); i++) {
            System.out.println("Card " + i + ": " + cards.get(i));
        }
    }
}