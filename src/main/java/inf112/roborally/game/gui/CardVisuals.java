package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.roborally.game.board.ProgramCard;

import java.util.ArrayList;

public class CardVisuals extends Sprite {
    final int CARD_WIDTH = 238;
    final int CARD_HEIGHT = 300;

    Texture cardTextures;
    ArrayList<TextureRegion> move;
    ArrayList<TextureRegion> rotate;
    TextureRegion back;

    public CardVisuals() {
        super();
        setBounds(0,0, CARD_WIDTH, CARD_HEIGHT);
        setSize(CARD_WIDTH/10, CARD_HEIGHT/10);
        cardTextures = new Texture("assets/cards/programchips.png");
        move = new ArrayList<>();
        rotate = new ArrayList<>();
        back = new TextureRegion(cardTextures, CARD_WIDTH*3, CARD_HEIGHT);
        addRegionsToList(move, 0,0, 4);
        addRegionsToList(rotate, 0, 1, 3);
    }

    public void addRegionsToList(ArrayList<TextureRegion> list, int x, int y, int nRegions){
        for(int i = 0; i < nRegions; i++){
            list.add(new TextureRegion(cardTextures, x+CARD_WIDTH*i, CARD_HEIGHT*y, CARD_WIDTH, CARD_HEIGHT));
        }
    }

    public void drawCard(ProgramCard programCard, int x, int y, SpriteBatch batch) {
        setSpriteToCard(programCard, x, y);
        draw(batch);
    }

    public TextureRegion getRegion(ProgramCard programCard){
//        if(!programCard.flipped)
//            return back;

        switch (programCard.getRotate()){
            case UTURN:
                return rotate.get(2);
            case LEFT:
                return rotate.get(1);
            case RIGHT:
                return rotate.get(0);
            case NONE:
                if(programCard.getMoveDistance() == -1) {
                    return move.get(0);
                }
                else {
                    return move.get(programCard.getMoveDistance());
                }
        }
        return null;
    }

    public Sprite setSpriteToCard(ProgramCard programCard, int x, int y){
        setRegion(getRegion(programCard));
        setPosition(x, y);
        return this;
    }
}