package inf112.roborally.game.gui;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.objects.Player;

public class CardDisplay {

    private ProgramRegisterDisplay programRegisterDisplay;
    private CardsInHandDisplay cardsInHandDisplay;
    private Stage stage;
    private Player player;

    private int posX, posY;

    public CardDisplay(ProgramRegisterDisplay programRegisterDisplay, CardsInHandDisplay cardsInHandDisplay) {
        this.programRegisterDisplay = programRegisterDisplay;
        this.cardsInHandDisplay = cardsInHandDisplay;
        stage = cardsInHandDisplay.stage;
        player = cardsInHandDisplay.getPlayer();
    }

    public void clearAllCards(){
        for (Actor button : stage.getActors()) {
            if (button instanceof ImageTextButton) {
                button.remove();
            }
        }
    }

    /**
     * Updates program cards in hand and program cards in register visually.
     */
    @SuppressWarnings("Duplicates")
    public void update() {
        //Remove all buttons, AKA. program cards
        for (Actor button : stage.getActors()) {
            if (button instanceof ImageTextButton) {
                button.remove();
            }
        }

        //Draw cards in hand
        float scale = 0.5f;
        int j = 0;
        posX = 1250;
        posY = 200;

        for (int i = 0; i < player.getNumberOfCardsInHand(); i++) {
            ImageTextButton cardInHandButton = new ProgramCardButton().makeImageTextButton(player.getCardInHand(i));
            cardInHandButton.setTransform(true);
            cardInHandButton.setScale(scale);
            cardInHandButton.setPosition(posX + 130 * (i % 5), posY - 170 * j);

            final int index = i;
            cardInHandButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.getRegisters().placeCard(index);
                    update();
                }
            });
            stage.addActor(cardInHandButton);

            if (i % 5 == 4) {
                j++;
                posX += 50;
            }
        }

        //Draw cards in register
        for (int i = 0; i < 5; i++) {
            ProgramCard card = player.getRegisters().getCard(i);
            if(card != null) {
                ImageTextButton btn = new ProgramCardButton().makeImageTextButton(card);
                btn.setTransform(true);
                btn.setScale(scale * 0.8f);
                btn.setPosition(programRegisterDisplay.getProgramBoard().getX() + 19 * scale + 200 * scale * i, 10 * scale);
                stage.addActor(btn);
            }
            //else update cards, remove old ones
        }
    }
}
