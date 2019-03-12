package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.objects.Player;

public class CardsInHandDisplay {

    public Stage stage;
    private final Player player;
    private CardVisuals cardVisuals;
    private CardButton cardButton;
    private TextureRegion cardTexture;
    private int posX, posY;


    public CardsInHandDisplay(final Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        cardVisuals = new CardVisuals();
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new CameraDragListener());
    }

    public void updateCardsInHandVisually() {
        float scale = 0.5f;
        int j = 0;
        posX = 550;
        posY = 200;

        for(Actor butt : stage.getActors()) {
            butt.remove();
        }

        for (int i = 0; i < player.getNumberOfCardsInHand(); i++) {
            cardTexture = cardVisuals.getRegion(player.getCardInHand(i));
            cardButton = new CardButton(new TextureRegionDrawable(cardTexture), player, i, this);
            cardButton.setTransform(true);
            cardButton.setScale(scale);
            cardButton.setPosition(posX + 130 * (i % 5), posY - 170 * j);
            stage.addActor(cardButton);

            if (i % 5 == 4) {
                j++;
                posX += 50;
            }
        }
    }

    public void dispose() {
        System.out.println("disposing CardsInHandDisplay");
        stage.dispose();
        cardVisuals.dispose();
    }
}