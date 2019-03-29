package inf112.roborally.game.objects;

import inf112.roborally.game.Main;
import inf112.roborally.game.gui.AssMan;

public class RepairSite extends GameObject {

    public RepairSite(int x, int y) {
        super(x, y, AssMan.REPAIR_SITE.fileName);
        makeSprite();
        updateSprite();
        sprite.setSize(Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
    }

}
