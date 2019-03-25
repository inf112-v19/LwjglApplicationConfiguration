package inf112.roborally.game.gui;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CardDisplay {

    private ProgramRegisterDisplay programRegisterDisplay;
    private CardsInHandDisplay cardsInHandDisplay;


    public CardDisplay(ProgramRegisterDisplay programRegisterDisplay, CardsInHandDisplay cardsInHandDisplay) {
        this.programRegisterDisplay = programRegisterDisplay;
        this.cardsInHandDisplay = cardsInHandDisplay;
    }


    /**
     * Remove all program card buttons.
     * Might need to call this function several times to actually remove all buttons. (Weird bug)
     */
    public void clearAllCards() {
        for (Actor button : cardsInHandDisplay.stage.getActors()) {
            if (button instanceof ProgramCardButton) {
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
