package inf112.roborally.game;

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
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.Rotate;
import inf112.roborally.game.screens.CardVisuals;

import java.util.ArrayList;


public class Hud {

    public Stage stage;
    private com.badlogic.gdx.utils.viewport.Viewport viewport;
    private static int current = 0;

    private Integer lives;
    private Integer cards;
    private Integer damage;
    Label livesLabel;
    Label damageLabel;

    public Hud(SpriteBatch sb, final ArrayList<Player> players){
        final Player player1 = players.get(0);
        lives = player1.getLives();
        damage = player1.getDamage();
        cards = 0;


        viewport = new FitViewport(1920, 1080, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table hud = new Table();
        hud.top().align(Align.topRight);
        hud.setFillParent(true);


        livesLabel = new Label("Lives: " + lives, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        damageLabel = new Label("Damage taken: " + damage, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel.setFontScale(3);
        damageLabel.setFontScale(3);


        CardVisuals cv = new CardVisuals();
        ImageButton button;
        TextureRegionDrawable buttonTextureDrawable;
        //Adding buttons:
        float scale = 0.25f;
        int j = 0;
        for(int i = 0; i < 9; i++){
            // TODO: GET TEXTURE FROM CARD AT ith POSITION:
            buttonTextureDrawable = new TextureRegionDrawable(cv.getRegion(new ProgramCard(Rotate.NONE, 1, 0)));
            button = new ImageButton(buttonTextureDrawable);
            button.setTransform(true);
            button.setScale(scale);
            button.setPosition(1100 + 180*(i%3), 800 - 200*j);

            button.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println(player1.getRegisters().getCardsInHand().get(0));
                    player1.execute(player1.getRegisters().getCardsInHand().get(0));
                }

            });
            stage.addActor(button);
            if(i % 3 == 2) j++;
        }

        hud.add(livesLabel).width(200).padRight(100);
        hud.add(damageLabel).width(200).padRight(200);
        stage.addActor(hud);
        Gdx.input.setInputProcessor(stage);
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