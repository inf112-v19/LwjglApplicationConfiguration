package inf112.roborally.game.objects;

import inf112.roborally.game.Main;
import inf112.roborally.game.tools.AssMan;

public class Flag extends GameObject {
    private int flagNumber;

    public Flag(int x, int y, int flagNumber) {
        super(x, y);
        this.flagNumber = flagNumber;
        setupTextureAtlas();
        updateSprite();
    }

    private void setupTextureAtlas() {
        sprite = AssMan.manager.get(AssMan.FLAG_ATLAS).createSprite(Integer.toString(flagNumber));
        int PPM = Main.PIXELS_PER_TILE;
        sprite.setBounds(getX() / PPM, getY() / PPM, PPM, PPM);
    }

    public int getFlagNumber() {
        return flagNumber;
    }
}

