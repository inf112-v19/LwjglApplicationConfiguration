package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.roborally.game.Main;
import inf112.roborally.game.tools.AssMan;

public class Flag extends GameObject {
    private final int PPM = Main.PIXELS_PER_TILE;
    private int flagNumber;

    public Flag(int x, int y, int flagNumber) {
        super(x, y, "assets/objects/flagTextures.atlas");
        this.flagNumber = flagNumber;
        setupTextureAtlas();
        updateSprite();
    }

    private void setupTextureAtlas() {
        sprite = AssMan.manager.get(AssMan.FLAG_ATLAS).createSprite("flags");
        sprite.setBounds(getX() / PPM, getY() / PPM, PPM, PPM);
        int size = 150;
        sprite.setRegion(new TextureRegion(sprite.getTexture(), 450 - flagNumber * size, 0, size, size));
    }

    public int getFlagNumber() {
        return flagNumber;
    }

}

