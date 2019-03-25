package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.roborally.game.enums.Rotate;

import java.util.Collections;
import java.util.Stack;


public class ProgramCard implements Comparable {
    private Rotate rotate;
    private int moveDistance;
    private int priority;
    private String type;

    private ImageTextButton.ImageTextButtonStyle style;
    private TextureAtlas atlas;
    private Skin skin;

    public ProgramCard(Rotate rotate, int moveDistance, int priority, String type) {
        this.rotate = rotate;
        this.moveDistance = moveDistance;
        this.priority = priority;
        this.type = type;
    }

    public void setUpSkin(){
        atlas = new TextureAtlas("assets/cards/imageButton.atlas");
        skin = new Skin();
        skin.addRegions(atlas);
        style = new ImageTextButton.ImageTextButtonStyle();
        style.font = new BitmapFont();
        style.up = skin.getDrawable(type);
    }

    public ImageTextButton.ImageTextButtonStyle getStyle(){
        return this.style;
    }

    public TextureAtlas getAtlas(){
        return this.atlas;
    }

    public Skin getSkin(){
        return this.skin;
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

    public static Stack<ProgramCard> makeProgramCardDeck() {
        Stack<ProgramCard> cardStack = new Stack<>();

        // Adding cards that rotate:
        for (int priority = 10; priority <= 60; priority += 10) {
            // 180
            cardStack.push(new ProgramCard(Rotate.UTURN, 0, priority , "uTurn"));
        }
        for (int priority = 80; priority <= 420; priority += 20) {
            // Right
            cardStack.push(new ProgramCard(Rotate.RIGHT, 0, priority - 10, "rotateRight"));
            // Left
            cardStack.push(new ProgramCard(Rotate.LEFT, 0, priority, "rotateLeft"));
        }

        // Adding cards that move:
        // Backwards
        for (int priority = 430; priority <= 480; priority += 10) {
            cardStack.push(new ProgramCard(Rotate.NONE, -1, priority, "back1"));
        }
        // Forwards 1
        for (int priority = 490; priority <= 660; priority += 10) {
            cardStack.push(new ProgramCard(Rotate.NONE, 1, priority, "move1"));
        }
        // Forwards 2
        for (int priority = 670; priority <= 780; priority += 10) {
            cardStack.push(new ProgramCard(Rotate.NONE, 2, priority, "move2"));
        }
        // Forwards 3
        for (int priority = 790; priority <= 840; priority += 10) {
            cardStack.push(new ProgramCard(Rotate.NONE, 3, priority, "move3"));
        }
        Collections.shuffle(cardStack);
        return cardStack;
    }

    // FOR TESTING ONLY
    // Create a deck of cards with only turn cards, so we can test some things happening after a round
    public static Stack<ProgramCard> makeTestProgramCardDeck() {
        Stack<ProgramCard> testCardStack = new Stack<>();
        for (int i = 1; i < 27; i++) {
            testCardStack.push(new ProgramCard(Rotate.RIGHT, 0, i, "testCard"));
        }


        return testCardStack;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        String s = "";
        if (this.moveDistance == 0) {
            switch (rotate) {
                case LEFT:
                    s = "Turn left,";
                    break;
                case RIGHT:
                    s = "Turn right,";
                    break;
                case UTURN:
                    s = "U turn,";
                    break;
            }
        }
        else
            s = "Move " + moveDistance + ",";

        return s + " priority: " + priority;
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
