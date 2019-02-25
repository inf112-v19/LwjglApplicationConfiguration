package inf112.roborally.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import java.util.ArrayList;


public class Hud {

    public Stage stage;
    private com.badlogic.gdx.utils.viewport.Viewport viewport;

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


        Texture buttonPic = new Texture(Gdx.files.internal("assets/objects/programcard.png"));
        TextureRegion buttonTexture= new TextureRegion(buttonPic);
        TextureRegionDrawable buttonTextureDrawable = new TextureRegionDrawable(buttonTexture);


        Table InteractiveHud = new Table();
        InteractiveHud.align(Align.topRight);
        InteractiveHud.setFillParent(true);

        ImageButton button = new ImageButton(buttonTextureDrawable);
        ImageButton button1 = new ImageButton(buttonTextureDrawable);
        ImageButton button2 = new ImageButton(buttonTextureDrawable);
        ImageButton button3 = new ImageButton(buttonTextureDrawable);
        ImageButton button4 = new ImageButton(buttonTextureDrawable);
        ImageButton button5 = new ImageButton(buttonTextureDrawable);
        ImageButton button6 = new ImageButton(buttonTextureDrawable);
        ImageButton button7 = new ImageButton(buttonTextureDrawable);
        ImageButton button8 = new ImageButton(buttonTextureDrawable);


        hud.add(livesLabel).width(200).padRight(100);
        hud.add(damageLabel).width(200).padRight(200);

        button.setWidth(50);
        button.setHeight(100);
        button.getImage().setFillParent(true);
        button1.setWidth(50);
        button1.setHeight(100);
        button1.getImage().setFillParent(true);
        button2.setWidth(50);
        button2.setHeight(100);
        button2.getImage().setFillParent(true);
        button3.setWidth(50);
        button3.setHeight(100);
        button3.getImage().setFillParent(true);
        button4.setWidth(50);
        button4.setHeight(50);
        button4.getImage().setFillParent(true);
        button5.setWidth(50);
        button5.setHeight(50);
        button5.getImage().setFillParent(true);
        button6.setWidth(50);
        button6.setHeight(50);
        button6.getImage().setFillParent(true);
        button7.setWidth(50);
        button7.setHeight(100);
        button7.getImage().setFillParent(true);
        button8.setWidth(50);
        button8.setHeight(100);
        button8.getImage().setFillParent(true);




        stage.addActor(hud);
        stage.addActor(InteractiveHud);

        InteractiveHud.padTop(100);
        InteractiveHud.add(button);
        InteractiveHud.add(button1);
        InteractiveHud.add(button2);
        button.padTop(100).padRight(150);
        button1.padTop(100).padRight(150);
        button2.padTop(100).padRight(150);
        InteractiveHud.row().padTop(50);
        InteractiveHud.add(button3);
        InteractiveHud.add(button4);
        InteractiveHud.add(button5);
        button3.padTop(100).padRight(150);
        button4.padTop(100).padRight(150);
        button5.padTop(100).padRight(150);
        InteractiveHud.row().padTop(50);
        InteractiveHud.add(button6);
        InteractiveHud.add(button7);
        InteractiveHud.add(button8);
        button6.padTop(100).padRight(100);
        button7.padTop(100).padRight(150);
        button8.padTop(100).padRight(150);




        Gdx.input.setInputProcessor(stage);
        button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                printCards(player1);
                System.out.println(player1.getRegisters().pickCard(0));
                System.out.println(player1.getRegisters().getCardsInHand().get(0));
                player1.execute(0);

                // test:

                //player1.pickCard(0);
                //ProgramCard cardFromRegister = player1.getCardInRegister(0);
                //player1.execute(cardFromRegister);

                //--
                //printCards(player1);
                //int posInRegister = player1.pickCard(0);
                //player1.execute(posInRegister);

                /*
                Instead of using pickCard right away we could just save the card numbers that are picked.
                Once all 5 numbers have been chosen and the player has locked it in - then we start using pickCard
                and such? It would make it easier for us to unpick and pick cards since all we have to do is
                check if the number we are picking is already in the list of picked numbers or not. If it is, then we
                remove it (because then the player has chosen that card number earlier and wants to remove it now),
                if not we add it to the list and make sure that the list can not be longer than 5 numbers.
                 */

            }

        });
        button1.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(player1.getRegisters().pickCard(1));

            }

        });
        button2.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(player1.getRegisters().pickCard(2));

            }

        });
        button3.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button4");
            }

        });
        button4.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button5");
                player1.execute(new ProgramCard(Rotate.UTURN, 0, 0));

            }

        });
        button5.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button6");

            }

        });
        button6.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button7");
                player1.execute(new ProgramCard(Rotate.NONE, 1, 0));
            }

        });

        button7.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button8");
                player1.execute(new ProgramCard(Rotate.NONE, 2, 0));
            }

        });

        button8.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button9");
                player1.execute(new ProgramCard(Rotate.NONE, 3, 0));
            }

        });



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