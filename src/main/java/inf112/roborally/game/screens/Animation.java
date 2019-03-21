package inf112.roborally.game.screens;

import inf112.roborally.game.objects.GameObject;

public abstract class Animation extends GameObject {

    public Animation(int x, int y, String filePath) {
        super(x, y, filePath);
        makeSprite();

    }

    protected abstract void update();
}
