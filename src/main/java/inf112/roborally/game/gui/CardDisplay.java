package inf112.roborally.game.gui;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import inf112.roborally.game.objects.Player;

public class CardDisplay {

    private ProgramRegisterDisplay programRegisterDisplay;
    private CardsInHandDisplay cardsInHandDisplay;
    private Stage stage;
    private Player player;


    public CardDisplay(ProgramRegisterDisplay programRegisterDisplay, CardsInHandDisplay cardsInHandDisplay) {
        this.programRegisterDisplay = programRegisterDisplay;
        this.cardsInHandDisplay = cardsInHandDisplay;
        stage = cardsInHandDisplay.stage;
        player = cardsInHandDisplay.getPlayer();
    }

    /**
     * Remove all program card buttons.
     */
    public void clearAllCards() {
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
    public void updateCards() {
        clearAllCards();
        cardsInHandDisplay.updateCardsInHand(this);
        programRegisterDisplay.drawCardsInProgramRegister(this);
    }
}
