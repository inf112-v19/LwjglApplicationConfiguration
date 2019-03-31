package inf112.roborally.game.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.objects.Player;

/**
 * A class for drawing all the cards that are in the player hand.
 */
public class HandDisplay {

    public Hud hud;
    private final Player player;

    private int posX, posY;


    public HandDisplay(final Player player, Hud hud) {
        this.player = player;
        this.hud = hud;
    }

    /**
     * Draws all cards in the player hand.
     * @param hud where it's being drawn
     */
    public void updateCardsInHand(final Hud hud){
        float scale = 0.5f;
        int j = 0;
        posX = 1250;
        posY = 200;

        for (int i = 0; i < player.getHand().size(); i++) {
            ProgramCard card = player.getHand().getCard(i);
            card.setUpSkin();
            final ProgramCardButton cardInHandButton = new ProgramCardButton(card);
            cardInHandButton.setTransform(true);
            cardInHandButton.setScale(scale);
            cardInHandButton.setPosition(posX + 130 * (i % 5), posY - 170 * j);

            final int index = i;
            cardInHandButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.getRegisters().placeCard(index);
                    hud.clearAllCards();
                    hud.updateCards();
                }
            });

            hud.handGui.addActor(cardInHandButton);

            if (i % 5 == 4) {
                j++;
                posX += 50;
            }
        }
    }
}