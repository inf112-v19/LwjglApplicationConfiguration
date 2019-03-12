package inf112.roborally.game.objects;

import inf112.roborally.game.Main;

public class RepairSite extends GameObject {

    public RepairSite(int x, int y) {
        super(x, y, "assets/objects/repairsite.png");
        makeSprite();
        updateSprite();
        sprite.setSize(Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
    }

}
