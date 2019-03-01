package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.objects.Player;

public class CardsInHandDisplay {

    public Stage stage;
    private final Player player;
    private CardVisuals cardVisuals;
    private ImageButton cardButton;
    private TextureRegion cardTexture;
    private int posX, posY;



    public CardsInHandDisplay(final Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        cardVisuals = new CardVisuals();
        Gdx.input.setInputProcessor(stage);
    }

    public void updateCardsInHandVisually() {
        System.out.println(player.getRegisters().getCardsInHand().size());
        float scale = 0.5f;
        int j = 0;
        posX = 550;
        posY = 200;

        for (int i = 0; i < player.getRegisters().getCardsInHand().size(); i++) {
            cardTexture = cardVisuals.getRegion(player.getRegisters().getCardsInHand().get(i));
            cardButton = new ImageButton(new TextureRegionDrawable(cardTexture));
            cardButton.setTransform(true);
            cardButton.setScale(scale);
            cardButton.setPosition(posX + 130 * (i % 5), posY - 170 * j);

            cardButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.getRegisters().pickCard(0);
                }
            });

            stage.addActor(cardButton);

            if (i % 5 == 4) {
                j++;
                posX += 50;
            }
        }
    }

}