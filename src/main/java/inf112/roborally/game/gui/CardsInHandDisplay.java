package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Player;

public class CardsInHandDisplay {

    public Stage stage;
    private final Player player;
    private CardVisuals cardVisuals;
    private CardButton cardButton;

    private ImageTextButton btn;

    private TextureRegion cardTexture;
    private int posX, posY;


    public CardsInHandDisplay(final Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        cardVisuals = new CardVisuals();
        Gdx.input.setInputProcessor(stage);
        stage.addListener(((RoboRallyGame) Gdx.app.getApplicationListener()).cameraListener);
    }

    //Not in use
    public void updateCardsInHandVisually() {
        float scale = 0.5f;
        int j = 0;
        posX = 1250;
        posY = 200;

        for (Actor button : stage.getActors()) {
            if (button instanceof CardButton)
                button.remove();
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

    //Not in use
    @SuppressWarnings("Duplicates")
    public void updateCardsV(){
        float scale = 0.5f;
        int j = 0;
        posX = 1250;
        posY = 200;

        for (Actor button : stage.getActors()) {
            if (button instanceof ImageTextButton)
                button.remove();
        }

        for(int i = 0; i < player.getNumberOfCardsInHand(); i++){
            btn = new ProgramCardButton().makeImageTextButton(player.getCardInHand(i));
            btn.setTransform(true);
            btn.setScale(scale);
            btn.setPosition(posX + 130 * (i % 5), posY - 170 * j);

            final int index = i;
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.getRegisters().placeCard(index);
                    //cardsInHandDisplay.updateCardsInHandVisually(); // When calling this method once, sometimes, index out
                    //cardsInHandDisplay.updateCardsInHandVisually(); // of bounds will occur when clicking the card button
                    //cardsInHandDisplay.updateCardsInHandVisually(); // in the last location. Therefor called thrice.
                    //updateCardsV();
                    //updateCardsV();
                    //updateCardsV();
                }
            });

            stage.addActor(btn);

            if (i % 5 == 4) {
                j++;
                posX += 50;
            }
        }
    }

    public Player getPlayer(){
        return player;
    }

    public void dispose() {
        System.out.println("disposing CardsInHandDisplay");
        stage.dispose();
        cardVisuals.dispose();
    }
}