package inf112.roborally.game.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.objects.Player;

public class CardButton extends ImageButton {

    private final Player player;
    private final int i;
    private final CardsInHandDisplay cardsInHandDisplay;

    public CardButton(TextureRegionDrawable texture, final Player player, final int i, final CardsInHandDisplay cardsInHandDisplay) {
        super(texture);
        this.cardsInHandDisplay = cardsInHandDisplay;
        this.player = player;
        this.i = i;

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("i: " + i);
                //System.out.println("Size: " + player.getNumberOfCardsInHand());
                player.pickCardInRegister(i);
                cardsInHandDisplay.updateCardsInHandVisually();
            }
        });
    }
}
