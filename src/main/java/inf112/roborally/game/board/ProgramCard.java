package inf112.roborally.game.board;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.tools.AssMan;

import java.util.Collections;
import java.util.Stack;

public class ProgramCard implements Comparable {
    private Rotate rotate;
    private int moveDistance;
    private int priority;

    private ImageTextButton.ImageTextButtonStyle style;
    private Skin skin;

    /**
     * Creates a program card.
     * Need to call {@link #setUpSkin()} once after creation to be able to draw the card.
     *
     * @param rotate       which rotation the card should have
     * @param moveDistance how many steps to move
     * @param priority     card priority
     */
    public ProgramCard(Rotate rotate, int moveDistance, int priority) {
        this.rotate = rotate;
        this.moveDistance = moveDistance;
        this.priority = priority;
    }

    public static Stack<ProgramCard> makeProgramCardDeck() {
        Stack<ProgramCard> cardStack = new Stack<>();
        // Adding cards that rotate:
        for (int priority = 10; priority <= 60; priority += 10) {
            // 180
            cardStack.push(new ProgramCard(Rotate.UTURN, 0, priority));
        }
        for (int priority = 80; priority <= 420; priority += 20) {
            // Right
            cardStack.push(new ProgramCard(Rotate.RIGHT, 0, priority - 10));
            // Left
            cardStack.push(new ProgramCard(Rotate.LEFT, 0, priority));
        }

        // Adding cards that move:
        // Backwards
        for (int priority = 430; priority <= 480; priority += 10) {
            cardStack.push(new ProgramCard(Rotate.NONE, -1, priority));
        }
        // Forwards 1
        for (int priority = 490; priority <= 660; priority += 10) {
            cardStack.push(new ProgramCard(Rotate.NONE, 1, priority));
        }
        // Forwards 2
        for (int priority = 670; priority <= 780; priority += 10) {
            cardStack.push(new ProgramCard(Rotate.NONE, 2, priority));
        }
        // Forwards 3
        for (int priority = 790; priority <= 840; priority += 10) {
            cardStack.push(new ProgramCard(Rotate.NONE, 3, priority));
        }
        Collections.shuffle(cardStack);
        return cardStack;
    }

    // FOR TESTING ONLY
    // Create a deck of cards with only turn cards, so we can test some things happening after a round
    public static Stack<ProgramCard> makeTestProgramCardDeck() {
        Stack<ProgramCard> testCardStack = new Stack<>();
        for (int i = 1; i < 27; i++) {
            testCardStack.push(new ProgramCard(Rotate.RIGHT, 0, i));
        }
        return testCardStack;
    }

    public void setUpSkin() {
        skin = new Skin();
        skin.addRegions(AssMan.manager.get(AssMan.PROGRAM_CARD_ATLAS));
        style = new ImageTextButton.ImageTextButtonStyle();
        style.font = AssMan.manager.get(AssMan.FONT_GROTESKIA);
        style.up = skin.getDrawable(identify());
    }

    public ImageTextButton.ImageTextButtonStyle getStyle() {
        return this.style;
    }

    public void dispose() {
        skin.dispose();
        style.font.dispose();
    }

    public boolean isRotate() {
        return rotate != Rotate.NONE;
    }

    public int getMoveDistance() {
        return this.moveDistance;
    }

    public Rotate getRotate() {
        return this.rotate;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        String result;
        if (rotate != Rotate.NONE)
            result = "Rotate: ";
        else
            result = "Move: ";
        return result + identify() + " Priority: " + priority;
    }

    private String identify() {
        if (rotate != Rotate.NONE) { // rotation card
            return rotate.toString();
        }
        else { // move card
            return Integer.toString(moveDistance);
        }
    }

    @Override
    public int compareTo(Object o) {
        if (this.getClass() != o.getClass())
            return 0;
        ProgramCard other = (ProgramCard) o;
        if (this.getPriority() > other.getPriority())
            return -1;
        return 1;
    }
}
