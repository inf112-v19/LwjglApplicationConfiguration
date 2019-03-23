package inf112.roborally.game.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.objects.Player;

public class CardButton extends ImageButton {

    private final Player player;
    private final CardsInHandDisplay cardsInHandDisplay;

    public CardButton(TextureRegionDrawable texture, final Player player, final int cardIndex, final CardsInHandDisplay cardsInHandDisplay) {
        super(texture);
        this.cardsInHandDisplay = cardsInHandDisplay;
        this.player = player;

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.getRegisters().placeCard(cardIndex);
                //cardsInHandDisplay.updateCardsInHandVisually(); // When calling this method once, sometimes, index out
                //cardsInHandDisplay.updateCardsInHandVisually(); // of bounds will occur when clicking the card button
                //cardsInHandDisplay.updateCardsInHandVisually(); // in the last location. Therefor called thrice.
                cardsInHandDisplay.updateCardsV();
            }
        });
    }
}
