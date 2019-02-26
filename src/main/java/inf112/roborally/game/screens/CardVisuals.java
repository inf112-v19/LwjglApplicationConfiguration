package inf112.roborally.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.roborally.game.ProgramCard;

import java.util.ArrayList;

public class CardVisuals extends Sprite {
    final int CARD_WIDTH = 640;
    final int CARD_HEIGHT = 890;

    Texture cardTextures;
    ArrayList<TextureRegion> move;
    ArrayList<TextureRegion> rotate;

    public CardVisuals() {
        super();
        setBounds(0,0, CARD_WIDTH, CARD_HEIGHT);
        setSize(CARD_WIDTH/10, CARD_HEIGHT/10);
        cardTextures = new Texture("assets/cards/cardTextures.png");
        move = new ArrayList<>();
        rotate = new ArrayList<>();
        addRegionsToList(move, 0,0, 4);
        addRegionsToList(rotate, 0, 1, 3);
    }

    public void addRegionsToList(ArrayList<TextureRegion> list, int x, int y, int nRegions){
        for(int i = 0; i < nRegions; i++){
            list.add(new TextureRegion(cardTextures, x+CARD_WIDTH*i, CARD_HEIGHT*y, CARD_WIDTH, CARD_HEIGHT));
        }
    }

    public void drawCard(ProgramCard programCard, int x, int y, SpriteBatch batch) {
        switch (programCard.getRotate()){
            case UTURN:
                setRegion(rotate.get(0)); break;
            case LEFT:
                setRegion(rotate.get(1)); break;
            case RIGHT:
                setRegion(rotate.get(2)); break;
            case NONE:
                if(programCard.getMoveDistance() == -1) {
                    setRegion(move.get(0));
                }
                else {
                    setRegion(move.get(programCard.getMoveDistance()));
                }
        }
        setPosition(x, y);
        draw(batch);
    }

}